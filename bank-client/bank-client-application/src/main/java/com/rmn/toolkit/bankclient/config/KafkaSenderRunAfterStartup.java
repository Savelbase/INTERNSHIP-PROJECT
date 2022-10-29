package com.rmn.toolkit.bankclient.config;

import com.rmn.toolkit.bankclient.service.BankClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSenderRunAfterStartup {
    private final BankClientService bankClientService;

    @EventListener(ApplicationReadyEvent.class)
    public void sendAllBankClientsAfterStartup() {
        bankClientService.sendAllBankClientsThrewKafka();
    }
}
