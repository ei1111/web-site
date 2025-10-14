package com.web.site;

import com.web.site.global.common.util.SecurityUtill;
import java.util.Optional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}


    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(SecurityUtill.getUserId());
    }

}
