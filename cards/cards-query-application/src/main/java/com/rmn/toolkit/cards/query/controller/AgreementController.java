package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.query.dto.response.success.AgreementDto;
import com.rmn.toolkit.cards.query.model.type.AgreementType;
import com.rmn.toolkit.cards.query.service.AgreementQueryService;
import com.rmn.toolkit.cards.query.util.DocumentationUtil;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Tag(name = "Cards Agreements")
@Slf4j
public class AgreementController {
   private final AgreementQueryService agreementQueryService;

    @GetMapping("/agreements/{agreementType}")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.cards.query.security.AuthorityType).CARD_VIEW)")
    @Operation(summary = "Get agreement text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agreement were received",
                    content = @Content(schema = @Schema(implementation = AgreementDto.class))),
            @ApiResponse(responseCode = "404", description = "Agreement not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.AGREEMENT_NOT_FOUND)))
    })
    public AgreementDto getAgreementText(@PathVariable AgreementType agreementType) {
        log.info("GET /api/v1/cards/agreement/{}", agreementType);
        return agreementQueryService.getAgreementText(agreementType, true);
    }
}
