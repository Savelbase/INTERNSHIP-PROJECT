package com.rmn.toolkit.user.command.controller;

import com.rmn.toolkit.user.command.dto.request.ApprovedBankClientDto;
import com.rmn.toolkit.user.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.user.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.service.UserCommandService;
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
@RequestMapping("/api/v1/users")
@Tag(name = "Users Command")
@RequiredArgsConstructor
@Slf4j
public class UserCommandController {
    private final UserCommandService userCommandService;
    private  final ClientUtil clientUtil;
    private  final SecurityUtil securityUtil;

    @PatchMapping("/approval")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).APPROVE_BANK_CLIENT)")
    @Operation(summary = "Approve registered non-bank client as bank-client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client approved as bank-client",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.BANK_CLIENT_SHOULD_BE_APPROVED))),
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
    public SuccessResponse approveBankClient(@Valid @RequestBody ApprovedBankClientDto approvedBankClientDto) {
        log.info("PATCH /api/v1/users/approval");
        String authorId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(authorId);

        Client client = clientUtil.findClientById(approvedBankClientDto.getClientId());
        if (!client.isBankClient()) {
            userCommandService.approveBankClient(client, authorId);
            return new SuccessResponse("Client approved as a bank-client");
        } else {
            return new SuccessResponse("Client already is a bank-client");
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.command.security.AuthorityType).DELETE_USER)")
    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
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
    })
    public SuccessResponse deleteUserById(@PathVariable String userId) {
        log.info("DELETE /api/v1/users/{}", userId);
        String authorId = securityUtil.getCurrentUserId();
        userCommandService.deleteUserById(userId, authorId);
        return new SuccessResponse("User deleted successfully");
    }
}
