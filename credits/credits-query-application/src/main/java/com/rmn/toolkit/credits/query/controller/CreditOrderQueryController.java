package com.rmn.toolkit.credits.query.controller;

import com.rmn.toolkit.credits.query.dto.response.success.CreditOrderDto;
import com.rmn.toolkit.credits.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.credits.query.security.SecurityUtil;
import com.rmn.toolkit.credits.query.service.CreditOrderService;
import com.rmn.toolkit.credits.query.util.DocumentationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
@Tag(name = "Credits Orders")
@Slf4j
public class CreditOrderQueryController {
    private static final int MIN_PAGE_VALUE = 0;
    private static final int MIN_SIZE_VALUE = 1;

    private final CreditOrderService creditOrderService;
    private final SecurityUtil securityUtil;

    @GetMapping("/clients/{clientId}/credits/orders")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.query.security.AuthorityType).CREDIT_VIEW)")
    @Operation(summary = "Get a list of all client's credit order statuses with pagination", description = "Pages start from scratch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditOrderDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS)))
    })
    public List<CreditOrderDto> getAllCreditOrderStatusesByClientId(@PathVariable String clientId,
                                                                    @RequestHeader(value = "Authorization", required = false)
                                                                    @Parameter(hidden = true) String authorizationHeader,
                                                                    @RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                                                    @RequestParam(defaultValue = "5") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/clients/{}/credits/orders", clientId);
        securityUtil.checkPermissionToViewCredits(clientId, authorizationHeader);
        return creditOrderService.getCreditOrdersByClientId(clientId, page, size);
    }

    @GetMapping("/credits/orders/{creditOrderId}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.query.security.AuthorityType).CREDIT_VIEW)")
    @Operation(summary = "Get credit order status by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold credit order status",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditOrderDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token", value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token", value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized", value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Credit order not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CREDIT_ORDER_NOT_FOUND)))
    })
    public CreditOrderDto getCreditOrderStatusById(@PathVariable String creditOrderId,
                                                   @RequestHeader(value = "Authorization", required = false)
                                                   @Parameter(hidden = true) String authorizationHeader) {
        log.info("GET /api/v1/credits/orders/{}", creditOrderId);
        String clientId = securityUtil.getCurrentUserId();
        return creditOrderService.getCreditOrderByIdAndClientId(creditOrderId, clientId, authorizationHeader);
    }
}

