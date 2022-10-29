package com.rmn.toolkit.user.registration.testUtil;

import com.rmn.toolkit.user.registration.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ModelUtil {
    private final ClientRepository clientRepository;

    @Transactional
    public void setClientVerificationCodeId() {
        clientRepository.findById(EndpointUrlAndConstants.CLIENT_ID)
                .ifPresent(client -> {
                    client.setVerificationCodeId(EndpointUrlAndConstants.VERIFICATION_CODE_ID);
                    clientRepository.save(client);
                });
    }

    @Transactional
    public void resetRegisteredClient() {
        clientRepository.findById(EndpointUrlAndConstants.CLIENT_ID)
                .ifPresent(client -> {
                    client.setRegistered(false);
                    clientRepository.save(client);
                });
    }
}
