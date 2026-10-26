
package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Derived Queries

    Optional<Book> findByTitle(String title);

    boolean existsByTitle(String title);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorBooksIsEmpty();


    // JPQL

    @Query("""
        SELECT DISTINCT b FROM Book b
        LEFT JOIN FETCH b.authorBooks ab
        LEFT JOIN FETCH ab.author
        WHERE b.id = :id
        """)
    Optional<Book> findByIdWithAuthors(@Param("id") Long id);

    @Query("""
           SELECT SIZE(b.authorBooks)
           FROM Book b
           WHERE b.id = :id
           """)
    Integer countAuthorsByBookId(@Param("id") Long id);
}
