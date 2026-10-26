
package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.AuthorBook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorBookRepository extends JpaRepository<AuthorBook, Long> {

    // Derived Queries

    List<AuthorBook> findByAuthorId(Long authorId);

    List<AuthorBook> findByBookId(Long bookId);

    Optional<AuthorBook> findByAuthorIdAndBookId(Long authorId, Long bookId);

    boolean existsByAuthorIdAndBookId(Long authorId, Long bookId);

    // JPQL

    @Query("""
           SELECT ab FROM AuthorBook ab
           JOIN FETCH ab.author
           JOIN FETCH ab.book
           WHERE ab.id = :id
           """)
    Optional<AuthorBook> findByIdWithAuthorAndBook(@Param("id") Long id);

    @Query("""
        SELECT ab FROM AuthorBook ab
        JOIN FETCH ab.book
        WHERE ab.author.id = :authorId
        """)
    List<AuthorBook> findAllByAuthorIdWithBooks(@Param("authorId") Long authorId);
}
