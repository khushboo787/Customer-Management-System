package com.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	 http.csrf().disable()
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
         .and()
         .authorizeHttpRequests()
         .requestMatchers("/favicon.ico","/css/**", "/js/**").permitAll()
         .requestMatchers("/customers","/sync","/customers/search","/customer").permitAll()
         .requestMatchers("/add", "/updateForm/{id}","/register","/login","/logout").permitAll()
      .requestMatchers( "/addCustomer", "/updateCustomer/{id}" , "/delete/{id}").hasAuthority("ROLE_ADMIN")
          
         .requestMatchers("/getAllCustomers").permitAll()
         .anyRequest().authenticated()
     .and()
     .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

     return http.build();
    }
}
