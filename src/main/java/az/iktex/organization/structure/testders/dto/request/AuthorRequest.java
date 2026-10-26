package az.iktex.organization.structure.testders.dto.request;

public record AuthorRequest(
        String name,
        String surname,
        String country
) {}