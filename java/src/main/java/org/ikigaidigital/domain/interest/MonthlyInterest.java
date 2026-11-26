package org.ikigaidigital.domain.interest;

import org.ikigaidigital.domain.model.TimeDeposit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * One month of interest for a stored time deposit.
 *
 * <p>Days are left unchanged: a balance update applies a single month to the
 * age already stored, which is also what {@code TimeDepositCalculator} does.
 * Withdrawals stay on the deposit and are not subtracted here; interest uses
 * the stored balance. An unrecognized plan earns nothing.
 *
 * <p>The first 30 days earn nothing for every plan. Student interest stops
 * once the deposit reaches 366 days, the same boundary the existing calculator
 * uses for "after 1 year" (day 365 still earns). Premium interest starts only
 * after 45 days. The result is rounded to cents with half-up, using decimal
 * arithmetic so the stored NUMERIC balance is not passed through {@code double}.
 * {@code TimeDepositCalculator} is left unchanged.
 */
public final class MonthlyInterest {

    private static final BigDecimal MONTHS_IN_YEAR = new BigDecimal("12");
    private static final int NO_INTEREST_THROUGH_DAY = 30;

    private final List<PlanInterest> plans;

    public MonthlyInterest() {
        this(List.of(new BasicPlanInterest(), new StudentPlanInterest(), new PremiumPlanInterest()));
    }

    MonthlyInterest(List<PlanInterest> plans) {
        this.plans = List.copyOf(plans);
    }

    public TimeDeposit apply(TimeDeposit deposit) {
        BigDecimal interest = monthlyInterest(deposit);
        if (interest.signum() == 0) {
            return deposit;
        }
        return deposit.withBalance(deposit.balance().add(interest));
    }

    private BigDecimal monthlyInterest(TimeDeposit deposit) {
        if (deposit.days() <= NO_INTEREST_THROUGH_DAY) {
            return BigDecimal.ZERO;
        }
        return plans.stream()
                .filter(plan -> plan.supports(deposit.planType()))
                .map(plan -> plan.annualRate(deposit.days()))
                .findFirst()
                .filter(rate -> rate.signum() != 0)
                .map(rate -> deposit.balance().multiply(rate).divide(MONTHS_IN_YEAR, 2, RoundingMode.HALF_UP))
                .orElse(BigDecimal.ZERO);
    }
}
