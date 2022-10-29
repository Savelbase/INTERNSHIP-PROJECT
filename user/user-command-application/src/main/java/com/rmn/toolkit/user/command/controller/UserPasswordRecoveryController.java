package com.rmn.toolkit.user.command.controller;

import com.rmn.toolkit.user.command.dto.request.CodeDto;
import com.rmn.toolkit.user.command.dto.request.PassportNumberDto;
import com.rmn.toolkit.user.command.dto.request.PasswordDto;
import com.rmn.toolkit.user.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.command.dto.response.success.AccessTokenResponse;
import com.rmn.toolkit.user.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.user.command.dto.response.success.VerificationCodeWithTokenResponse;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.service.PasswordRecoveryService;
import com.rmn.toolkit.user.command.util.ClientUtil;
import com.rmn.toolkit.user.command.util.DocumentationUtil;
import com.rmn.toolkit.user.command.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/password/recovery")
@Tag(name = "Password Recovery")
@Slf4j
public class UserPasswordRecoveryController {
    private final PasswordRecoveryService passwordRecoveryService;
    private final ResponseUtil responseUtil;
    private final SecurityUtil securityUtil;
    private final ClientUtil clientUtil;

    @PostMapping("/code/generation")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = " Create Verification Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Verification code successfully created",
                    content = @Content(schema = @Schema(implementation = VerificationCodeWithTokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.PASSPORT_BAD_REQUEST))),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client or Role not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client" , value = DocumentationUtil.CLIENT_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND)})),
            @ApiResponse(responseCode = "423", description = "Time counter not reached yet or Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = {@ExampleObject(name = "Time counter not reached yet" , value = DocumentationUtil.GENERATE_CODE_UNAVAILABLE_YET),
                                    @ExampleObject(name = "Client status is blocked" , value = DocumentationUtil.CLIENT_BLOCKED_STATUS)}))
    })
    public VerificationCodeWithTokenResponse createVerificationCode(@RequestBody @Valid PassportNumberDto passportNumber) {
        log.info("POST /api/v1/password/recovery/code/generation");

        Client client = clientUtil.findClientByPassportNumber(passportNumber.getPassportNumber());
        clientUtil.checkIfClientIsBlocked(client.getId());
        String verificationCode = passwordRecoveryService.createVerificationCode(client.getId());

        String accessToken = passwordRecoveryService.checkClientByMobilePhoneAndGenerateToken(client);
        return responseUtil.getVerificationCodeWithTokenResponse(verificationCode, accessToken);
    }

    @PostMapping("/code/verification")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).USER_EDIT)")
    @Operation(summary = "Check Verification code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification code successfully checked",
                    content = @Content(schema = @Schema(implementation = AccessTokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CODE_BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED),
                                    @ExampleObject(name = "Code expired" , value = DocumentationUtil.EXPIRED_VERIFICATION_CODE),
                                    @ExampleObject(name = "Incorrect code" , value = DocumentationUtil.INCORRECT_VERIFICATION_CODE)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client or Verification code or Role not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client" , value = DocumentationUtil.CLIENT_NOT_FOUND),
                                    @ExampleObject(name = "Code" , value = DocumentationUtil.VERIFICATION_CODE_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND)})),
            @ApiResponse(responseCode = "423", description = "Time counter not reached yet or Max limit is exceeded," +
                    "or Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = {@ExampleObject(name = "Max limit is exceeded" , value = DocumentationUtil.MAX_LIMIT_IS_EXCEEDED),
                                    @ExampleObject(name = "Check code unavailable yet" , value = DocumentationUtil.CHECK_CODE_UNAVAILABLE_YET),
                                    @ExampleObject(name = "Client status is blocked" , value = DocumentationUtil.CLIENT_BLOCKED_STATUS)}))
    })
    public AccessTokenResponse checkVerificationCode(@Valid @RequestBody CodeDto verificationCodeDto) {
        log.info("POST /api/v1/password/recovery/code/verification");

        String clientId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(clientId);
        passwordRecoveryService.checkVerificationCode(verificationCodeDto.getCode(), clientId);

        Client client = clientUtil.findClientById(clientId);
        String accessToken = passwordRecoveryService.createAccessTokenWithPasswordRecoveryAuthorityType(client);
        return responseUtil.createAccessToken(accessToken);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).PASSWORD_RECOVERY)")
    @Operation(summary = "Create new password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password recovered successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.PASSWORD_BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.USER_NOT_FOUND))),
            @ApiResponse(responseCode = "423", description = "Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_BLOCKED_STATUS)))
    })
    public SuccessResponse createPassword(@Valid @RequestBody PasswordDto passwordDto) {
        log.info("PATCH /api/v1/password/recovery");
        String clientId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(clientId);
        passwordRecoveryService.savePassword(passwordDto, clientId);
        return new SuccessResponse("Password recovered successfully");
    }
}
