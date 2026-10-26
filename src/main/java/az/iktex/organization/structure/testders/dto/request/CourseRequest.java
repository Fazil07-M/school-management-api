package az.iktex.organization.structure.testders.dto.request;

public record CourseRequest(
        String title,
        Integer credit,
        String teacher
) {}