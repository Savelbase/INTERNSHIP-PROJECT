package com.rmn.toolkit.user.command.controller;

import com.rmn.toolkit.user.command.dto.request.CodeDto;
import com.rmn.toolkit.user.command.dto.request.NotificationDto;
import com.rmn.toolkit.user.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.service.UserDataService;
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
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users Data")
@RequiredArgsConstructor
@Slf4j
public class UserDataController {
    private final UserDataService userDataService;
    private  final SecurityUtil securityUtil;
    private  final ClientUtil clientUtil;

    @PatchMapping("/pin")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).USER_EDIT)")
    @Operation(summary = "Create PIN code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PIN code successfully created",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CODE_BAD_REQUEST))),
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
            @ApiResponse(responseCode = "423", description = "Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_BLOCKED_STATUS)))
    })
    public SuccessResponse createPinCode(@Valid @RequestBody CodeDto codeDto) {
        log.info("PATCH /api/v1/users/pin");
        String userId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(userId);
        userDataService.createPinCode(codeDto, userId);
        return new SuccessResponse("PIN code successfully created");
    }

    @PatchMapping("/email")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).USER_EDIT)")
    @Operation(summary = "Change email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email changed successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.EMAIL_IS_INCORRECT))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "423", description = "Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_BLOCKED_STATUS)))
    })
    public SuccessResponse changeEmail(@RequestParam @Email(message = "EMAIL_IS_INCORRECT") String email) {
        log.info("PATCH /api/v1/users/email");
        userDataService.changeEmail(email);
        return new SuccessResponse("Email changed successfully");
    }

    @PatchMapping("/notifications")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).USER_EDIT)")
    @Operation(summary = "Change notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification status changed successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NO_SUCH_TYPE_NOTIFICATION))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "423", description = "Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_BLOCKED_STATUS)))
    })
    public SuccessResponse changeNotification(@RequestBody @Valid NotificationDto notificationDto){
        log.info("PATCH /api/v1/users/notifications");
        userDataService.changeNotification(notificationDto);
        String notificationState = notificationDto.isState() ? "notification enabled" : "notification disabled";
        return new SuccessResponse(notificationState);
    }
}
