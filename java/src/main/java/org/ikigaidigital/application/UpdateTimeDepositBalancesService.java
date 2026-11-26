package org.ikigaidigital.application;

import org.ikigaidigital.application.port.in.UpdateTimeDepositBalancesUseCase;
import org.ikigaidigital.application.port.out.TimeDepositRepository;
import org.ikigaidigital.domain.interest.MonthlyInterest;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UpdateTimeDepositBalancesService implements UpdateTimeDepositBalancesUseCase {

    private final TimeDepositRepository timeDeposits;
    private final MonthlyInterest monthlyInterest = new MonthlyInterest();

    public UpdateTimeDepositBalancesService(TimeDepositRepository timeDeposits) {
        this.timeDeposits = timeDeposits;
    }

    @Override
    @Transactional
    public List<TimeDeposit> updateAll() {
        List<TimeDeposit> updated = timeDeposits.findAll().stream()
                .map(monthlyInterest::apply)
                .toList();
        timeDeposits.updateBalances(updated);
        return updated;
    }
}
