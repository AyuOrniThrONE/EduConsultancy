package com.example.UserAuthModule.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .requestMatchers("/api/register", "/api/verify", "/login").permitAll()
            .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
            .requestMatchers("/student/**").hasRole("ROLE_USER")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .defaultSuccessUrl("/dashboard", true)
            .permitAll()
            .and()
            .logout().permitAll();
        return http.build();
    }
}
