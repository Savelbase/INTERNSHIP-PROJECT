package com.rmn.toolkit.user.registration.util;

import com.rmn.toolkit.user.registration.exception.locked.MaxLimitExceededException;
import com.rmn.toolkit.user.registration.exception.locked.TimeCounterNotReachedException;
import com.rmn.toolkit.user.registration.exception.notfound.VerificationCodeNotFoundException;
import com.rmn.toolkit.user.registration.exception.unauthorized.ExpiredVerificationCodeException;
import com.rmn.toolkit.user.registration.exception.unauthorized.IncorrectVerificationCodeException;
import com.rmn.toolkit.user.registration.model.VerificationCode;
import com.rmn.toolkit.user.registration.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class VerificationCodeUtil {
    private static final int MAX_VALUE_FOR_VERIFICATION_CODE = 999999;
    private static final String VERIFICATION_CODE_FORMAT = "%06d";
    private static final int VERSION = 1;
    private static final int MAX_LIMIT = 3;

    @Value("${authentication.verificationCode.timeCounterBeforeMaxAttemptSec}")
    private Integer timeCounterBeforeMaxAttemptSec;
    @Value("${authentication.verificationCode.timeCounterAfterMaxAttemptSec}")
    private Integer timeCounterAfterMaxAttemptSec;
    private final VerificationCodeRepository verificationCodeRepository;

    public String generateVerificationCode() {
        Random random = new Random();
        int verificationCode = random.nextInt(MAX_VALUE_FOR_VERIFICATION_CODE);
        return String.format(VERIFICATION_CODE_FORMAT, verificationCode);
    }

    public void incrementAttemptCounterOfVerificationCode(VerificationCode verification) {
        verification.setAttemptCounter(verification.getAttemptCounter() + 1);
    }

    public VerificationCode createVerificationCode(String clientId, String verificationCodeHash, ZonedDateTime expiration) {
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

    public void editVerificationAfterReachingMaxAttempt(VerificationCode verificationCode) {
        verificationCode.setVerificationCode(null);
        verificationCode.setExpiryDateTime(null);
        verificationCode.setAttemptCounter(0);
        verificationCode.setNextRequestDateTime(ZonedDateTime.now().plus(timeCounterAfterMaxAttemptSec, ChronoUnit.SECONDS));
        verificationCode.setExceededMaxLimit(true);
        verificationCode.setVerified(false);
    }

    public VerificationCode findNotVerifiedCodeByClientId(String clientId) {
        return verificationCodeRepository.findByClientIdAndVerified(clientId, false)
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
            verificationCodeRepository.save(verification);

            int remainingAttempts = MAX_LIMIT - verification.getAttemptCounter();
            throw new IncorrectVerificationCodeException(remainingAttempts);
        }
    }

    public void checkVerificationCodeIfMaxLimitExceeded(VerificationCode verification, String verificationCode) {
        boolean validVerificationCode = matchesVerificationCode(verification, verificationCode);
        if (!validVerificationCode) {
            editVerificationAfterReachingMaxAttempt(verification);
            verificationCodeRepository.save(verification);

            throw new MaxLimitExceededException(timeCounterAfterMaxAttemptSec);
        }
    }

    public boolean matchesVerificationCode(VerificationCode verification, String verificationCode) {
        return new BCryptPasswordEncoder().matches(verificationCode, verification.getVerificationCode());
    }

    public void saveVerified(VerificationCode verificationCode, boolean verified) {
        verificationCode.setVerified(verified);
        verificationCodeRepository.save(verificationCode);
    }
}
