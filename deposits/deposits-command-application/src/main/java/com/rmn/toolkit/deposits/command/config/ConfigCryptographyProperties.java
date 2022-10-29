package com.rmn.toolkit.deposits.command.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cryptography")
public class ConfigCryptographyProperties {
    private String privateKey ;
    private String publicKey ;
}
