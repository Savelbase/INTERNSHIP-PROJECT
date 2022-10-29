package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.query.dto.response.success.CardOrderDto;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.service.CardOrderService;
import com.rmn.toolkit.cards.query.util.DocumentationUtil;
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
@Tag(name = "Cards Orders")
@Slf4j
public class CardOrderController {
    private static final int MIN_PAGE_VALUE = 0;
    private static final int MIN_SIZE_VALUE = 1;

    private final CardOrderService cardOrderService;
    private final SecurityUtil securityUtil;

    @GetMapping("/clients/{clientId}/cards/orders")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get a list of all client's card order's statuses with pagination", description = "Pages start from scratch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardOrderDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS)))
    })
    public List<CardOrderDto> getAllCardOrdersByClientId(@PathVariable String clientId,
                                                         @RequestHeader(value = "Authorization", required = false)
                                                                    @Parameter(hidden = true) String authorizationHeader,
                                                         @RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                                         @RequestParam(defaultValue = "5") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/clients/{}/cards/orders", clientId);
        securityUtil.checkPermissionToViewCardOrder(clientId, authorizationHeader);
        return cardOrderService.getAllCardOrdersByClientId(clientId, page, size);
    }

    @GetMapping("/cards/orders/{cardOrderId}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get card order status by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold card order status",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardOrderDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token", value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token", value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized", value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Card order not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CARD_ORDER_NOT_FOUND)))
    })
    public CardOrderDto getCardOrderStatusById(@PathVariable String cardOrderId,
                                               @RequestHeader(value = "Authorization", required = false)
                                                   @Parameter(hidden = true) String authorizationHeader) {
        log.info("GET /api/v1/cards/orders/{}", cardOrderId);
        String clientId = securityUtil.getCurrentUserId();
        return cardOrderService.getCardOrderByIdAndClientId(cardOrderId, clientId, authorizationHeader);
    }
}

