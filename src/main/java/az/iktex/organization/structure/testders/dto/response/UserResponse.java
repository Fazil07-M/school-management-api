package az.iktex.organization.structure.testders.dto.response;

import az.iktex.organization.structure.testders.entity.Gender;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String email,
        Gender gender,
        PassportResponse passport
) {}