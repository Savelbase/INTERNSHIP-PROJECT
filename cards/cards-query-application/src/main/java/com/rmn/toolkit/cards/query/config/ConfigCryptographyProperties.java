package com.rmn.toolkit.cards.query.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "cryptography")
public class ConfigCryptographyProperties {
    private String privateKey ;
    private String publicKey ;
}
