package com.rmn.toolkit.user.command.util;

import com.rmn.toolkit.user.command.exception.locked.TimeCounterNotReachedException;
import com.rmn.toolkit.user.command.exception.notfound.VerificationCodeNotFoundException;
import com.rmn.toolkit.user.command.exception.unauthorized.ExpiredVerificationCodeException;
import com.rmn.toolkit.user.command.exception.unauthorized.IncorrectVerificationCodeException;
import com.rmn.toolkit.user.command.exception.locked.MaxLimitExceededException;
import com.rmn.toolkit.user.command.model.VerificationCode;
import com.rmn.toolkit.user.command.model.type.VerificationCodeType;
import com.rmn.toolkit.user.command.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class VerificationUtil {
    private static final int MAX_VALUE_FOR_VERIFICATION_CODE = 999999;
    private static final String VERIFICATION_CODE_FORMAT = "%06d";
    private static final int VERSION = 1;
    private static final int MAX_LIMIT = 3;

    @Value("${authentication.verificationCode.timeCounterBeforeMaxAttemptSec}")
    private Integer timeCounterBeforeMaxAttemptSec;
    @Value("${authentication.verificationCode.timeCounterAfterMaxAttemptSec}")
    private Integer timeCounterAfterMaxAttemptSec;
    @Value("${authentication.verificationCode.expirationSec}")
    private Integer verificationCodeExpirationSec;
    private final VerificationRepository verificationRepository;
    private final UserUtil userUtil;

    public String createVerificationCode(String clientId , VerificationCodeType type){
        String verificationCode = generateVerificationCode();
        String verificationCodeHash = userUtil.encodeValue(verificationCode);

        ZonedDateTime currentDateTime = ZonedDateTime.now();
        ZonedDateTime expiryDateTime = currentDateTime.plus(verificationCodeExpirationSec, ChronoUnit.SECONDS);

        Optional<VerificationCode> optionalVerification = verificationRepository.findByClientIdAndVerified(clientId, false);
        VerificationCode verification;
        if (optionalVerification.isEmpty()) {
            verification = createVerification(clientId, verificationCodeHash, expiryDateTime);
        } else {
            verification = optionalVerification.get();
            checkIfValidToGenerateNewCode(verification.getNextRequestDateTime());
            int attemptCounter = verification.getAttemptCounter();
            if (attemptCounter < MAX_LIMIT) {
                editVerificationBeforeReachingMaxAttempt(verification, verificationCodeHash, expiryDateTime);
            } else {
               editVerificationAfterReachingMaxAttempt(verification);
            }
        }
        verification.setAppointment(type);
        verificationRepository.save(verification);
        return verificationCode;
    }

    public void checkVerificationCode(String verificationCode , String clientId){
        VerificationCode verification = findNotVerifiedCodeByClientId(clientId);
        checkIfMaxLimitIsExceeded(verification.isExceededMaxLimit());
        checkIfCodeIsExpired(verification.getExpiryDateTime());

        int attemptCounter = verification.getAttemptCounter();
        if (attemptCounter < MAX_LIMIT - 1) {
            checkVerificationCodeBeforeMaxLimitExceeded(verification, verificationCode);
        } else {
            checkVerificationCodeIfMaxLimitExceeded(verification, verificationCode);
        }

        saveVerified(verification, true);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int verificationCode = random.nextInt(MAX_VALUE_FOR_VERIFICATION_CODE);
        return String.format(VERIFICATION_CODE_FORMAT, verificationCode);
    }

    public void incrementAttemptCounterOfVerificationCode(VerificationCode verification) {
        verification.setAttemptCounter(verification.getAttemptCounter() + 1);
    }

    public VerificationCode createVerification(String clientId, String verificationCodeHash, ZonedDateTime expiration) {
        return VerificationCode.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .verificationCode(verificationCodeHash)
                .expiryDateTime(expiration)
                .attemptCounter(0)
                .nextRequestDateTime(ZonedDateTime.now().plus(timeCounterBeforeMaxAttemptSec, ChronoUnit.SECONDS))
                .version(VERSION)
                .build();
    }

    public void editVerificationBeforeReachingMaxAttempt(VerificationCode verification, String verificationCodeHash,
                                                         ZonedDateTime expiration) {
        verification.setVerificationCode(verificationCodeHash);
        verification.setExpiryDateTime(expiration);
        verification.setNextRequestDateTime(ZonedDateTime.now().plus(timeCounterBeforeMaxAttemptSec, ChronoUnit.SECONDS));
        verification.setExceededMaxLimit(false);
        verification.setVerified(false);
    }

    public void editVerificationAfterReachingMaxAttempt(VerificationCode verification) {
        verification.setVerificationCode(null);
        verification.setExpiryDateTime(null);
        verification.setAttemptCounter(0);
        verification.setNextRequestDateTime(ZonedDateTime.now().plus(timeCounterAfterMaxAttemptSec, ChronoUnit.SECONDS));
        verification.setExceededMaxLimit(true);
        verification.setVerified(false);
    }

    public VerificationCode findNotVerifiedCodeByClientId(String clientId) {
        return verificationRepository.findByClientIdAndVerified(clientId, false)
                .orElseThrow(() -> {
                    log.error("Verification code with clientId={} not found", clientId);
                    throw new VerificationCodeNotFoundException(clientId);
                });
    }

    public void checkIfMaxLimitIsExceeded(boolean exceededMaxLimit) {
        if (exceededMaxLimit) {
            throw new TimeCounterNotReachedException(true);
        }
    }

    public void checkIfCodeIsExpired(ZonedDateTime expiryDateTime) {
        if (expiryDateTime.isBefore(ZonedDateTime.now())) {
            throw new ExpiredVerificationCodeException();
        }
    }

    public void checkIfValidToGenerateNewCode(ZonedDateTime nextRequestDateTime) {
        if (nextRequestDateTime.isAfter(ZonedDateTime.now())) {
            throw new TimeCounterNotReachedException();
        }
    }

    public void checkVerificationCodeBeforeMaxLimitExceeded(VerificationCode verification, String verificationCode) {
        boolean validVerificationCode = matchesVerificationCode(verification, verificationCode);
        if (!validVerificationCode) {
            incrementAttemptCounterOfVerificationCode(verification);
            verificationRepository.save(verification);

            int remainingAttempts = MAX_LIMIT - verification.getAttemptCounter();
            throw new IncorrectVerificationCodeException(remainingAttempts);
        }
    }

    public void checkVerificationCodeIfMaxLimitExceeded(VerificationCode verification, String verificationCode) {
        boolean validVerificationCode = matchesVerificationCode(verification, verificationCode);
        if (!validVerificationCode) {
            editVerificationAfterReachingMaxAttempt(verification);
            verificationRepository.save(verification);

            throw new MaxLimitExceededException(timeCounterAfterMaxAttemptSec);
        }
    }

    public boolean matchesVerificationCode(VerificationCode verification, String verificationCode) {
        return new BCryptPasswordEncoder().matches(verificationCode, verification.getVerificationCode());
    }

    public void saveVerified(VerificationCode verificationCode, boolean verified) {
        verificationCode.setVerified(verified);
        verificationRepository.save(verificationCode);
    }
}
