package com.rmn.toolkit.user.query.service;

import com.rmn.toolkit.user.query.dto.response.proections.ClientDto;
import com.rmn.toolkit.user.query.dto.response.proections.UserDto;
import com.rmn.toolkit.user.query.model.Client;
import com.rmn.toolkit.user.query.repo.ClientRepository;
import com.rmn.toolkit.user.query.util.ClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserQueryService {
    private final ClientRepository clientRepository;
    private final ClientUtil clientUtil;

    @Transactional(readOnly = true)
    public List<ClientDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientRepository.findAll(pageable);
        List<ClientDto> clientDtoList = new ArrayList<>();
        clients.stream().forEach(client ->
                clientDtoList.add(createClientDto(client)));
        return clientDtoList;
    }

    @Transactional(readOnly = true)
    public ClientDto getUserById(String id) {
        Client client = clientUtil.findClientById(id);
        return createClientDto(client);
    }

    private ClientDto createClientDto(Client client){
        return ClientDto.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .mobilePhone(client.getMobilePhone())
                .passportNumber(client.getPassportNumber())
                .status(client.getStatus())
                .resident(client.isResident())
                .bankClient(client.isBankClient())
                .user(UserDto.builder()
                        .securityQuestion(client.getUser().getSecurityQuestion())
                        .email(client.getUser().getEmail())
                        .notifications(client.getUser().getNotifications())
                        .build())
                .build();
    }
}
