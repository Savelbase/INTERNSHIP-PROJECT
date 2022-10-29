package com.rmn.toolkit.credits.query.testUtil;

import com.rmn.toolkit.credits.query.dto.response.success.CreditOrderDto;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoBuilder {

    public CreditOrderDto createCreditOrderDto() {
        return CreditOrderDto.builder().build();
    }
}
