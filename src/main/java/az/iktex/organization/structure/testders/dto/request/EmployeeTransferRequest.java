
package az.iktex.organization.structure.testders.dto.request;

import java.math.BigDecimal;

public record EmployeeTransferRequest(
        Long departmentId,
        BigDecimal raisePercent
) {
}
