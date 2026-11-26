package org.ikigaidigital.domain.interest;

import java.math.BigDecimal;

/**
 * Annual interest for one plan. A new plan is another implementation registered
 * with {@link MonthlyInterest}; the 30-day rule stays outside the plan.
 */
interface PlanInterest {

    boolean supports(String planType);

    BigDecimal annualRate(int days);
}
