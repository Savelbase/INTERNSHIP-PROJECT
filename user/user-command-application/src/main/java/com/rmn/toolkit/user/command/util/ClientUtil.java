package com.rmn.toolkit.user.command.util;

import com.rmn.toolkit.user.command.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.user.command.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.model.Role;
import com.rmn.toolkit.user.command.model.type.ClientStatusType;
import com.rmn.toolkit.user.command.repository.ClientRepository;
import com.rmn.toolkit.user.command.security.AuthorityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientUtil {
    private final ClientRepository clientRepository;
    private final RoleUtil roleUtil;

    public Client findClientById(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.error("Client with id='{}' not found", clientId);
                    throw new ClientNotFoundException(clientId);
                });
    }

    public Client findClientByPassportNumber(String passportNumber) {
        return clientRepository.findClientByPassportNumber(passportNumber)
                .orElseThrow(() -> {
                    log.error("Client with given passport number is absent");
                    throw new ClientNotFoundException();
                });
    }

    public void checkClientRights(String roleName) {
        Role role = roleUtil.findRoleByName(roleName);
        if (Arrays.stream(role.getAuthorities()).noneMatch(AuthorityType.USER_EDIT::equals)) {
            log.error("Client hasn't got enough rights for registration");
            throw new AccessDeniedException("Client hasn't got enough rights for registration");
        }
    }

    public void checkIfClientIsBlocked(String clientId) {
        Client client = findClientById(clientId);
        if (ClientStatusType.BLOCKED.equals(client.getStatus())) {
            log.error("Client status is blocked");
            throw new ClientStatusBlockedException();
        }
    }
}
