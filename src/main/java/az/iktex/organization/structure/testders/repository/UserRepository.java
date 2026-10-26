package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.User;
import az.iktex.organization.structure.testders.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 1. Derived queries

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    List<User> findByGender(Gender gender);

    // 2. JPQL queries

    // İstifadəçini pasportu ilə birlikdə gətirir
    @Query("""
           SELECT u FROM User u
           LEFT JOIN FETCH u.passport
           WHERE u.id = :id
           """)
    Optional<User> findByIdWithPassport(@Param("id") Long id);

    // Pasportu olmayan istifadəçiləri gətirir
    @Query("""
           SELECT u FROM User u
           WHERE u.passport IS NULL
           """)
    List<User> findUsersWithoutPassport();
}