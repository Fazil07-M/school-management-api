package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Derived Queries

    List<Student> findByName(String name);

    List<Student> findBySurname(String surname);

    List<Student> findByCoursesIsEmpty();

    // JPQL

    @Query("""
           SELECT s FROM Student s
           LEFT JOIN FETCH s.courses
           WHERE s.id = :id
           """)
    Optional<Student> findByIdWithCourses(@Param("id") Long id);

    @Query("""
           SELECT SIZE(s.courses)
           FROM Student s
           WHERE s.id = :id
           """)
    Integer countStudentCourses(@Param("id") Long id);
}