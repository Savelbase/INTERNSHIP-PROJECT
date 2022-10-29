package com.rmn.toolkit.credits.command.config;


import com.rmn.toolkit.cryptography.RSAUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@RequiredArgsConstructor
public class CryptographyConfig {
    private final ConfigCryptographyProperties cryptographyProperties;
    @Bean
    RSAUtils getRSAUtils (){
        return new RSAUtils(cryptographyProperties.getPublicKey() , cryptographyProperties.getPrivateKey());
    }

}
