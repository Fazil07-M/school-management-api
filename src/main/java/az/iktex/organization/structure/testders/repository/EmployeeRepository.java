package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Derived Queries

    // Müəyyən şöbənin işçilərini tapır
    List<Employee> findByDepartmentId(Long departmentId);

    // Maaş aralığına görə işçiləri tapır
    List<Employee> findBySalaryBetween(
            BigDecimal minSalary,
            BigDecimal maxSalary
    );

    // Şöbədə eyni adlı və soyadlı işçinin olub-olmadığını yoxlayır
    boolean existsByNameAndSurnameAndDepartmentId(
            String name,
            String surname,
            Long departmentId
    );

    // JPQL

    // İşçini şöbəsi ilə birlikdə gətirir
    @Query("""
           SELECT e FROM Employee e
           JOIN FETCH e.department
           WHERE e.id = :id
           """)
    Optional<Employee> findByIdWithDepartment(@Param("id") Long id);

    // Bütün işçilərin orta maaşını hesablayır
    @Query("""
           SELECT AVG(e.salary)
           FROM Employee e
           """)
    Double findAverageSalary();
}