package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.dto.request.CardReceiptsFilters;
import com.rmn.toolkit.cards.query.dto.request.CardStatementsPeriodDto;
import com.rmn.toolkit.cards.query.dto.request.TransactionTypeDto;
import com.rmn.toolkit.cards.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.query.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.cards.query.dto.response.success.ReceiptDto;
import com.rmn.toolkit.cards.query.model.projection.ReceiptView;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.service.CreatePDFService;
import com.rmn.toolkit.cards.query.service.ReceiptsService;
import com.rmn.toolkit.cards.query.util.DocumentationUtil;
import com.rmn.toolkit.cards.query.util.ReceiptsUtil;
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
import org.apache.pdfbox.io.IOUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Tag(name = "Cards Statements")
@Slf4j
public class CardStatementController {
    private static final int MIN_PAGE_VALUE = 0;
    private static final int MIN_SIZE_VALUE = 1;

    private final ReceiptsService receiptsService;
    private final CreatePDFService convertToPDFService;
    private final SecurityUtil securityUtil;
    private final ReceiptsUtil receiptsUtil;

    @GetMapping("/statements")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get a list of card statements with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a list of statements",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReceiptDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class))),
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
                                    @ExampleObject(name = "Card", value = DocumentationUtil.CARD_NOT_FOUND)}))
    })
    public List<ReceiptDto> getCardStatements(@Valid @RequestBody CardStatementsPeriodDto cardStatementsPeriodDto,
                                              @RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                              @RequestParam(defaultValue = "5") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/cards/statements");
        securityUtil.checkCurrentClientIdWithCardClientId(cardStatementsPeriodDto.getCardId());
        return receiptsService.getCardStatements(cardStatementsPeriodDto, page, size);
    }

    @GetMapping("/{cardId}/statements/doc")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get a list of all card statements for the period in PDF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a PDF doc",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)))),
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
                                    @ExampleObject(name = "Card", value = DocumentationUtil.CARD_NOT_FOUND)}))
    })
    public ResponseEntity<byte[]> getCardStatementInPDFByCardId(@PathVariable String cardId,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam ZonedDateTime startPeriod,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam ZonedDateTime endPeriod) {
        log.info("GET /api/v1/cards/{}/statements/doc", cardId);
        securityUtil.checkCurrentClientIdWithCardClientId(cardId);

        try {
            byte[] bytes = IOUtils.toByteArray(convertToPDFService.getCardStatementInPDF(cardId, startPeriod, endPeriod));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=statement" + cardId +".pdf");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(bytes.length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(IOUtils.toByteArray(convertToPDFService.getCardStatementInPDF(cardId, startPeriod, endPeriod)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/receipts")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get a list of card receipts with filters and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keep a list of card receipts",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReceiptDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class))),
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
                                    @ExampleObject(name = "Card", value = DocumentationUtil.CARD_NOT_FOUND)}))
    })
    public List<ReceiptDto> getCardReceiptsAccordingFilters(@Valid @RequestBody CardReceiptsFilters filters,
                                                            @RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                                            @RequestParam(defaultValue = "4") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/cards/receipts");
        securityUtil.checkCurrentClientIdWithCardClientId(filters.getCardId());
        return receiptsService.getReceipts(filters, page, size);
    }

    @GetMapping("/receipts/{receiptId}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get detailed receipt info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold detailed receipt info",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReceiptDto.class)))),
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
                                    @ExampleObject(name = "Card", value = DocumentationUtil.CARD_NOT_FOUND),
                                    @ExampleObject(name = "Card Receipts", value = DocumentationUtil.CARD_RECEIPTS_NOT_FOUND)}))
    })
    public ReceiptDto getReceiptById(@PathVariable String receiptId) {
        log.info("GET /api/v1/cards/receipts/{}", receiptId);
        ReceiptView receiptView = receiptsUtil.findReceiptViewById(receiptId);
        securityUtil.checkCurrentClientIdWithCardClientId(receiptView.getCard().getId());
        return receiptsUtil.createReceiptDto(receiptView);
    }

    @GetMapping("/receipts/search")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Search for all receipts by transaction type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold list of receipts",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReceiptDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class))),
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
                                    @ExampleObject(name = "Card", value = DocumentationUtil.CARD_NOT_FOUND)}))
    })
    public List<ReceiptDto> searchAllReceiptsByTransactionType(@Valid @RequestBody TransactionTypeDto transactionTypeDto,
                                                               @RequestParam(defaultValue = "0") @Min(MIN_PAGE_VALUE) int page,
                                                               @RequestParam(defaultValue = "4") @Min(MIN_SIZE_VALUE) int size) {
        log.info("GET /api/v1/cards/receipts/search");
        securityUtil.checkCurrentClientIdWithCardClientId(transactionTypeDto.getCardId());
        return receiptsService.searchAllReceiptsByTransactionType(transactionTypeDto, page, size);
    }
}
