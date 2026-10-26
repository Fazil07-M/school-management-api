package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.request.EmployeeRequest;
import az.iktex.organization.structure.testders.dto.response.EmployeeResponse;
import az.iktex.organization.structure.testders.service.EmployeeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // Yeni işçi yaradır.
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(request));
    }

    // ID üzrə işçini tapır.
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Bütün işçiləri qaytarır.
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {

        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Müəyyən şöbənin işçilərini qaytarır.
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByDepartment(departmentId));
    }

    // Maaş aralığına görə işçiləri tapır.
    @GetMapping("/salary-range")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesBySalaryRange(
            @RequestParam BigDecimal minSalary,
            @RequestParam BigDecimal maxSalary) {

        return ResponseEntity.ok(
                employeeService.getEmployeesBySalaryRange(
                        minSalary, maxSalary));
    }

    // Bütün işçilərin orta maaşını qaytarır.
    @GetMapping("/average-salary")
    public ResponseEntity<BigDecimal> getAverageSalary() {

        return ResponseEntity.ok(employeeService.getAverageSalary());
    }

    // İşçi məlumatlarını yeniləyir.
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, request));
    }

    // İşçini silir.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}