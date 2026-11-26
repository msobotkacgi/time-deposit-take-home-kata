package org.ikigaidigital.domain.interest;

import org.ikigaidigital.domain.model.TimeDeposit;
import org.ikigaidigital.domain.model.Withdrawal;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MonthlyInterestTest {

    private final MonthlyInterest interest = new MonthlyInterest();

    @Test
    void basicEarnsOnePercentAfterThirtyDays() {
        assertThat(apply("basic", "1200.00", 31).balance()).isEqualByComparingTo("1201.00");
    }

    @Test
    void noPlanEarnsInterestDuringTheFirstThirtyDays() {
        assertThat(apply("basic", "1200.00", 30).balance()).isEqualByComparingTo("1200.00");
        assertThat(apply("student", "1200.00", 30).balance()).isEqualByComparingTo("1200.00");
        assertThat(apply("premium", "1200.00", 30).balance()).isEqualByComparingTo("1200.00");
    }

    @Test
    void studentEarnsThreePercentUntilOneYearAndThenNothing() {
        assertThat(apply("student", "1200.00", 365).balance()).isEqualByComparingTo("1203.00");
        assertThat(apply("student", "1200.00", 366).balance()).isEqualByComparingTo("1200.00");
    }

    @Test
    void premiumEarnsFivePercentOnlyAfterFortyFiveDays() {
        assertThat(apply("premium", "1200.00", 45).balance()).isEqualByComparingTo("1200.00");
        assertThat(apply("premium", "1200.00", 46).balance()).isEqualByComparingTo("1205.00");
    }

    @Test
    void unknownPlanAndWithdrawalsAreLeftUntouched() {
        Withdrawal withdrawal = new Withdrawal(7, 1, new BigDecimal("10.00"), LocalDate.of(2026, 1, 1));
        TimeDeposit updated = interest.apply(deposit("unknown", "1200.00", 400, List.of(withdrawal)));

        assertThat(updated.balance()).isEqualByComparingTo("1200.00");
        assertThat(updated.withdrawals()).containsExactly(withdrawal);
        assertThat(updated.days()).isEqualTo(400);
    }

    private TimeDeposit apply(String planType, String balance, int days) {
        return interest.apply(deposit(planType, balance, days, List.of()));
    }

    private static TimeDeposit deposit(String planType, String balance, int days, List<Withdrawal> withdrawals) {
        return new TimeDeposit(1, planType, new BigDecimal(balance), days, withdrawals);
    }
}
