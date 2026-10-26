package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Derived Queries

    // Kursu adına görə tapır
    Optional<Course> findByTitle(String title);

    // Kursun mövcud olub-olmadığını yoxlayır
    boolean existsByTitle(String title);

    // Tələbəsi olmayan kursları tapır
    List<Course> findByStudentsIsEmpty();

    // JPQL

    // Kursu tələbələri ilə birlikdə gətirir
    @Query("""
           SELECT c FROM Course c
           LEFT JOIN FETCH c.students
           WHERE c.id = :id
           """)
    Optional<Course> findByIdWithStudents(@Param("id") Long id);

    // Kursdakı tələbələrin sayını hesablayır
    @Query("""
           SELECT SIZE(c.students)
           FROM Course c
           WHERE c.id = :id
           """)
    Integer countStudentsByCourseId(@Param("id") Long id);
}