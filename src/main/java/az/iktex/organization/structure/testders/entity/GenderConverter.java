package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }

        return switch (gender) {
            case MALE -> 1;
            case FEMALE -> 2;
        };
    }

    @Override
    public Gender convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            default -> throw new IllegalArgumentException(
                "Naməlum gender dəyəri: " + value
            );
        };
    }
}

