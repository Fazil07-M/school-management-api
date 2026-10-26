package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "author_book",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_author_book",
                        columnNames = {"author_id", "book_id"}
                )
        }
)
public class AuthorBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hər bir əlaqə mütləq bir müəllifə aid olmalıdır.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_ab_author")
    )
    private Author author;

    // Hər bir əlaqə mütləq bir kitaba aid olmalıdır.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "book_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_ab_book")
    )
    private Book book;
}