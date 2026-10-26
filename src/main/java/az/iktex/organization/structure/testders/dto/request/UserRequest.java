package az.iktex.organization.structure.testders.dto.request;

import az.iktex.organization.structure.testders.entity.Gender;

public record UserRequest(
        String name,
        String surname,
        String email,
        Gender gender
) {}