package az.iktex.organization.structure.testders.dto.response;

import java.time.LocalDate;

public record PassportResponse(
        Long id,
        String serialNumber,
        LocalDate issueDate,
        String country
) {}