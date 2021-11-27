package com.m2w.sispj.config.security;

import com.m2w.sispj.domain.User;
import com.m2w.sispj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAndDeletedFalse(username);

        if(user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("Usuário ou password incorretos!");
    }
}
