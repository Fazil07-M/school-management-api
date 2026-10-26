package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender_id", nullable = false)
    private Gender gender;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Passport passport;

    public void setPassport(Passport passport) {
        if (this.passport != null && this.passport != passport) {
            this.passport.setUser(null);
        }

        this.passport = passport;

        if (passport != null && passport.getUser() != this) {
            passport.setUser(this);
        }
    }
}