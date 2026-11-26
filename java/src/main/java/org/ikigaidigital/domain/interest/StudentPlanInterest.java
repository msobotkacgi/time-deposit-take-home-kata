package org.ikigaidigital.domain.interest;

import java.math.BigDecimal;

final class StudentPlanInterest implements PlanInterest {

    private static final BigDecimal ANNUAL_RATE = new BigDecimal("0.03");
    private static final int NO_INTEREST_FROM_DAY = 366;

    @Override
    public boolean supports(String planType) {
        return "student".equals(planType);
    }

    @Override
    public BigDecimal annualRate(int days) {
        if (days >= NO_INTEREST_FROM_DAY) {
            return BigDecimal.ZERO;
        }
        return ANNUAL_RATE;
    }
}
