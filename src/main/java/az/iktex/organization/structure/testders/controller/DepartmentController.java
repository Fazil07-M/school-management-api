package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.request.DepartmentRequest;
import az.iktex.organization.structure.testders.dto.response.DepartmentResponse;
import az.iktex.organization.structure.testders.entity.Department;
import az.iktex.organization.structure.testders.mapper.DepartmentMapper;
import az.iktex.organization.structure.testders.service.DepartmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    // Yeni şöbə yaradır.
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @RequestBody DepartmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(request));
    }

    // ID üzrə şöbəni tapır.
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    // Şöbəni işçiləri ilə birlikdə qaytarır.
    @GetMapping("/{id}/employees")
    public ResponseEntity<DepartmentResponse> getDepartmentWithEmployees(
            @PathVariable Long id) {

        Department department = departmentService.getDepartmentWithEmployees(id);

        return ResponseEntity.ok(departmentMapper.toResponse(department));
    }

    // Bütün şöbələri qaytarır.
    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // İşçisi olmayan şöbələri qaytarır.
    @GetMapping("/without-employees")
    public ResponseEntity<List<DepartmentResponse>> getDepartmentsWithoutEmployees() {

        return ResponseEntity.ok(
                departmentService.getDepartmentsWithoutEmployees());
    }

    // Şöbə məlumatlarını yeniləyir.
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(id, request));
    }

    // Şöbəni silir.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}