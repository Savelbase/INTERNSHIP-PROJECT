package com.rmn.toolkit.authorization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
    @NotBlank(message = "Refresh token is required")
    @Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJybW5fbW9iaWxlX2F1dGgiLCJzdWIiOiIwZDNhNjhhMS01OTE5LTQ5MTQtY" +
            "mMyMC04MzlmYWUyNDgwYWMiLCJpYXQiOjE2NTA3OTYzNTIsImV4cCI6MTY1MDgyNTE1Mn0.Ugqx7tRxZobQb-Tbu8FoSdRC1b2L" +
            "xubZeknlvMd9kU1LFVmy85G-o8t01JH-bnuJd-jHyrzY-CIToPr9IHsBFw")
    private String refreshToken;
}