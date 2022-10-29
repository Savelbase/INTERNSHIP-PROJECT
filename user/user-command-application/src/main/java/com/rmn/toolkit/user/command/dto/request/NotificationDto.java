package com.rmn.toolkit.user.command.dto.request;

import com.rmn.toolkit.user.command.model.type.NotificationType;
import com.rmn.toolkit.user.command.util.validation.EnumNamePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    @Schema(example = "SMS|PUSH|EMAIL")
    @EnumNamePattern(regexp = "SMS|PUSH|EMAIL")
    private NotificationType type;

    @NotNull
    private boolean state;
}
