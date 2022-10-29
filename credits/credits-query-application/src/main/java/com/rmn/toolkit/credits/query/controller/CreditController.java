package com.rmn.toolkit.credits.query.controller;

import com.rmn.toolkit.credits.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.credits.query.dto.response.success.CreditDto;
import com.rmn.toolkit.credits.query.security.SecurityUtil;
import com.rmn.toolkit.credits.query.service.CreditService;
import com.rmn.toolkit.credits.query.util.DocumentationUtil;
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
@RequestMapping("/api/v1/credits")
@Tag(name = "Credits")
@RequiredArgsConstructor
@Slf4j
public class CreditController {
    private static final int MIN_PAGE_VALUE = 0;
    private static final int MIN_SIZE_VALUE = 1;

    private final CreditService creditService;
    private final SecurityUtil securityUtil;

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.query.security.AuthorityType).CREDIT_VIEW)")
    @Operation(summary = "Get a list of all client's credits with pagination", description = "Pages start from scratch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditDto.class)))),
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
                            examples = @ExampleObject(DocumentationUtil.CLIENT_NOT_FOUND)))
    })
    public List<CreditDto> getAllCreditsByClientId(@RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                                   @RequestParam(defaultValue = "5") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/credits");
        String clientId = securityUtil.getCurrentUserId();
        return creditService.getCreditsByClientId(clientId, page, size);
    }
}
