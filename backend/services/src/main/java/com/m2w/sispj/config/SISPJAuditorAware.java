package com.m2w.sispj.config;

import com.m2w.sispj.domain.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SISPJAuditorAware implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).map(o -> (User)o);
    }
}