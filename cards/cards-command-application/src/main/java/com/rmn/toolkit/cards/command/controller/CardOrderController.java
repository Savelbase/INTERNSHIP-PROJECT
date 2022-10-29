package com.rmn.toolkit.cards.command.controller;

import com.rmn.toolkit.cards.command.dto.request.CardOrderDto;
import com.rmn.toolkit.cards.command.dto.request.CardOrderStatusDto;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.command.dto.response.success.CardOrderIdResponse;
import com.rmn.toolkit.cards.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.type.CardOrderStatusType;
import com.rmn.toolkit.cards.command.security.SecurityUtil;
import com.rmn.toolkit.cards.command.service.CardOrderService;
import com.rmn.toolkit.cards.command.util.CardOrderUtil;
import com.rmn.toolkit.cards.command.util.DocumentationUtil;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cards/orders")
@Tag(name = "Cards Orders")
@RequiredArgsConstructor
@Slf4j
public class CardOrderController {
    private final CardOrderService cardOrderService;
    private final SecurityUtil securityUtil;
    private final CardOrderUtil cardOrderUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.command.security.AuthorityType).CARD_EDIT)")
    @Operation(summary = "Create card order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card order created successfully, hold card order id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardOrderIdResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.CONDITIONS_MUST_BE_ACCEPTED))),
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
                            examples = {@ExampleObject(name = "Client", value = DocumentationUtil.CLIENT_NOT_FOUND),
                                    @ExampleObject(name = "Card product", value = DocumentationUtil.CARD_PRODUCT_NOT_FOUND)}))
    })
    public CardOrderIdResponse createCardOrder(@Valid @RequestBody CardOrderDto cardOrderDto) {
        log.info("POST /api/v1/cards/orders");
        String clientId = securityUtil.getCurrentUserId();
        CardOrder cardOrder = cardOrderService.createCardOrder(cardOrderDto, clientId);
        return new CardOrderIdResponse(cardOrder.getId());
    }

    @PatchMapping
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.command.security.AuthorityType).EDIT_CARD_ORDER_STATUS)")
    @Operation(summary = "Change card order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit order status changed successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NO_SUCH_TYPE_STATUS))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token", value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token", value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized", value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Client", value = DocumentationUtil.CLIENT_NOT_FOUND),
                                    @ExampleObject(name = "Card order", value = DocumentationUtil.CARD_ORDER_NOT_FOUND)}))
    })
    public SuccessResponse changeCardOrderStatus(@Valid @RequestBody CardOrderStatusDto cardOrderStatusDto) {
        log.info("PATCH /api/v1/cards/orders");

        CardOrder cardOrder = cardOrderUtil.findCardOrderById(cardOrderStatusDto.getCardOrderId());
        if (CardOrderStatusType.PROCESSING.equals(cardOrder.getStatus())) {
            String authorId = securityUtil.getCurrentUserId();
            cardOrderService.changeCardOrderStatusById(cardOrder, cardOrderStatusDto, authorId);
            return new SuccessResponse("Card order status changed successfully");
        } else {
            return new SuccessResponse("Card order status was changed earlier");
        }
    }

}
