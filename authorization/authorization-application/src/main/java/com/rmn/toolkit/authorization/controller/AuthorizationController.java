package com.rmn.toolkit.authorization.controller;

import com.rmn.toolkit.authorization.dto.request.PhoneAndPasswordDto;
import com.rmn.toolkit.authorization.dto.request.PhoneAndPinCodeDto;
import com.rmn.toolkit.authorization.dto.request.RefreshTokenDto;
import com.rmn.toolkit.authorization.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.authorization.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.authorization.dto.response.success.SuccessResponse;
import com.rmn.toolkit.authorization.dto.response.success.TokensAndUserIdDto;
import com.rmn.toolkit.authorization.dto.response.success.TokensResponse;
import com.rmn.toolkit.authorization.service.AuthorizationService;
import com.rmn.toolkit.authorization.util.DocumentationUtil;
import com.rmn.toolkit.authorization.service.TokenService;
import com.rmn.toolkit.authorization.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization")
@Slf4j
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final TokenService tokenService;
    private final ResponseUtil responseUtil;

    @PostMapping("/login/password")
    @Operation(summary = "Login with mobile phone and password and generate tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank client authorized and tokens are generated",
                    content = @Content(schema = @Schema(implementation = TokensResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples ={ @ExampleObject(name = "Phone" , value = DocumentationUtil.PHONE_BAD_REQUEST),
                                    @ExampleObject(name = "Password" , value = DocumentationUtil.PASSWORD_BAD_REQUEST) })),
            @ApiResponse(responseCode = "401", description = "Incorrect user password",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.INCORRECT_PASSWORD))),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples ={ @ExampleObject(name = "User" , value = DocumentationUtil.USER_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND) })),
            @ApiResponse(responseCode = "423", description = "User status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.USER_STATUS_BLOCKED)))
    })
    public TokensResponse loginWithPhoneAndPassword(@Valid @RequestBody PhoneAndPasswordDto phoneAndPasswordDto) {
        log.info("POST /api/v1/auth/login/password");
        TokensAndUserIdDto tokensAndUserIdDto = authorizationService.loginWithPhoneAndPassword(phoneAndPasswordDto);
        return responseUtil.createTokensResponse(tokensAndUserIdDto);
    }

    @PostMapping("/login/pin")
    @Operation(summary = "Login with mobile phone and PIN code and generate tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank client authorized and tokens are generated",
                    content = @Content(schema = @Schema(implementation = TokensResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples ={ @ExampleObject(name = "Phone" , value = DocumentationUtil.PHONE_BAD_REQUEST),
                                    @ExampleObject(name = "PIN code" , value = DocumentationUtil.PIN_CODE_BAD_REQUEST) })),
            @ApiResponse(responseCode = "401", description = "Incorrect user PIN code",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.INCORRECT_PIN_CODE))),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples ={ @ExampleObject(name = "User" , value = DocumentationUtil.USER_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND) })),
            @ApiResponse(responseCode = "423", description = "Application is locked or User status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples ={ @ExampleObject(name = "Application is locked" , value = DocumentationUtil.APPLICATION_IS_LOCKED),
                                    @ExampleObject(name = "User status is blocked" , value = DocumentationUtil.USER_STATUS_BLOCKED) }))
    })
    public TokensResponse loginWithPhoneAndPinCode(@Valid @RequestBody PhoneAndPinCodeDto phoneAndPinCodeDto) {
        log.info("POST /api/v1/auth/login/pin");
        TokensAndUserIdDto tokensAndUserIdDto = authorizationService.loginWithPhoneAndPinCode(phoneAndPinCodeDto);
        return responseUtil.createTokensResponse(tokensAndUserIdDto);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Generate new access and refresh tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New tokens are generated",
                    content = @Content(schema = @Schema(implementation = TokensResponse.class))),
            @ApiResponse(responseCode = "400", description = "Refresh token couldn't be empty",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.REFRESH_TOKEN_REQUIRED))),
            @ApiResponse(responseCode = "401", description = "Expired or Invalid token",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples ={@ExampleObject(name = "User" , value = DocumentationUtil.USER_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND)})),
            @ApiResponse(responseCode = "409", description = "Refresh token with no session",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.REFRESH_TOKEN_WITH_NO_SESSIONS)))
    })
    public TokensResponse refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        log.info("POST /api/v1/auth/refresh");

        String oldRefreshToken = refreshTokenDto.getRefreshToken();
        TokensAndUserIdDto tokensAndUserIdDto = tokenService.generateAccessAndRefreshTokens(oldRefreshToken);
        tokenService.deleteRefreshToken(oldRefreshToken);
        return responseUtil.createTokensResponse(tokensAndUserIdDto);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout and delete refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refresh token deleted successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Refresh token couldn't be empty",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.REFRESH_TOKEN_REQUIRED))),
            @ApiResponse(responseCode = "401", description = "Expired or Invalid token",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples ={ @ExampleObject(name = "User" , value = DocumentationUtil.USER_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND) })),
            @ApiResponse(responseCode = "409", description = "Refresh token with no session",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.REFRESH_TOKEN_WITH_NO_SESSIONS)))
    })
    public SuccessResponse logout(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        log.info("POST /api/v1/auth/logout");
        tokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        return SuccessResponse.getGeneric();
    }
}