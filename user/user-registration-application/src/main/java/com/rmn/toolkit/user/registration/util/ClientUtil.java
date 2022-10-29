package com.rmn.toolkit.user.registration.util;

import com.rmn.toolkit.user.registration.dto.request.RegexConstants;
import com.rmn.toolkit.user.registration.exception.badrequest.InvalidMiddleNameException;
import com.rmn.toolkit.user.registration.exception.conflict.ClientAlreadyRegisteredException;
import com.rmn.toolkit.user.registration.exception.conflict.NotRFResidentException;
import com.rmn.toolkit.user.registration.exception.conflict.RequiredFieldMissingException;
import com.rmn.toolkit.user.registration.exception.forbidden.DifferentClientIdException;
import com.rmn.toolkit.user.registration.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.registration.model.Client;
import com.rmn.toolkit.user.registration.model.Role;
import com.rmn.toolkit.user.registration.repository.ClientRepository;
import com.rmn.toolkit.user.registration.security.AuthorityType;
import com.rmn.toolkit.user.registration.security.SecurityUtil;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientUtil {
    private static final int VERSION = 1;

    private final ClientRepository clientRepository;
    private final SecurityUtil securityUtil;
    private final RoleUtil roleUtil;

    public String encodeValue(String value) {
        return new BCryptPasswordEncoder().encode(value);
    }

    public Client createClient(String mobilePhone, String roleId) {
        return Client.builder()
                .id(UUID.randomUUID().toString())
                .mobilePhone(mobilePhone)
                .roleId(roleId)
                .version(VERSION)
                .build();
    }

    public Client findClientById(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.error("Client with id='{}' not found", clientId);
                    throw new ClientNotFoundException(clientId);
                });
    }

    public void checkIfClientRegisteredByMobilePhone(String mobilePhone) {
        if (clientRepository.existsByMobilePhoneAndRegistered(mobilePhone, true)) {
            log.error("Client with mobile phone='{}' is already registered", mobilePhone);
            throw new ClientAlreadyRegisteredException(mobilePhone, true);
        }
    }

    public void checkClientRights(String roleName) {
        Role role = roleUtil.findRoleByName(roleName);

        if (Arrays.stream(role.getAuthorities()).noneMatch(AuthorityType.REGISTRATION::equals)) {
            log.error("Client hasn't got enough rights for registration");
            throw new AccessDeniedException("Client hasn't got enough rights for registration");
        }
    }

    public void checkIfTokenClientIdMatchClientId(String clientId) {
        if (!Objects.equals(securityUtil.getClientIdFromSecurityContext(), clientId)){
            log.error("Client with id='{}' doesn't match clientId in token", clientId);
            throw new DifferentClientIdException();
        }
    }

    public void checkRFClientPassportNumber(String rfPassportNumber) {
        if (!Pattern.matches(RegexConstants.RF_PASSPORT_NUMBER_REGEX, rfPassportNumber)) {
            log.error("Client passport number doesn't relate to RF resident");
            throw new NotRFResidentException(true);
        }
    }

    public void checkClientMiddleName(String middleName) {
        if (Strings.hasText(middleName) && !Pattern.matches(RegexConstants.USER_NAME_REGEX, middleName)) {
            log.error("Client middle name doesn't match");
            throw new InvalidMiddleNameException();
        }
    }

    public void checkIfClientIsResident(boolean resident) {
        if (!resident) {
            log.error("Client isn't RF resident");
            throw new NotRFResidentException();
        }
    }

    public void checkIfVerificationCodePassed(String verificationCodeId) {
        if (verificationCodeId == null || !clientRepository.existsByVerificationCodeId(verificationCodeId)) {
            log.error("Verification code is missing");
            throw new RequiredFieldMissingException();
        }
    }

    public void checkIfRequiredFieldIsAbsent(Client client) {
        boolean absent = Stream.of(client.getMobilePhone(),
                        client.getVerificationCodeId(),
                        client.getPassportNumber(),
                        client.getFirstName(),
                        client.getLastName(),
                        client.getPassword(),
                        client.getSecurityQuestion(),
                        client.getSecurityAnswer())
                .anyMatch(Objects::isNull);

        throwExceptionIfRequiredFieldIsAbsent(absent);
    }

    public void checkIfFullNameIsAbsent(Client client) {
        boolean absent = Stream.of(client.getFirstName(), client.getLastName()).anyMatch(Objects::isNull);
        throwExceptionIfRequiredFieldIsAbsent(absent);
    }

    public void checkIfPasswordIsAbsent(String password) {
        throwExceptionIfRequiredFieldIsAbsent(password == null);
    }

    public void resetClientFields(Client client) {
        client.setPassportNumber(null);
        client.setFirstName(null);
        client.setLastName(null);
        client.setMiddleName(null);
        client.setPassword(null);
        client.setSecurityQuestion(null);
        client.setSecurityAnswer(null);
        client.setVerificationCodeId(null);
        client.setAccessionDateTime(null);
        client.setBankClient(false);
        client.setResident(false);
        client.setRegistered(false);
    }

    private void throwExceptionIfRequiredFieldIsAbsent(boolean absent) {
        if (absent) {
            throw new RequiredFieldMissingException();
        }
    }

    public void saveVerificationCodeId(Client client, String verificationCodeId) {
        client.setVerificationCodeId(verificationCodeId);
        clientRepository.save(client);
    }
}
