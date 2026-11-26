package org.ikigaidigital.adapter.in.web;

import org.ikigaidigital.adapter.in.web.dto.TimeDepositResponse;
import org.ikigaidigital.application.port.in.ListTimeDepositsUseCase;
import org.ikigaidigital.application.port.in.UpdateTimeDepositBalancesUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    public List<TimeDepositResponse> listTimeDeposits() {
        return listTimeDeposits.listAll().stream()
                .map(TimeDepositResponseMapper::toResponse)
                .toList();
    }

    @PostMapping("/time-deposits/balance-updates")
    public List<TimeDepositResponse> updateBalances() {
        return updateBalances.updateAll().stream()
                .map(TimeDepositResponseMapper::toResponse)
                .toList();
    }
}
