package com.rmn.toolkit.user.query.controller;

import com.rmn.toolkit.user.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.query.dto.response.proections.ClientDto;
import com.rmn.toolkit.user.query.model.User;
import com.rmn.toolkit.user.query.service.UserQueryService;
import com.rmn.toolkit.user.query.util.DocumentationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import javax.validation.constraints.Min;
import java.util.List;

@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
@Tag(name = "Users Query")
@Slf4j
public class UserQueryController {
    private static final int MIN_PAGE_VALUE = 0;
    private static final int MIN_SIZE_VALUE = 1;

    private final UserQueryService userQueryService;

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.query.security.AuthorityType).USER_VIEW)")
    @Operation(summary = "Get a list of all users with pagination", description = "Pages start from scratch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClientDto.class)))),
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
    public List<ClientDto> getListOfUsers(@RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                          @RequestParam(defaultValue = "5") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/users");
        return userQueryService.getUsers(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.query.security.AuthorityType).USER_VIEW)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold user",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))) ,
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_NOT_FOUND))),
            @ApiResponse(responseCode = "423", description = "Client status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CLIENT_BLOCKED_STATUS)))
    })
    public ClientDto getUserById(@PathVariable String id) {
        log.info("GET /api/v1/users/{}", id);
        return userQueryService.getUserById(id);
    }
}
