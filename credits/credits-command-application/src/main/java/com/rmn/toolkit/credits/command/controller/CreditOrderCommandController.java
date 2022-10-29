package com.rmn.toolkit.credits.command.controller;

import com.rmn.toolkit.credits.command.dto.request.CreditOrderDto;
import com.rmn.toolkit.credits.command.dto.request.CreditOrderStatusDto;
import com.rmn.toolkit.credits.command.dto.response.success.CreditOrderIdResponse;
import com.rmn.toolkit.credits.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.credits.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.credits.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.credits.command.model.CreditOrder;
import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.command.security.SecurityUtil;
import com.rmn.toolkit.credits.command.service.CreditOrderService;
import com.rmn.toolkit.credits.command.util.CreditOrderUtil;
import com.rmn.toolkit.credits.command.util.DocumentationUtil;
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
@RequestMapping("/api/v1/credits/orders")
@RequiredArgsConstructor
@Tag(name = "Credits Orders")
@Slf4j
public class CreditOrderCommandController {
    private final CreditOrderService creditOrderService;
    private final SecurityUtil securityUtil;
    private final CreditOrderUtil creditOrderUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.command.security.AuthorityType).CREDIT_EDIT)")
    @Operation(summary = "Create credit order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credit order created successfully, hold credit order id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditOrderIdResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = {@ExampleObject(name = "Credit month period" , value = DocumentationUtil.INVALID_CREDIT_MONTH_PERIOD),
                                    @ExampleObject(name = "Decimal values" , value = DocumentationUtil.INVALID_DECIMAL_VALUE)})),
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
                                    @ExampleObject(name = "Credit product", value = DocumentationUtil.CREDIT_PRODUCT_NOT_FOUND)}))
    })
    public CreditOrderIdResponse createCreditOrder(@Valid @RequestBody CreditOrderDto creditOrderDto) {
        log.info("POST /api/v1/credits/orders");
        String clientId = securityUtil.getCurrentUserId();
        CreditOrder creditOrder = creditOrderService.createCreditOrder(clientId, creditOrderDto);
        return new CreditOrderIdResponse(creditOrder.getId());
    }

    @PatchMapping
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.command.security.AuthorityType).EDIT_CREDIT_ORDER_STATUS)")
    @Operation(summary = "Change credit order status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit order status changed successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid client values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
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
                                    @ExampleObject(name = "Credit order", value = DocumentationUtil.CREDIT_ORDER_NOT_FOUND)}))
    })
    public SuccessResponse changeCreditOrderStatus(@Valid @RequestBody CreditOrderStatusDto creditOrderStatusDto) {
        log.info("PATCH /api/v1/credits/orders");

        CreditOrder creditOrder = creditOrderUtil.findCreditOrderById(creditOrderStatusDto.getCreditOrderId());
        if (CreditOrderStatusType.PROCESSING.equals(creditOrder.getStatus())) {
            String authorId = securityUtil.getCurrentUserId();
            creditOrderService.changeCreditOrderStatusById(creditOrder, creditOrderStatusDto, authorId);
            return new SuccessResponse("Credit order status changed successfully");
        } else {
            return new SuccessResponse("Credit order status was changed earlier");
        }
    }
}
