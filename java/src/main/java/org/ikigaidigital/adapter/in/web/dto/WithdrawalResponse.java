package org.ikigaidigital.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "Withdrawal", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public record WithdrawalResponse(
        @Schema(description = "withdrawals.id", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED)
        int id,
        @Schema(
                description = "withdrawals.timeDepositId, foreign key to timeDeposits.id",
                format = "int32",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        int timeDepositId,
        @Schema(
                description = "withdrawals.amount",
                type = "string",
                format = "decimal",
                example = "100.50",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String amount,
        @Schema(description = "withdrawals.date", format = "date", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate date
) {
}
