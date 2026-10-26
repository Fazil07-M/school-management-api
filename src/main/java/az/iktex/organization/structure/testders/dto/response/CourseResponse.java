package az.iktex.organization.structure.testders.dto.response;

public record CourseResponse(
        Long id,
        String title,
        Integer credit,
        String teacher
) {}