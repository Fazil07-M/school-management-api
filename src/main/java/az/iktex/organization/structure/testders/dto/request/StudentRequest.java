package az.iktex.organization.structure.testders.dto.request;

public record StudentRequest(
        String name,
        String surname,
        Integer age
) {}