package com.rmn.toolkit.user.command.controller;

import com.rmn.toolkit.user.command.dto.request.ChangePasswordDto;
import com.rmn.toolkit.user.command.dto.request.SecurityQADto;
import com.rmn.toolkit.user.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.user.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.service.UserSecurityService;
import com.rmn.toolkit.user.command.util.ClientUtil;
import com.rmn.toolkit.user.command.util.DocumentationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/security")
@Tag(name = "Users Security")
@RequiredArgsConstructor
@Slf4j
public class UserSecurityController {
    private final SecurityUtil securityUtil;
    private final ClientUtil clientUtil;
    private final UserSecurityService securityService;

    @PatchMapping("/question-answer")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).USER_EDIT)")
    @Operation(summary = "Change question and answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question and Answer changed successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "423", description = "Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_BLOCKED_STATUS)))
    })
    public SuccessResponse changeSecurityQuestion(@Valid @RequestBody SecurityQADto qaDto){
        log.info("PATCH /api/v1/users/security/question-answer");
        String clientId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(clientId);
        securityService.changeQA(qaDto , clientId);
        return new SuccessResponse("Question and Answer changed successfully");
    }

    @PatchMapping("/password")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).USER_EDIT)")
    @Operation(summary = "Change password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "423", description = "Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_BLOCKED_STATUS)))
    })
    public SuccessResponse changePassword(@Valid @RequestBody ChangePasswordDto passwordDto){
        log.info("PATCH /api/v1/users/security/password");
        String clientId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(clientId);
        securityService.changePassword(passwordDto , clientId);
        return new SuccessResponse("Password changed successfully");
    }
}
