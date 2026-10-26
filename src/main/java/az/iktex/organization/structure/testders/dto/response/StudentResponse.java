package az.iktex.organization.structure.testders.dto.response;

import java.util.Set;

public record StudentResponse(
        Long id,
        String name,
        String surname,
        Integer age,
        Set<CourseResponse> courses
) {}