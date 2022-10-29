package com.rmn.toolkit.user.query.dto.response.proections;

import com.rmn.toolkit.user.query.model.type.ClientStatusType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientDto {
    String id,
            firstName,
            lastName,
            middleName,
            passportNumber,
            mobilePhone;
    boolean resident,
            bankClient;
    ClientStatusType status;
    UserDto user;
}
