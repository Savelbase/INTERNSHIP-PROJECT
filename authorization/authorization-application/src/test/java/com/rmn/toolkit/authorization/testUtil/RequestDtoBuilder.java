package com.rmn.toolkit.authorization.testUtil;

import com.rmn.toolkit.authorization.dto.request.PhoneAndPasswordDto;
import com.rmn.toolkit.authorization.dto.request.PhoneAndPinCodeDto;
import com.rmn.toolkit.authorization.dto.request.RefreshTokenDto;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoBuilder {

    public PhoneAndPasswordDto createPhoneAndPasswordDto(String mobilePhone, String password) {
        PhoneAndPasswordDto phoneAndPasswordDto = new PhoneAndPasswordDto();
        phoneAndPasswordDto.setMobilePhone(mobilePhone);
        phoneAndPasswordDto.setPassword(password);
        return phoneAndPasswordDto;
    }

    public PhoneAndPinCodeDto createPhoneAndPinCodeDto(String mobilePhone, String pinCode) {
        PhoneAndPinCodeDto phoneAndPinCodeDto = new PhoneAndPinCodeDto();
        phoneAndPinCodeDto.setMobilePhone(mobilePhone);
        phoneAndPinCodeDto.setPinCode(pinCode);
        return phoneAndPinCodeDto;
    }

    public RefreshTokenDto createRefreshTokenDto(String refreshToken) {
        RefreshTokenDto phoneAndPinCodeDto = new RefreshTokenDto();
        phoneAndPinCodeDto.setRefreshToken(refreshToken);
        return phoneAndPinCodeDto;
    }
}
