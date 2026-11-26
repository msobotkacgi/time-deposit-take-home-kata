package org.ikigaidigital.domain.interest;

import java.math.BigDecimal;

final class PremiumPlanInterest implements PlanInterest {

    private static final BigDecimal ANNUAL_RATE = new BigDecimal("0.05");
    private static final int INTEREST_STARTS_AFTER_DAY = 45;

    @Override
    public boolean supports(String planType) {
        return "premium".equals(planType);
    }

    @Override
    public BigDecimal annualRate(int days) {
        if (days <= INTEREST_STARTS_AFTER_DAY) {
            return BigDecimal.ZERO;
        }
        return ANNUAL_RATE;
    }
}
