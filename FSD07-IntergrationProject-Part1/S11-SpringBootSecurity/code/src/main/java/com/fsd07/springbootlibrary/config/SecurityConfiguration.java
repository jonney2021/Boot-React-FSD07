package com.fsd07.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import com.sun.xml.bind.v2.TODO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

/**
 * @Author: Yeming Hu
 * @Date: 9/9/2023
 * @Description: com.fsd07.springbootlibrary.config
 * @Version: 1.0
 */

// TODO S20.1 Create Class SecurityConfiguration

@Configuration

public class SecurityConfiguration {
//    TODO S20.2 Create a filterChain bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // Disable Cross Site Request Forgery
        // TODO: Refactor the filter chain configurations into smaller, testable methods.
        http.csrf().disable();

        //Protect endpoints ar /api/<type>/secure
        http.authorizeRequests(configurer ->
                configurer.antMatchers("/api/books/secure/**") // TODO: Add dynamic URL-based security policies depending on user roles or permissions.
                        .authenticated())// only authenticated user can access the routes math
                .oauth2ResourceServer() // spring boot act as oauth2ResourceServer
                .jwt();  // spring boot use jwt

        //Add CORS filters
        http.cors();

        // Add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class,new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401's to make the response friendly
        Okta.configureResourceServer401ResponseBody(http); // TODO: Customize the 401 response body to provide more informative error messages.
        return http.build();
    }


}
