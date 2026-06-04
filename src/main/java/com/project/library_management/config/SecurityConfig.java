package com.project.library_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/", "/login","/books", "/css/**", "/js/**")
                        .permitAll()

                        // ADMIN ONLY
                        .requestMatchers(
                                "/books/new",
                                "/books/save_books",
                                "/books/edit/**",
                                "/books/save_edit/**",
                                "/books/delete/**",
                                "/students/new",
                                "/students/save_students",
                                "/students/edit/**",
                                "/students/save_edit/**",
                                "/students/delete/**"
                        ).hasRole("ADMIN")

                        // ADMIN + LIBRARIAN
                        .requestMatchers(
                                "/books",
                                "/books/search",
                                "/students",
                                "/issues/**",
                                "/returns/**",
                                "/overdue/**"
                        ).hasAnyRole("ADMIN", "LIBRARIAN")

                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );


        return http.build();
    }
}