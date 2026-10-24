
package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "employees",
    check = @CheckConstraint(
        name = "chk_employee_salary_positive",
        constraint = "salary > 0"
    )
)

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;
// BigDecimal pul dəyərlərində dəqiq hesablamalar aparmaq üçün istifadə olunur.

// Cascade seçilmir, çünki işçi silindikdə şöbə silinməməlidir.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",
        foreignKey = @ForeignKey(name = "fk_employees_department")
    )
    private Department department;
}
