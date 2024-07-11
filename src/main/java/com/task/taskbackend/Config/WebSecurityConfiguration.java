package com.task.taskbackend.Config;


import com.task.taskbackend.Models.UserRole;
import com.task.taskbackend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfiguration {

    private  final JwtFilter jwtFilter;
    private  final UserService userService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                        "/api/auth/**").permitAll()  // Permit all endpoints under /api/auth without authentication
                                .requestMatchers("/api/produits/**").permitAll()   // Permit all endpoints under /api/produits without authentication
                                .requestMatchers("/api/stocks/**").permitAll()      // Require authentication for endpoints under /api/stocks
                                .requestMatchers("/api/promotions/**").permitAll()      // Require authentication for endpoints under /api/stocks

                                .requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name())  // Require ADMIN role for /api/admin/**
                                .requestMatchers("/api/employee/**").hasAnyAuthority(UserRole.EMPLOYEE.name())  // Require EMPLOYEE role for /api/employee/**
                                .anyRequest().authenticated()  // All other requests require authentication
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean

    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsservice());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{

        return  config.getAuthenticationManager();
    }

}