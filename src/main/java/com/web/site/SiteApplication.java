package com.web.site;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.site.global.common.util.SecurityUtill;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestClient;

@EnableJpaAuditing
@SpringBootApplication
public class SiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}

    @Bean
    JPAQueryFactory queryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }


    @Bean
    public AuditorAware<String> auditorProvider() {
        return () ->  Optional.ofNullable(SecurityUtill.getUserId())
                .or(() -> Optional.of("SYSTEM"));
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
