package com.rmn.toolkit.deposits.query.util;


import com.rmn.toolkit.deposits.query.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.deposits.query.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.deposits.query.model.Client;
import com.rmn.toolkit.deposits.query.model.type.ClientStatusType;
import com.rmn.toolkit.deposits.query.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientUtil {
    private final ClientRepository clientRepository;

    public Client findClientById(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.error("Client with id='{}' not found", clientId);
                    throw new ClientNotFoundException(clientId);
                });
    }

    public void checkIfClientIsBlocked(Client client) {
        if (ClientStatusType.BLOCKED.equals(client.getStatus())) {
            log.error("Client status is blocked");
            throw new ClientStatusBlockedException();
        }
    }
}
