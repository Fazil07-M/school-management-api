package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "books",
        check = @CheckConstraint(
                name = "chk_book_price",
                constraint = "price >= 0"
        )
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    // Müəllif-kitab əlaqələrinin həyat dövrünü kitab idarə edir.
    // Kitab silindikdə ona aid AuthorBook qeydləri də silinir.
    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<AuthorBook> authorBooks = new ArrayList<>();
}