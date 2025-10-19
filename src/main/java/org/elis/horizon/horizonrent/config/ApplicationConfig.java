package org.elis.horizon.horizonrent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Use the PasswordEncoder for storing the passwords securely.
        String passDev = passwordEncoder().encode("dev123");
        String passQa = passwordEncoder().encode("qa123");

        // 1. Define the 'dev' user
        UserDetails devUser = User.builder()
            .username("dev")
            .password(passDev)
            .roles("Dev") // Spring Security automatically prefixes this with 'ROLE_'
            .build();

        // 2. Define the 'qa' user
        UserDetails qaUser = User.builder()
            .username("qa")
            .password(passQa)
            .roles("QA")
            .build();

        // 3. Return an InMemoryUserDetailsManager containing both users
        return new InMemoryUserDetailsManager(devUser, qaUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
