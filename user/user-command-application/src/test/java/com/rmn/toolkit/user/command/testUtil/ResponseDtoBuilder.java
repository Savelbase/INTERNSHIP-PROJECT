package com.rmn.toolkit.user.command.testUtil;

import com.rmn.toolkit.user.command.dto.response.success.AccessTokenResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseDtoBuilder {

    public AccessTokenResponse createAccessTokenResponse() {
        return AccessTokenResponse.builder()
                .accessToken(EndpointUrlAndConstants.TEST_VALUE)
                .tokenType(EndpointUrlAndConstants.TEST_VALUE)
                .accessTokenExpirationTime(EndpointUrlAndConstants.TEST_INT_VALUE)
                .build();
    }
}
