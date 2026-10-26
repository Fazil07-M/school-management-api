
package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Derived Queries

    Optional<Author> findByName(String name);

    boolean existsByName(String name);

    List<Author> findByAuthorBooksIsEmpty();

    List<Author> findByCountry(String country);

    // JPQL

    @Query("""
        SELECT DISTINCT a FROM Author a
        LEFT JOIN FETCH a.authorBooks ab
        LEFT JOIN FETCH ab.book
        WHERE a.id = :id
        """)
    Optional<Author> findByIdWithBooks(@Param("id") Long id);

    @Query("""
           SELECT SIZE(a.authorBooks)
           FROM Author a
           WHERE a.id = :id
           """)
    Integer countBooksByAuthorId(@Param("id") Long id);
}
