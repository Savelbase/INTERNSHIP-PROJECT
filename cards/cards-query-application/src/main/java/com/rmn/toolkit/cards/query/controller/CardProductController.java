package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.query.model.projection.CardProductView;
import com.rmn.toolkit.cards.query.repository.CardProductRepository;
import com.rmn.toolkit.cards.query.util.DocumentationUtil;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards/products")
@Tag(name = "Card Products")
@Slf4j
public class CardProductController {

    private final CardProductRepository cardProductRepository;

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get all card products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold card products info",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardProductView.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token", value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token", value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized", value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS)))
    })
    public List<CardProductView> getAllCardProducts() {
        return cardProductRepository.findAllBy();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get card product information by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold card product info",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardProductView.class)))),
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
                                    @ExampleObject(name = "Card", value = DocumentationUtil.CARD_PRODUCT_NOT_FOUND)}))
    })
    public CardProductView getCardProductById(@PathVariable String id){
        return cardProductRepository.getCardProductById(id);
    }

}
