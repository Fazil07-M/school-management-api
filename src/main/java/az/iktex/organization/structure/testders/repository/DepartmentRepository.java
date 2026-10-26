package az.iktex.organization.structure.testders.repository;
import az.iktex.organization.structure.testders.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

    boolean existsByName(String name);

    List<Department> findByEmployeesIsEmpty();

    @Query("""
           SELECT d FROM Department d
           LEFT JOIN FETCH d.employees
           WHERE d.id = :id
           """)
    Optional<Department> findByIdWithEmployees(@Param("id") Long id);

    @Query("""
           SELECT SIZE(d.employees)
           FROM Department d
           WHERE d.id = :id
           """)
    Integer countEmployeesByDepartmentId(@Param("id") Long id);


    
}
