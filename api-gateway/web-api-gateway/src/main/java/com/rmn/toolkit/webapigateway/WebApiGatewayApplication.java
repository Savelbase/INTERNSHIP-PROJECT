package com.rmn.toolkit.webapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WebApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApiGatewayApplication.class, args);
    }
}
