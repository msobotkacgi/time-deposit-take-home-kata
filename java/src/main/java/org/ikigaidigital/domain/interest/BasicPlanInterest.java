package org.ikigaidigital.domain.interest;

import java.math.BigDecimal;

final class BasicPlanInterest implements PlanInterest {

    private static final BigDecimal ANNUAL_RATE = new BigDecimal("0.01");

    @Override
    public boolean supports(String planType) {
        return "basic".equals(planType);
    }

    @Override
    public BigDecimal annualRate(int days) {
        return ANNUAL_RATE;
    }
}
