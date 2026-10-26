package az.iktex.organization.structure.testders.dto.request;

public record DepartmentRequest(
        String name,
        String location,
        String phone
) {}