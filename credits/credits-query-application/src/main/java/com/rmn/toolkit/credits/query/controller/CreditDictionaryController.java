package com.rmn.toolkit.credits.query.controller;

import com.rmn.toolkit.credits.query.dto.response.success.CreditProductDto;
import com.rmn.toolkit.credits.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.credits.query.dto.response.success.CreditProductAgreementDto;
import com.rmn.toolkit.credits.query.service.CreditDictionaryService;
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
@RequestMapping("/api/v1/credits/products")
@RequiredArgsConstructor
@Tag(name = "Credits Products")
@Slf4j
public class CreditDictionaryController {
    private static final int MIN_PAGE_VALUE = 0;
    private static final int MIN_SIZE_VALUE = 1;

    private final CreditDictionaryService creditDictionaryService;

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.query.security.AuthorityType).CREDIT_VIEW)")
    @Operation(summary = "Get a list of all credit products with pagination", description = "Pages start from scratch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold a list of credit products",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditProductDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS)))
    })
    public List<CreditProductDto> getAllCreditProducts(@RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                                       @RequestParam(defaultValue = "5") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/credits/products");
        return creditDictionaryService.getAllCreditProducts(page, size);
    }

    @GetMapping("/{creditProductId}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.query.security.AuthorityType).CREDIT_VIEW)")
    @Operation(summary = "Get credit product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold credit product",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditProductDto.class)))),
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
                            examples = @ExampleObject(DocumentationUtil.CREDIT_PRODUCT_NOT_FOUND)))
    })
    public CreditProductDto getCreditProductById(@PathVariable String creditProductId) {
        log.info("GET /api/v1/credits/products/{}", creditProductId);
        return creditDictionaryService.getCreditProductById(creditProductId);
    }

    @GetMapping("/{creditProductId}/agreement")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.credits.query.security.AuthorityType).CREDIT_VIEW)")
    @Operation(summary = "Get credit product agreement by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold credit product agreement",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditProductAgreementDto.class)))),
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
                            examples = @ExampleObject(DocumentationUtil.CREDIT_PRODUCT_NOT_FOUND)))
    })
    public CreditProductAgreementDto getCreditProductAgreementById(@PathVariable String creditProductId) {
        log.info("GET /api/v1/credits/products/{}/agreement", creditProductId);
        return creditDictionaryService.getCreditProductAgreementById(creditProductId);
    }
}
