package com.kms.demo.config;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

import com.kms.demo.security.AuthoritiesConstants;
import com.kms.demo.security.jwt.JWTFilter;
import com.kms.demo.security.jwt.TokenProvider;
import com.kms.demo.web.filter.SpaWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport;
import tech.jhipster.config.JHipsterProperties;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final JHipsterProperties jHipsterProperties;

    private final ReactiveUserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(
        ReactiveUserDetailsService userDetailsService,
        TokenProvider tokenProvider,
        JHipsterProperties jHipsterProperties,
        SecurityProblemSupport problemSupport
    ) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.jHipsterProperties = jHipsterProperties;
        this.problemSupport = problemSupport;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
            userDetailsService
        );
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
            .securityMatcher(new NegatedServerWebExchangeMatcher(new OrServerWebExchangeMatcher(
                pathMatchers("/**"),
                pathMatchers(HttpMethod.OPTIONS, "/**")
            )))
            .csrf()
                .disable()
            .exceptionHandling()
                .accessDeniedHandler(problemSupport)
                .authenticationEntryPoint(problemSupport)
        .and()
            .headers()
            .contentSecurityPolicy(jHipsterProperties.getSecurity().getContentSecurityPolicy())
            .and()
                .referrerPolicy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
            .and()
                .permissionsPolicy().policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
            .and()
                .frameOptions().mode(Mode.DENY)
        .and()
            .authorizeExchange()
            .pathMatchers("/**").permitAll();
        // @formatter:on
        return http.build();
    }
}
