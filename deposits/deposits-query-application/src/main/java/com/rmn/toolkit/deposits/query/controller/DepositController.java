package com.rmn.toolkit.deposits.query.controller;


import com.rmn.toolkit.deposits.query.dto.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.deposits.query.dto.success.DepositDto;
import com.rmn.toolkit.deposits.query.security.SecurityUtil;
import com.rmn.toolkit.deposits.query.service.DepositService;
import com.rmn.toolkit.deposits.query.util.DocumentationUtil;
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

@RestController
@RequestMapping("/api/v1/deposits")
@Tag(name = "Deposits")
@RequiredArgsConstructor
@Slf4j
public class DepositController {

    private final DepositService depositService;
    private final SecurityUtil securityUtil;

    @GetMapping("/{clientId}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.deposits.query.security.AuthorityType).DEPOSIT_VIEW)")
    @Operation(summary = "Get a list of all client's deposits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepositDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(DocumentationUtil.DEPOSIT_NOT_FOUND),
                                    @ExampleObject(DocumentationUtil.CLIENT_NOT_FOUND)}))
    })
    public List<DepositDto> getAllDepositsByClientId(@PathVariable String clientId) {
        log.info("GET /api/v1/deposits/{}", clientId);
        return depositService.getDepositsDtoByClientId(clientId);
    }
}

