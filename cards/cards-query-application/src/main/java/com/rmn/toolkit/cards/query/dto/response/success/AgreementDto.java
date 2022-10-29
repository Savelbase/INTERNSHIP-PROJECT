package com.rmn.toolkit.cards.query.dto.response.success;

import com.rmn.toolkit.cards.query.model.type.AgreementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgreementDto {

    @Schema(example = "Text of rules")
    private String agreementText;
}
