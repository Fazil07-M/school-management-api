
package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.response.EmployeeResponse;
import az.iktex.organization.structure.testders.service.DepartmentIsolationService;
import az.iktex.organization.structure.testders.service.EmployeeService;
import az.iktex.organization.structure.testders.dto.response.StudentResponse;
import az.iktex.organization.structure.testders.service.StudentService;
import az.iktex.organization.structure.testders.service.EnrollmentRetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/isolation")
@RequiredArgsConstructor
public class IsolationController {

    private final DepartmentIsolationService departmentIsolationService;
    private final EmployeeService employeeService;
    private final StudentService studentService;
    private final EnrollmentRetryService enrollmentRetryService;

    // READ_COMMITTED və REPEATABLE_READ səviyyələrində orta maaşı yoxlayır.
    @GetMapping("/departments/{id}/salary-report")
    public ResponseEntity<Map<String, Object>> getSalaryReport(
            @PathVariable Long id,
            @RequestParam String isolation,
            @RequestParam(defaultValue = "8000") long delayMs) {

        if (isolation.equalsIgnoreCase("READ_COMMITTED")) {
            return ResponseEntity.ok(
                    departmentIsolationService.readCommittedReport(id, delayMs));
        }

        if (isolation.equalsIgnoreCase("REPEATABLE_READ")) {
            return ResponseEntity.ok(
                    departmentIsolationService.repeatableReadReport(id, delayMs));
        }

        throw new IllegalArgumentException(
                "Unsupported isolation level: " + isolation);
    }

    // Paralel sorğularla maaşın artırılmasını yoxlayır.
    @PostMapping("/employees/{id}/raise")
    public ResponseEntity<EmployeeResponse> raiseSalary(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam String isolation,
            @RequestParam(defaultValue = "8000") long delayMs) {

        if (isolation.equalsIgnoreCase("READ_COMMITTED")) {
            return ResponseEntity.ok(
                    employeeService.raiseSalaryReadCommitted(id, amount, delayMs));
        }

        if (isolation.equalsIgnoreCase("REPEATABLE_READ")) {
            return ResponseEntity.ok(
                    employeeService.raiseSalaryRepeatableRead(id, amount, delayMs));
        }

        throw new IllegalArgumentException(
                "Unsupported isolation level: " + isolation);
    }

    // Paralel sorğularla kurs tutumunun yoxlanılması.
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponse> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestParam String isolation,
            @RequestParam(defaultValue = "8000") long delayMs) {

        if (isolation.equalsIgnoreCase("REPEATABLE_READ")) {
            return ResponseEntity.ok(
                    studentService.enrollWithRepeatableRead(
                            studentId, courseId, delayMs));
        }

        if (isolation.equalsIgnoreCase("SERIALIZABLE")) {
            return ResponseEntity.ok(
                    studentService.enrollWithSerializable(
                            studentId, courseId, delayMs));
        }

        throw new IllegalArgumentException(
                "Unsupported isolation level: " + isolation);
    }

        // SERIALIZABLE tranzaksiyasını maksimum 3 dəfə təkrar edir.
        @PostMapping("/students/{studentId}/courses/{courseId}/retry")
        public ResponseEntity<StudentResponse> enrollWithRetry(
                @PathVariable Long studentId,
                @PathVariable Long courseId,
                @RequestParam(defaultValue = "8000") long delayMs) {

        return ResponseEntity.ok(
                enrollmentRetryService.enrollWithRetry(
                        studentId, courseId, delayMs));
        }
}
