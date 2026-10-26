package az.iktex.organization.structure.testders.dto.request;

import java.math.BigDecimal;

public record BookRequest(
        String title,
        BigDecimal price,
        Integer pageCount
) {}