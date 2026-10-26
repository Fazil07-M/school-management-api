package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Passport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {

    // Derived Queries

    Optional<Passport> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);

    Optional<Passport> findByUserId(Long userId);

    // JPQL

    @Query("""
           SELECT p FROM Passport p
           JOIN FETCH p.user
           WHERE p.id = :id
           """)
    Optional<Passport> findByIdWithUser(@Param("id") Long id);

    @Query("""
           SELECT p FROM Passport p
           JOIN FETCH p.user
           WHERE p.user.id = :userId
           """)
    Optional<Passport> findPassportWithUserByUserId(@Param("userId") Long userId);
}