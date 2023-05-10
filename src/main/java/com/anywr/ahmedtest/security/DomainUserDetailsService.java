package com.anywr.ahmedtest.security;

import com.anywr.ahmedtest.domain.User;
import com.anywr.ahmedtest.repository.UserRepository;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findOneByLogin(lowercaseLogin)
            .map(user -> createSpringSecurityUser(user))
            .orElseThrow(
            		() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database")
            		);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), new ArrayList<GrantedAuthority>());
    }
}
