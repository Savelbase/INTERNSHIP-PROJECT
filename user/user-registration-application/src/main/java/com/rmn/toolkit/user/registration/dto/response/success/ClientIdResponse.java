package com.rmn.toolkit.user.registration.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientIdResponse {
    @Schema(example = "123456f7-1c8a-9aa0-ad1d-ec1234567ec")
    private String clientId;
}
