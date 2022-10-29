package com.rmn.toolkit.user.registration.service;

import com.rmn.toolkit.user.registration.dto.request.*;
import com.rmn.toolkit.user.registration.event.EventType;
import com.rmn.toolkit.user.registration.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.registration.exception.conflict.DuplicatePassportNumberException;
import com.rmn.toolkit.user.registration.exception.unauthorized.IncorrectVerificationCodeException;
import com.rmn.toolkit.user.registration.exception.locked.MaxLimitExceededException;
import com.rmn.toolkit.user.registration.message.EventSender;
import com.rmn.toolkit.user.registration.model.Client;
import com.rmn.toolkit.user.registration.model.Role;
import com.rmn.toolkit.user.registration.model.type.RoleType;
import com.rmn.toolkit.user.registration.model.VerificationCode;
import com.rmn.toolkit.user.registration.repository.ClientRepository;
import com.rmn.toolkit.user.registration.repository.VerificationCodeRepository;
import com.rmn.toolkit.user.registration.security.jwt.service.TokenService;
import com.rmn.toolkit.user.registration.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private static final int MAX_LIMIT = 3;
    private static final int VERSION = 1;

    @Value("${authentication.verificationCode.expirationSec}")
    private Integer verificationCodeExpirationSec;
    private final ClientUtil clientUtil;
    private final ClientRepository clientRepository;
    private final EventUtil eventUtil;
    private final RoleUtil roleUtil;
    private final TokenService tokenService;
    private final VerificationCodeUtil verificationCodeUtil;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EventPayloadUtil eventPayloadUtil;
    private final EventSender eventSender;

    @Transactional
    public String checkMobilePhoneAndGenerateToken(MobilePhoneDto mobilePhoneDto) {
        String mobilePhone = mobilePhoneDto.getMobilePhone();
        clientUtil.checkIfClientRegisteredByMobilePhone(mobilePhone);

        Optional<Client> optionalClient = clientRepository.findByMobilePhone(mobilePhone);

        Client client;
        String accessToken;
        if (optionalClient.isEmpty()) {
            Role role = roleUtil.findRoleByName(RoleType.CLIENT.name());
            client = clientUtil.createClient(mobilePhone, role.getId());

            accessToken = tokenService.generateAccessToken(client.getId(), role.getAuthorities());
        } else {
            client = optionalClient.get();
            if (!client.isBankClient()) {
                clientUtil.resetClientFields(client);
            }
            Role role = roleUtil.findRoleById(client.getRoleId());

            accessToken = tokenService.generateAccessToken(client.getId(), role.getAuthorities());
        }

        clientRepository.save(client);

        return accessToken;
    }

    @Transactional
    public String createVerificationCode(String clientId) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());

        String verificationCode = verificationCodeUtil.generateVerificationCode();
        String verificationCodeHash = clientUtil.encodeValue(verificationCode);

        ZonedDateTime currentDateTime = ZonedDateTime.now();
        ZonedDateTime expiryDateTime = currentDateTime.plus(verificationCodeExpirationSec, ChronoUnit.SECONDS);

        Optional<VerificationCode> optionalVerification = verificationCodeRepository
                .findByClientIdAndVerified(clientId, false);
        VerificationCode verification;
        if (optionalVerification.isEmpty()) {
            verification = verificationCodeUtil.createVerificationCode(clientId, verificationCodeHash, expiryDateTime);
        } else {
            verification = optionalVerification.get();
            verificationCodeUtil.checkIfValidToGenerateNewCode(verification.getNextRequestDateTime());

            int attemptCounter = verification.getAttemptCounter();
            if (attemptCounter < MAX_LIMIT) {
                verificationCodeUtil.editVerificationBeforeReachingMaxAttempt(verification, verificationCodeHash, expiryDateTime);
            } else {
                verificationCodeUtil.editVerificationAfterReachingMaxAttempt(verification);
            }
        }

        verificationCodeRepository.save(verification);

        return verificationCode;
    }

    @Transactional(noRollbackFor = {
            IncorrectVerificationCodeException.class,
            MaxLimitExceededException.class
    })
    public void checkVerificationCode(String clientId, String verificationCode) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());

        VerificationCode verification = verificationCodeUtil.findNotVerifiedCodeByClientId(clientId);

        verificationCodeUtil.checkIfMaxLimitIsExceeded(verification.isExceededMaxLimit());

        verificationCodeUtil.checkIfCodeIsExpired(verification.getExpiryDateTime());

        int attemptCounter = verification.getAttemptCounter();
        if (attemptCounter < MAX_LIMIT - 1) {
            verificationCodeUtil.checkVerificationCodeBeforeMaxLimitExceeded(verification, verificationCode);
        } else {
            verificationCodeUtil.checkVerificationCodeIfMaxLimitExceeded(verification, verificationCode);
        }

        verificationCodeUtil.saveVerified(verification, true);
        clientUtil.saveVerificationCodeId(client, verification.getId());
    }

    @Transactional
    public void savePassword(String clientId, PasswordDto passwordDto) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());
        clientUtil.checkIfVerificationCodePassed(client.getVerificationCodeId());

        String passwordHash = clientUtil.encodeValue(passwordDto.getPassword());
        client.setPassword(passwordHash);

        clientRepository.save(client);
    }

    @Transactional
    public void saveResidentPassportNumber(String clientId, PassportNumberDto passportNumberDto) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());
        clientUtil.checkIfPasswordIsAbsent(client.getPassword());

        String passportNumber = passportNumberDto.getPassportNumber();
        clientUtil.checkRFClientPassportNumber(passportNumber);

        Optional<Client> optionalClient = clientRepository.findByPassportNumber(passportNumber);
        if (optionalClient.isPresent()) {
            Client clientWithCurrentPassportNumber = optionalClient.get();
            String mobilePhone = clientWithCurrentPassportNumber.getMobilePhone();
            if (!client.getMobilePhone().equals(mobilePhone)) {
                log.error("Passport number should be unique");
                throw new DuplicatePassportNumberException();
            }
        }

        client.setPassportNumber(passportNumber);
        client.setResident(true);

        clientRepository.save(client);
    }

    @Transactional
    public void saveResidentFullName(String clientId, FullNameDto fullNameDto) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());
        clientUtil.checkIfClientIsResident(client.isResident());

        client.setFirstName(fullNameDto.getFirstName());
        client.setLastName(fullNameDto.getLastName());

        String middleName = fullNameDto.getMiddleName();
        clientUtil.checkClientMiddleName(middleName);
        client.setMiddleName(middleName);

        clientRepository.save(client);
    }

    @Transactional
    public void saveSecurityQuestionAnswer(String clientId, SecurityQuestionAnswerDto securityQADto) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());
        clientUtil.checkIfFullNameIsAbsent(client);

        client.setSecurityQuestion(securityQADto.getQuestion());
        client.setSecurityAnswer(securityQADto.getAnswer());

        clientRepository.save(client);
    }

    @Transactional
    public void acceptRBSSRules(String clientId) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());
        clientUtil.checkIfRequiredFieldIsAbsent(client);

        Role role = roleUtil.findRoleByName(RoleType.REGISTERED_CLIENT.name());

        ClientRegisteredEvent.Payload payload = eventPayloadUtil.createClientRegisteredEventPayload(client, role);
        ClientRegisteredEvent event = ClientRegisteredEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CLIENT_REGISTERED, client.getId(), VERSION, client.getId(), payload);

        eventSender.send(event);
    }
}
