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

        return gender.getDbCode();
    }

    @Override
    public Gender convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }

        return Gender.findByDbCode(value);
    }
}