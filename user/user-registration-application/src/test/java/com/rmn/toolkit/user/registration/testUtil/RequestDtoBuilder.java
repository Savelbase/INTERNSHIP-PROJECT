package com.rmn.toolkit.user.registration.testUtil;

import com.rmn.toolkit.user.registration.dto.request.*;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoBuilder {

    public MobilePhoneDto createMobilePhoneDto(String mobilePhone) {
        MobilePhoneDto mobilePhoneDto = new MobilePhoneDto();
        mobilePhoneDto.setMobilePhone(mobilePhone);

        return mobilePhoneDto;
    }

    public VerificationCodeDto createVerificationCodeDto(String verificationCode) {
        VerificationCodeDto verificationCodeDto = new VerificationCodeDto();
        verificationCodeDto.setVerificationCode(verificationCode);

        return verificationCodeDto;
    }

    public PasswordDto createPasswordDto(String password) {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setPassword(password);

        return passwordDto;
    }

    public PassportNumberDto createPassportNumberDto(String passportNumber) {
        PassportNumberDto passportNumberDto = new PassportNumberDto();
        passportNumberDto.setPassportNumber(passportNumber);

        return passportNumberDto;
    }

    public FullNameDto createFullNameDto() {
        FullNameDto fullNameDto = new FullNameDto();
        fullNameDto.setFirstName(EndpointUrlAndConstants.TEST_VALUE);
        fullNameDto.setLastName(EndpointUrlAndConstants.TEST_VALUE);
        fullNameDto.setMiddleName(EndpointUrlAndConstants.TEST_VALUE);

        return fullNameDto;
    }

    public SecurityQuestionAnswerDto createSecurityQuestionAnswerDto() {
        SecurityQuestionAnswerDto questionAnswerDto = new SecurityQuestionAnswerDto();
        questionAnswerDto.setQuestion(EndpointUrlAndConstants.TEST_VALUE);
        questionAnswerDto.setAnswer(EndpointUrlAndConstants.TEST_VALUE);

        return questionAnswerDto;
    }

    public AcceptRBSSRulesDto createAcceptRBSSRulesDto(boolean accepted) {
        AcceptRBSSRulesDto acceptRBSSRulesDto = new AcceptRBSSRulesDto();
        acceptRBSSRulesDto.setAcceptedValue(String.valueOf(accepted));

        return acceptRBSSRulesDto;
    }
}
