package com.example.backend2.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests((authorize) -> authorize
                                .antMatchers("/api/auth/login").permitAll()
//                                .antMatchers("/api/comment").permitAll()
//                                .antMatchers("/api/comment/*").permitAll()
//                                .antMatchers("/api/course").permitAll()
//                                .antMatchers("/api/course/*").permitAll()
//                                .antMatchers("/api/summary").permitAll()
//                                .antMatchers("/api/summary/*").permitAll()
//                                .antMatchers("/api/course/{courseId}/review").permitAll()
//                                .antMatchers("/api/course/{courseId}/summary").permitAll()
//                                .antMatchers("/api/review").permitAll()
//                                .antMatchers("/api/review/*").permitAll()
                                .antMatchers("/api/files").permitAll()
                                .antMatchers("/api/files/*").permitAll()
                                .antMatchers("/api/ReportReview").permitAll()
                                .antMatchers("/api/ReportReview/*").permitAll()
                                .antMatchers("/api/ReportCourseFile").permitAll()
                                .antMatchers("/api/ReportCourseFile/*").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)) // 401 Unauthorized
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
