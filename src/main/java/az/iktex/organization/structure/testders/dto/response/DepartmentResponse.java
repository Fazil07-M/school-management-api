package az.iktex.organization.structure.testders.dto.response;

public record DepartmentResponse(
        Long id,
        String name,
        String location,
        String phone
) {}