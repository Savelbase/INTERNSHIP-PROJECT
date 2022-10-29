package com.rmn.toolkit.mediastorage.query.controller;

import com.rmn.toolkit.mediastorage.query.dto.response.SuccessResponse;
import com.rmn.toolkit.mediastorage.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.mediastorage.query.service.MediaStorageQueryService;
import com.rmn.toolkit.mediastorage.query.util.DocumentationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Slf4j
public class MediaStorageQueryController {
    private final MediaStorageQueryService mediaStorageQueryService;

    @GetMapping(value = "/{clientId}/avatar", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.mediastorage.query.security.AuthorityType).VIEW_IMAGE)")
    @Operation(summary = "Get client avatar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hold avatar",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "User or File not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "User" , value = DocumentationUtil.USER_NOT_FOUND),
                                    @ExampleObject(name = "File" , value = DocumentationUtil.FILE_NOT_FOUND)})),
            @ApiResponse(responseCode = "423", description = "User status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.USER_STATUS_BLOCKED)))
    })
    public byte[] loadFileByClientId(@PathVariable String clientId) {
        log.info("GET /api/v1/clients/{}/avatar", clientId);
        return mediaStorageQueryService.getFileByClientId(clientId);
    }
}
