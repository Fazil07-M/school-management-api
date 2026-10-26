package az.iktex.organization.structure.testders.entity;

import java.util.Arrays;

public enum Gender {

    MALE(1),
    FEMALE(2);

    private final Integer dbCode;

    Gender(Integer dbCode) {
        this.dbCode = dbCode;
    }

    public Integer getDbCode() {
        return dbCode;
    }

    public static Gender findByDbCode(Integer dbCode) {
        return Arrays.stream(values())
                .filter(gender -> gender.getDbCode().equals(dbCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Naməlum gender dəyəri: " + dbCode
                ));
    }
}