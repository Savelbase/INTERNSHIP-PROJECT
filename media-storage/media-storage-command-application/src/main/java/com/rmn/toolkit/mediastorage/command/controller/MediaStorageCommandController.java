package com.rmn.toolkit.mediastorage.command.controller;

import com.rmn.toolkit.mediastorage.command.dto.response.SuccessResponse;
import com.rmn.toolkit.mediastorage.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.mediastorage.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.mediastorage.command.security.SecurityUtil;
import com.rmn.toolkit.mediastorage.command.service.MediaStorageCommandService;
import com.rmn.toolkit.mediastorage.command.util.DocumentationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/clients/avatar")
@RequiredArgsConstructor
@Slf4j
public class MediaStorageCommandController {
    private final MediaStorageCommandService mediaStorageCommandService;
    private final SecurityUtil securityUtil;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority(T(com.rmn.toolkit.mediastorage.command.security.AuthorityType).UPLOAD_IMAGE)")
    @Operation(summary = "Upload client avatar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "File uploaded successfully",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "File not provided or Invalid extension",
                    content = @Content(schema = @Schema(implementation = GeneralMessageErrorResponse.class),
                            examples = {@ExampleObject(name = "File not provided" , value = DocumentationUtil.FILE_NOT_PROVIDED),
                                    @ExampleObject(name = "Invalid file extension" , value = DocumentationUtil.INVALID_FILE_EXTENSION)})),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = {@ExampleObject(name = "Expired token" , value = DocumentationUtil.EXPIRED_TOKEN),
                                    @ExampleObject(name = "Invalid token" , value = DocumentationUtil.INVALID_TOKEN),
                                    @ExampleObject(name = "Unauthorized" , value = DocumentationUtil.UNAUTHORIZED)})),
            @ApiResponse(responseCode = "403", description = "Access denied exception",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.NOT_ENOUGH_RIGHTS))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples =@ExampleObject(DocumentationUtil.USER_NOT_FOUND))),
            @ApiResponse(responseCode = "409", description = "Failed to create folder",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.FAILED_CREATE_FOLDER))),
            @ApiResponse(responseCode = "413", description = "File too large",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.FILE_TOO_LARGE))),
            @ApiResponse(responseCode = "423", description = "User status is blocked",
                    content = @Content(schema = @Schema(implementation = GeneralErrorTypeErrorResponse.class),
                            examples = @ExampleObject(DocumentationUtil.USER_STATUS_BLOCKED)))
    })
    public SuccessResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException, MimeTypeException {
        log.info("POST /api/v1/clients/avatar");
        mediaStorageCommandService.saveFile(securityUtil.getCurrentUserId(), file);
        return new SuccessResponse("File uploaded successfully");
    }
}