package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.dto.response.success.CardRequisitesDto;
import com.rmn.toolkit.cards.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.service.CardRequisitesService;
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

@RequestMapping("/api/v1/cards")
@RestController
@Tag(name = "Cards Requisites")
@RequiredArgsConstructor
@Slf4j
public class CardRequisitesController {
    private final CardRequisitesService cardRequisitesService;
    private final SecurityUtil securityUtil;

    @GetMapping("/{cardId}/requisites")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get card requisites by card id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold card requisites",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardRequisitesDto.class)))),
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
                                    @ExampleObject(name = "Card requisites", value = DocumentationUtil.CARD_REQUISITES_NOT_FOUND)}))
    })
    public CardRequisitesDto getCardRequisitesByCardId(@PathVariable String cardId) {
        log.info("GET /api/v1/cards/{}/requisites", cardId);
        securityUtil.checkCurrentClientIdWithCardClientId(cardId);
        return cardRequisitesService.getCardRequisitesByCardId(cardId);
    }
}


