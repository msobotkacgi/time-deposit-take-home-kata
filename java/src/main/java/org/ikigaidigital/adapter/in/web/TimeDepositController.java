package org.ikigaidigital.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ikigaidigital.adapter.in.web.dto.TimeDepositResponse;
import org.ikigaidigital.application.port.in.ListTimeDepositsUseCase;
import org.ikigaidigital.application.port.in.UpdateTimeDepositBalancesUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Time deposits")
public class TimeDepositController {

    private final ListTimeDepositsUseCase listTimeDeposits;
    private final UpdateTimeDepositBalancesUseCase updateBalances;

    public TimeDepositController(
            ListTimeDepositsUseCase listTimeDeposits,
            UpdateTimeDepositBalancesUseCase updateBalances) {
        this.listTimeDeposits = listTimeDeposits;
        this.updateBalances = updateBalances;
    }

    @GetMapping("/time-deposits")
    @Operation(operationId = "listTimeDeposits", summary = "Retrieve all time deposits")
    @ApiResponse(responseCode = "200", description = "Every time deposit, including its withdrawals.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = TimeDepositResponse.class))))
    public List<TimeDepositResponse> listTimeDeposits() {
        return listTimeDeposits.listAll().stream()
                .map(TimeDepositResponseMapper::toResponse)
                .toList();
    }

    @PostMapping("/time-deposits/balance-updates")
    @Operation(
            operationId = "updateTimeDepositBalances",
            summary = "Update the balances of all time deposits",
            description = "Recalculates and stores the balance of every time deposit. No request body is accepted."
    )
    @ApiResponse(responseCode = "200", description = "Every time deposit after its balance was updated.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = TimeDepositResponse.class))))
    public List<TimeDepositResponse> updateBalances() {
        return updateBalances.updateAll().stream()
                .map(TimeDepositResponseMapper::toResponse)
                .toList();
    }
}
