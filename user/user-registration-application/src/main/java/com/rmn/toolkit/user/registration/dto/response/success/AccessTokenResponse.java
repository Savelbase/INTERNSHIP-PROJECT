package com.rmn.toolkit.user.registration.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenResponse {
    @Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJybW5fbW9iaWxlX2F1dGgiLCJzdWIiOiJlZTYwNzE1OS0yMjg1LTRkMDctYTg" +
            "xNy0zNTZlNGY4ZjU2ZmMiLCJpYXQiOjE2NTA3ODE3OTksImV4cCI6MTY1MDc4NTM5OSwiYXV0aG9yaXRpZXMiOlsiUkVHSVNUUkFUS" +
            "U9OIiwiUkVTSURFTlRfQ0xJRU5UX1JFR0lTVFJBVElPTiJdfQ.Z7IhAoC6sgdeIltWT-Tz4TW1KG6uP8OWgqfVJJGQ9mKI_mQSExTe" +
            "YfNhlStltfBS43awlBso5Qzp_bWcsdVgqw")
    private String accessToken;

    @Schema(example = "Bearer", description = "Always 'Bearer'")
    private String tokenType;

    @Schema(example = "20min", description = "Access token expiration time is 20 min")
    private Integer accessTokenExpirationTime;
}
