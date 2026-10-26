package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, unique = true, length = 20)
    private String serialNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false, length = 50)
    private String country;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            unique = true,
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_passport_user")
    )
    private User user;

    public void setUser(User user) {
        this.user = user;

        if (user != null && user.getPassport() != this) {
            user.setPassport(this);
        }
    }
}