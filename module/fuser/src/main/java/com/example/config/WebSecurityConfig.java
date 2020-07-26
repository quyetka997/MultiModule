package com.example.config;

import com.example.application.exception.CustomExceptionHandler;
import com.example.application.jwt.user.JWTAppAuthenticationFilter;
import com.example.application.jwt.user.JWTAppAuthorizationFilter;
import com.example.application.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure HttpSecurity");
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/api/user/register").permitAll()
                .antMatchers("/api/user/forget_password","/api/user/verification").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/carrier/**").access("hasRole('carrier')")
                .antMatchers("/api/shipper/**").hasRole("shipper")
                .anyRequest().authenticated()
                .and()
                //.addFilterBefore(new CustomExceptionHandler(), ProblemHandling.class)
                .addFilter(new JWTAppAuthenticationFilter(authenticationManager(), userDetailsService, bCryptPasswordEncoder))
                .addFilter(new JWTAppAuthorizationFilter(authenticationManager(), userDetailsService))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("configure AuthenticationManagerBuilder");
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        System.out.println("corsConfigurationSource");
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

