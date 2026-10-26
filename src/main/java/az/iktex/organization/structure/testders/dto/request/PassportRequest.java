package az.iktex.organization.structure.testders.dto.request;

import java.time.LocalDate;

public record PassportRequest(
        String serialNumber,
        LocalDate issueDate,
        String country
) {}