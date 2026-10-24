package az.iktex.organization.structure.testders.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;
// user_id unikal olmalıdır, çünki OneToOne əlaqəsində bir istifadəçiyə yalnız bir pasport uyğun gəlir.
    @OneToOne
    @JoinColumn(
        name = "user_id",
        unique = true,
        foreignKey = @ForeignKey(name = "fk_passports_user"),
        nullable = false
    )
    private User user;

    public void setUser(User user){
        this.user = user;
        
        if (user !=null && user.getPassport()!= this) {
            user.setPassport(this);
        }
    }
}
