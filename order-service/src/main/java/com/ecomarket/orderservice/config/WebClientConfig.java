package com.ecomarket.orderservice.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {
    @Value("") private String invUrl;
    @Value("") private String couponUrl;
    @Value("") private String billingUrl;

    @Bean public WebClient inventoryWebClient(WebClient.Builder builder) { return builder.baseUrl(invUrl).build(); }
    @Bean public WebClient couponWebClient(WebClient.Builder builder) { return builder.baseUrl(couponUrl).build(); }
    @Bean public WebClient billingWebClient(WebClient.Builder builder) { return builder.baseUrl(billingUrl).build(); }
}