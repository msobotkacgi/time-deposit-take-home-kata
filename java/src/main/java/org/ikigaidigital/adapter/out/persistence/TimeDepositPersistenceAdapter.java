package org.ikigaidigital.adapter.out.persistence;

import org.ikigaidigital.application.port.out.TimeDepositRepository;
import org.ikigaidigital.domain.model.TimeDeposit;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TimeDepositPersistenceAdapter implements TimeDepositRepository {

    private final TimeDepositJpaRepository timeDeposits;

    public TimeDepositPersistenceAdapter(TimeDepositJpaRepository timeDeposits) {
        this.timeDeposits = timeDeposits;
    }

    @Override
    public List<TimeDeposit> findAll() {
        return timeDeposits.findAllByOrderByIdAsc().stream()
                .map(TimeDepositEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void updateBalances(List<TimeDeposit> deposits) {
        Map<Integer, BigDecimal> balances = deposits.stream()
                .collect(Collectors.toMap(TimeDeposit::id, TimeDeposit::balance));
        timeDeposits.findAllByOrderByIdAsc().forEach(entity -> {
            BigDecimal balance = balances.get(entity.getId());
            if (balance != null) {
                entity.setBalance(balance);
            }
        });
    }
}
