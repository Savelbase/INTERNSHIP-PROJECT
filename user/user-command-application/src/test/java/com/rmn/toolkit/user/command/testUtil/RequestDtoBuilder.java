package com.rmn.toolkit.user.command.testUtil;

import com.rmn.toolkit.user.command.dto.request.*;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.model.type.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoBuilder {

    public CodeDto createCodeDto(String code) {
        CodeDto codeDto = new CodeDto();
        codeDto.setCode(code);
        return codeDto;
    }
    public PassportNumberDto createPassportDto(String number) {
        PassportNumberDto passportNumberDto = new PassportNumberDto();
        passportNumberDto.setPassportNumber(number);
        return passportNumberDto;
    }
    public PasswordDto createPasswordDto(String password) {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setPassword(password);
        return passwordDto;
    }

    public SecurityQADto createQADto(String question , String answer) {
        return new SecurityQADto(question , answer);
    }

    public Client createClient() {
        return Client.builder()
                .id(EndpointUrlAndConstants.TEST_VALUE)
                .bankClient(false)
                .build();
    }

    public ChangePasswordDto createChangePassDto(){
        return ChangePasswordDto.builder()
                .newPassword(EndpointUrlAndConstants.PASSWORD)
                .oldPassword(EndpointUrlAndConstants.PASSWORD)
                .build();
    }

    public NotificationDto createNotificationDto(){
        return NotificationDto.builder().state(true).type(NotificationType.SMS).build();
    }

    public ApprovedBankClientDto createApprovedBankClientDto() {
        return ApprovedBankClientDto.builder()
                .clientId(EndpointUrlAndConstants.TEST_VALUE)
                .approvedValue(String.valueOf(true))
                .build();
    }
}
