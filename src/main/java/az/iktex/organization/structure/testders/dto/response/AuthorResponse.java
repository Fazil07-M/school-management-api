package az.iktex.organization.structure.testders.dto.response;

public record AuthorResponse(
        Long id,
        String name,
        String surname,
        String country
) {}