package org.example.servicelinkbe.configuration.security;

import org.example.servicelinkbe.configuration.security.auth.AuthenticationRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class WebSecurityConfig {
    private static final String USERS_ENDPOINT = "/api/users";
    private static final String SERVICES_ENDPOINT = "/api/services";
    private static final String OFFERS_ENDPOINT  = "/api/offers";
    private static final String APPOINTMENTS_ENDPOINT = "/api/appointments";
    private static final String[] SWAGGER_UI_RESOURCES = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           AuthenticationEntryPoint authenticationEntryPoint,
                                           AuthenticationRequestFilter authenticationRequestFilter) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST,
                                        USERS_ENDPOINT,
                                        "/api/tokens",
                                        "/api/tokens/register",
                                        SERVICES_ENDPOINT,
                                        SERVICES_ENDPOINT+"/{id}",
                                        OFFERS_ENDPOINT,
                                        OFFERS_ENDPOINT+"/{id}",
                                        APPOINTMENTS_ENDPOINT,
                                        APPOINTMENTS_ENDPOINT+"/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET,
                                        USERS_ENDPOINT,
                                        USERS_ENDPOINT+"/{id}",
                                        SERVICES_ENDPOINT,
                                        SERVICES_ENDPOINT+"/{id}",
                                        OFFERS_ENDPOINT,
                                        OFFERS_ENDPOINT+"/{id}",
                                        APPOINTMENTS_ENDPOINT,
                                        APPOINTMENTS_ENDPOINT+"/{id}")
                                .permitAll()
                                .requestMatchers(HttpMethod.DELETE,
                                        USERS_ENDPOINT,
                                        SERVICES_ENDPOINT)
                                .permitAll()
                                .requestMatchers(HttpMethod.PUT,
                                        USERS_ENDPOINT,
                                        SERVICES_ENDPOINT)
                                .permitAll()
                                .requestMatchers(SWAGGER_UI_RESOURCES).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(configure -> configure.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
