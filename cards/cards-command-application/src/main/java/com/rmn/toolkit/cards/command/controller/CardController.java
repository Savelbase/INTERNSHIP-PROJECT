package com.rmn.toolkit.cards.command.controller;

import com.rmn.toolkit.cards.command.dto.request.CardDailyLimitDto;
import com.rmn.toolkit.cards.command.dto.request.CardStatusDto;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.cards.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.cards.command.model.Card;
import com.rmn.toolkit.cards.command.security.SecurityUtil;
import com.rmn.toolkit.cards.command.service.CardService;
import com.rmn.toolkit.cards.command.util.CardUtil;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cards")
@Tag(name = "Cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {
    private final CardService cardService;
    private final CardUtil cardUtil;
    private final SecurityUtil securityUtil;

    @PatchMapping("/status")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.command.security.AuthorityType).CARD_EDIT)")
    @Operation(summary = "Change card status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card status changed successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
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
                                    @ExampleObject(name = "Card", value = DocumentationUtil.CARD_NOT_FOUND)}))
    })
    public SuccessResponse changeCardStatus(@Valid @RequestBody CardStatusDto cardStatusDto) {
        log.info("PATCH /api/v1/cards/status");
        securityUtil.checkCurrentClientIdWithCardClientId(cardStatusDto.getCardId());
        Card card = cardUtil.findCardById(cardStatusDto.getCardId());

        if (!cardStatusDto.getStatus().equals(card.getStatus())) {
            cardService.changeCardStatusById(cardStatusDto);
            return new SuccessResponse("Card status changed successfully");
        } else {
            return new SuccessResponse("Card status was changed earlier");
        }
    }

    @PatchMapping("/limit")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.command.security.AuthorityType).CARD_EDIT)")
    @Operation(summary = "Change cards daily limits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card limit successfully changed",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request values",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Limit sum", value = DocumentationUtil.INVALID_DAILY_LIMIT_SUM),
                                    @ExampleObject(name = "Accepted", value = DocumentationUtil.CONDITIONS_MUST_BE_ACCEPTED)})),
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
    public SuccessResponse changeCardDailyLimit(@Valid @RequestBody CardDailyLimitDto cardDailyLimitDto) {
        log.info("PATCH /api/v1/cards/limit");
        securityUtil.checkCurrentClientIdWithCardClientId(cardDailyLimitDto.getCardId());
        cardService.changeCardDailyLimit(cardDailyLimitDto);
        return new SuccessResponse("Card limit successfully changed");
    }
}
