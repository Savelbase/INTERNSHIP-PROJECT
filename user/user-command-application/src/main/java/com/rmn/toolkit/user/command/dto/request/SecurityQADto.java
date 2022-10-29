package com.rmn.toolkit.user.command.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityQADto {
    @NotNull
    private String question;

    @NotNull
    private String answer;
}
