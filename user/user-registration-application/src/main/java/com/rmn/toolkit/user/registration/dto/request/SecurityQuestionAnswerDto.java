package com.rmn.toolkit.user.registration.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityQuestionAnswerDto {
    @NotNull
    @Schema(example = "Name of your pet")
    @Pattern(
            regexp = RegexConstants.QUESTION_AND_ANSWER_REGEX,
            message = "Security question can contain {Upper/Lower 'Eng/Ru' letters, digits and special symbols}"
    )
    private String question;

    @NotNull
    @Schema(example = "Sam")
    @Pattern(
            regexp = RegexConstants.QUESTION_AND_ANSWER_REGEX,
            message = "Security answer can contain {Upper/Lower 'Eng/Ru' letters, digits and special symbols}"
    )
    private String answer;
}
