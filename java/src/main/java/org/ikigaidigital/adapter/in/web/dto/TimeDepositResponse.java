package org.ikigaidigital.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * JSON representation from the OpenAPI {@code TimeDeposit} schema.
 * Balance is a decimal string because the column is PostgreSQL NUMERIC.
 */
@Schema(name = "TimeDeposit", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public record TimeDepositResponse(
        @Schema(description = "timeDeposits.id", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED)
        int id,
        @Schema(
                description = "timeDeposits.planType. The interest rules recognize basic, student, and premium.",
                example = "basic",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String planType,
        @Schema(
                description = "timeDeposits.balance",
                type = "string",
                format = "decimal",
                example = "1234567.00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String balance,
        @Schema(description = "timeDeposits.days", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED)
        int days,
        @Schema(
                description = "Withdrawals whose timeDepositId is this deposit.",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        List<WithdrawalResponse> withdrawals
) {
}
