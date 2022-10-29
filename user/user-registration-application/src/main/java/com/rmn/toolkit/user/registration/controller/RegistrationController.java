package com.rmn.toolkit.user.registration.controller;

import com.rmn.toolkit.user.registration.dto.request.*;
import com.rmn.toolkit.user.registration.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.success.AccessTokenResponse;
import com.rmn.toolkit.user.registration.dto.response.success.ClientIdResponse;
import com.rmn.toolkit.user.registration.dto.response.success.SuccessResponse;
import com.rmn.toolkit.user.registration.dto.response.success.VerificationCodeResponse;
import com.rmn.toolkit.user.registration.security.SecurityUtil;
import com.rmn.toolkit.user.registration.service.RegistrationService;
import com.rmn.toolkit.user.registration.util.ClientUtil;
import com.rmn.toolkit.user.registration.util.DocumentationUtil;
import com.rmn.toolkit.user.registration.util.ResponseUtil;
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
@RequestMapping("/api/v1/sign-up/clients")
@RequiredArgsConstructor
@Tag(name = "User Registration")
@Slf4j
public class RegistrationController {
    private final RegistrationService registrationService;
    private final SecurityUtil securityUtil;
    private final ClientUtil clientUtil;
    private final ResponseUtil responseUtil;

    @PostMapping("/phone")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generate access token if client doesn't yet registered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Access token generated successfully",
                    content = @Content(schema = @Schema(implementation = AccessTokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.PHONE_BAD_REQUEST))),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.ROLE_NOT_FOUND))),
            @ApiResponse(responseCode = "409", description = "User already is registered",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED)))
    })
    public AccessTokenResponse checkMobilePhoneAndGenerateToken(@Valid @RequestBody MobilePhoneDto mobilePhoneDto) {
        log.info("POST /api/v1/sign-up/clients/phone");
        String accessToken = registrationService.checkMobilePhoneAndGenerateToken(mobilePhoneDto);
        return responseUtil.createAccessToken(accessToken);
    }

    @PostMapping("/code/generation")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).REGISTRATION)")
    @Operation(summary = "Create verification code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Verification code successfully created",
                    content = @Content(schema = @Schema(implementation = VerificationCodeResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client or Role not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client" , value = DocumentationUtil.CLIENT_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND)})),
            @ApiResponse(responseCode = "409", description = "User already is registered",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED))),
            @ApiResponse(responseCode = "423", description = "Time counter not reached yet",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.GENERATE_CODE_UNAVAILABLE_YET)))
    })
    public VerificationCodeResponse createVerificationCode() {
        log.info("POST /api/v1/sign-up/clients/code/generation");
        String clientId = securityUtil.getClientIdFromSecurityContext();
        String verificationCode = registrationService.createVerificationCode(clientId);
        return responseUtil.createVerificationCode(verificationCode);
    }

    @PostMapping("/code/verification")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).REGISTRATION)")
    @Operation(summary = "Check verification code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification code successfully checked",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.VERIFICATION_CODE_BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples={ @ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED),
                                    @ExampleObject(name = "Code expired" , value = DocumentationUtil.EXPIRED_VERIFICATION_CODE),
                                    @ExampleObject(name = "Code incorrect" , value = DocumentationUtil.INCORRECT_VERIFICATION_CODE) })),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client or Verification code or Role not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples={@ExampleObject(name = "Client" , value = DocumentationUtil.CLIENT_NOT_FOUND),
                                    @ExampleObject(name = "Code" , value = DocumentationUtil.VERIFICATION_CODE_NOT_FOUND),
                                    @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND)})),
            @ApiResponse(responseCode = "409", description = "User already is registered",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED))),
            @ApiResponse(responseCode = "423", description = "Time counter not reached yet or Max limit is exceeded",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples={ @ExampleObject(name = "Max limit exceeded" , value = DocumentationUtil.MAX_LIMIT_EXCEEDED),
                                    @ExampleObject(name = "Time counter not reached yet" , value = DocumentationUtil.CHECK_CODE_UNAVAILABLE_YET)}))
    })
    public SuccessResponse checkVerificationCode(@Valid @RequestBody VerificationCodeDto verificationCodeDto) {
        log.info("POST /api/v1/sign-up/clients/code/verification");
        String clientId = securityUtil.getClientIdFromSecurityContext();
        registrationService.checkVerificationCode(clientId, verificationCodeDto.getVerificationCode());
        return new SuccessResponse("Verification code checked successfully");
    }

    @PatchMapping("/password")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).REGISTRATION)")
    @Operation(summary = "Save password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password is saved, now you can receive clientId",
                    content = @Content(schema = @Schema(implementation = ClientIdResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.PASSWORD_BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_NOT_FOUND))),
            @ApiResponse(responseCode = "409", description = "Client already is registered or Verification code is missed",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client Registered" , value = DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED),
                                        @ExampleObject(name = "Field Missing" , value = DocumentationUtil.REQUIRED_FIELD_IS_MISSING)}))
    })
    public ClientIdResponse savePassword(@Valid @RequestBody PasswordDto passwordDto) {
        log.info("PATCH /api/v1/sign-up/clients/password");
        String clientId = securityUtil.getClientIdFromSecurityContext();
        registrationService.savePassword(clientId, passwordDto);
        return new ClientIdResponse(clientId);
    }

    @PatchMapping("/{clientId}/passport")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).SELF_REGISTRATION)")
    @Operation(summary = "Save resident client passport number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resident client passport number saved",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.PASSPORT_BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client not found by id",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_NOT_FOUND))),
            @ApiResponse(responseCode = "409", description = "Client already is registered or " +
                    "isn't RF resident or Password is missed",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client registered" , value = DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED),
                                    @ExampleObject(name = "Duplicated passport number" , value = DocumentationUtil.DUPLICATED_PASSPORT_NUMBER),
                                    @ExampleObject(name = "Not Resident" , value = DocumentationUtil.NOT_RF_RESIDENT),
                                    @ExampleObject(name = "Field missing" , value = DocumentationUtil.REQUIRED_FIELD_IS_MISSING)}))
    })
    public SuccessResponse saveResidentPassportNumber(@PathVariable String clientId,
                                                      @Valid @RequestBody PassportNumberDto passportNumberDto) {
        log.info("PATCH /api/v1/sign-up/clients/{}/passport", clientId);
        clientUtil.checkIfTokenClientIdMatchClientId(clientId);
        registrationService.saveResidentPassportNumber(clientId, passportNumberDto);
        return new SuccessResponse("Resident passport number saved successfully");
    }

    @PatchMapping("/{clientId}/full-name")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).SELF_REGISTRATION)")
    @Operation(summary = "Save resident client full name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resident client full name saved",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = {@ExampleObject(name = "First name" , value = DocumentationUtil.FIRST_NAME_BAD_REQUEST),
                                    @ExampleObject(name = "Last name" , value = DocumentationUtil.LAST_NAME_BAD_REQUEST),
                                    @ExampleObject(name = "Middle name" , value = DocumentationUtil.INVALID_MIDDLE_NAME)})),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client not found by id",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_NOT_FOUND))),
            @ApiResponse(responseCode = "409", description = "Client already is registered " +
                    "or isn't RF resident or passport number is duplicated",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client registered" , value = DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED),
                                    @ExampleObject(name = "Not resident" , value = DocumentationUtil.NOT_RF_RESIDENT)}))
    })
    public SuccessResponse saveResidentFullName(@PathVariable String clientId,
                                                @Valid @RequestBody FullNameDto fullNameDto) {
        log.info("PATCH /api/v1/sign-up/clients/{}/full-name", clientId);
        clientUtil.checkIfTokenClientIdMatchClientId(clientId);
        registrationService.saveResidentFullName(clientId, fullNameDto);
        return new SuccessResponse("Resident full name saved successfully");
    }

    @PatchMapping("/{clientId}/security/question-answer")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).SELF_REGISTRATION)")
    @Operation(summary = "Save resident client security question and answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resident client security question and answer are saved",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = {@ExampleObject(name = "Question" , value = DocumentationUtil.QUESTION_BAD_REQUEST),
                                    @ExampleObject(name = "Answer" , value = DocumentationUtil.ANSWER_BAD_REQUEST)})),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client not found by id",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_NOT_FOUND))),
            @ApiResponse(responseCode = "409", description = "Client already is registered or Full name is missed",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client registered" , value = DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED),
                                    @ExampleObject(name = "Field missing" , value = DocumentationUtil.REQUIRED_FIELD_IS_MISSING)}))
    })
    public SuccessResponse saveSecurityQuestionAnswer(@PathVariable String clientId,
                                                      @Valid @RequestBody SecurityQuestionAnswerDto securityQADto) {
        log.info("PATCH /api/v1/sign-up/clients/{}/security/question-answer", clientId);
        clientUtil.checkIfTokenClientIdMatchClientId(clientId);
        registrationService.saveSecurityQuestionAnswer(clientId, securityQADto);
        return new SuccessResponse("Resident security question and answer saved successfully");
    }

    @PatchMapping("/{clientId}/rbss")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).REGISTRATION)")
    @Operation(summary = "Complete client registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client registered successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid value",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.RBSS_SHOULD_BE_ACCEPTED))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client or Role not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client" , value = DocumentationUtil.CLIENT_NOT_FOUND),
                                        @ExampleObject(name = "Role" , value = DocumentationUtil.ROLE_NOT_FOUND)})),
            @ApiResponse(responseCode = "409", description = "Client already is registered or Required fields are missed",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client registered" , value = DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED),
                                        @ExampleObject(name = "Field missing" , value = DocumentationUtil.REQUIRED_FIELD_IS_MISSING)}))
    })
    public SuccessResponse acceptRBSSRules(@PathVariable String clientId,
                                           @Valid @RequestBody AcceptRBSSRulesDto acceptRBSSRulesDto) {
        log.info("PATCH /api/v1/sign-up/clients/{}/rbss", clientId);
        clientUtil.checkIfTokenClientIdMatchClientId(clientId);
        registrationService.acceptRBSSRules(clientId);
        return new SuccessResponse("Client registered successfully");
    }
}