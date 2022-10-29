package com.rmn.toolkit.bankclient.service;

import com.rmn.toolkit.bankclient.event.EventType;
import com.rmn.toolkit.bankclient.event.client.BankClientCreatedEvent;
import com.rmn.toolkit.bankclient.message.EventSender;
import com.rmn.toolkit.bankclient.model.Client;
import com.rmn.toolkit.bankclient.repository.ClientRepository;
import com.rmn.toolkit.bankclient.util.EventPayloadUtil;
import com.rmn.toolkit.bankclient.util.EventUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankClientService {
    private static final Integer VERSION = 1;
    private static final Integer SIZE_VALUE = 10;

    private final ClientRepository clientRepository;
    private final EventPayloadUtil eventPayloadUtil;
    private final EventUtil eventUtil;
    private final EventSender eventSender;

    public Page<Client> getBankClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAll(pageable);
    }

    public void sendAllBankClientsThrewKafka() {
        long count = clientRepository.count();
        for (int i = 0; i < count; i++) {
            Page<Client> bankClients = getBankClients(i, SIZE_VALUE);

            for (Client client : bankClients) {
                if (client == null) {
                    break;
                }
                BankClientCreatedEvent.Payload payload = eventPayloadUtil.createBankClientCreatedEventPayload(client);
                BankClientCreatedEvent event = BankClientCreatedEvent.builder().build();
                eventUtil.populateEventFields(event, EventType.BANK_CLIENT_CREATED, client.getId(), VERSION, client.getId(), payload);
                eventSender.send(event);
            }
            count -= SIZE_VALUE;
        }
    }
}
