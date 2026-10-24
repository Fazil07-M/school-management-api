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
// user_id unikal olmalıdır, çünki OneToOne əlaqəsində bir istifadəçiyə yalnız bir pasport uyğun gəlir.
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
    @Column(nullable = false)
    private Gender gender;

    @OneToOne(mappedBy = "user")
    private Passport passport;

    public void setPassport(Passport passport) {
        this.passport = passport;

        if (passport != null && passport.getUser() != this) {
            passport.setUser(this);
        }
    }
}