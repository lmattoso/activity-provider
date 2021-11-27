package com.m2w.sispj.config;

import com.m2w.sispj.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class SISPJConfiguration {

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new SISPJAuditorAware();
    }
}
