package com.rmn.toolkit.user.registration.controller;

import com.rmn.toolkit.user.registration.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.success.*;
import com.rmn.toolkit.user.registration.security.SecurityUtil;
import com.rmn.toolkit.user.registration.service.RulesQueryService;
import com.rmn.toolkit.user.registration.util.DocumentationUtil;
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
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
@Tag(name = "Rules Query")
@Slf4j
public class RulesQueryController {
    private final RulesQueryService rulesQueryService;
    private final SecurityUtil securityUtil;

    @GetMapping("/privacy-policy")
    @Operation(summary = "Get privacy policy text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Privacy policy found",
                    content = @Content(schema = @Schema(implementation = RulesTextResponse.class))),
            @ApiResponse(responseCode = "404", description = "Privacy policy not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.RULES_NOT_FOUND)))
    })
    public RulesTextResponse getPrivacyPolicyText() {
        log.info("GET /api/v1/rules/privacy-policy");
        String text = rulesQueryService.getPrivacyPolicyText();
        return new RulesTextResponse(text);
    }

    @GetMapping("/rbss")
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.user.registration.security.AuthorityType).REGISTRATION)")
    @Operation(summary = "Get RBSS rules text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RBSS rules found",
                    content = @Content(schema = @Schema(implementation = RulesTextResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access  denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "Client or RBSS rules not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = { @ExampleObject(name = "Rules" , value = DocumentationUtil.RULES_NOT_FOUND),
                                    @ExampleObject(name = "Client" , value = DocumentationUtil.CLIENT_NOT_FOUND) })),
            @ApiResponse(responseCode = "409", description = "Client already is registered or Required fields are missed",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = { @ExampleObject(name = "Client already is registered" , value = DocumentationUtil.CLIENT_ALREADY_IS_REGISTERED),
                                    @ExampleObject(name = "Required field is missing" , value = DocumentationUtil.REQUIRED_FIELD_IS_MISSING) }))
    })
    public RulesTextResponse getRBSSRulesText() {
        log.info("GET /api/v1/rules/rbss");
        String clientId = securityUtil.getClientIdFromSecurityContext();
        String text = rulesQueryService.getRBSSRulesText(clientId);
        return new RulesTextResponse(text);
    }
}