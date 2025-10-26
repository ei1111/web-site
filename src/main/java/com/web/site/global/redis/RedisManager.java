package com.web.site.global.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.web.site.global.config.utill.CustomObjectMapper;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisManager {

    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;
    private static final Long EXPIRE_SECONDS = 60 * 60 * 24 * 7L; // 1주일

    public <T> T get(RedisKeyPrefix keyPrefix, Long id, Class<T> clazz) {
        String generatedKey = generateCacheKey(keyPrefix, id);
        return Optional.ofNullable(redisTemplate.opsForValue().get(generatedKey))
                .map(s -> {
                    try {
                        return customObjectMapper.readValue(s, clazz);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(null);
    }

    public <T> void set(RedisKeyPrefix keyPrefix, Long id, T data) {
        try {
            String generatedKey = generateCacheKey(keyPrefix, id);
            redisTemplate.opsForValue().set(
                    generatedKey,
                    customObjectMapper.writeValueAsString(data),
                    Duration.ofSeconds(EXPIRE_SECONDS)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(RedisKeyPrefix keyPrefix, Long id) {
        redisTemplate.delete(generateCacheKey(keyPrefix, id));
    }

    private String generateCacheKey(RedisKeyPrefix keyPrefix, Long id) {
        return keyPrefix.getPrefix() + id;
    }
}
