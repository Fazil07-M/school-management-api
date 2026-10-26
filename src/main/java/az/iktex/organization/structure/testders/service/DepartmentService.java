package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.request.DepartmentRequest;
import az.iktex.organization.structure.testders.dto.response.DepartmentResponse;
import az.iktex.organization.structure.testders.entity.Department;
import az.iktex.organization.structure.testders.mapper.DepartmentMapper;
import az.iktex.organization.structure.testders.repository.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    // Yeni şöbə yaradır.
    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByName(request.name())) {
            throw new IllegalStateException("Bu şöbə adı artıq mövcuddur");
        }

        Department department = departmentMapper.toEntity(request);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponse(savedDepartment);
    }

    // ID üzrə şöbəni tapır.
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Şöbə tapılmadı"));

        return departmentMapper.toResponse(department);
    }

    // Şöbəni işçiləri ilə birlikdə tapır.
    public Department getDepartmentWithEmployees(Long id) {

        return departmentRepository.findByIdWithEmployees(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Şöbə tapılmadı"));
    }

    // Bütün şöbələri qaytarır.
    public List<DepartmentResponse> getAllDepartments() {

        return departmentMapper.toResponseList(
                departmentRepository.findAll());
    }

    // İşçisi olmayan şöbələri qaytarır.
    public List<DepartmentResponse> getDepartmentsWithoutEmployees() {

        return departmentMapper.toResponseList(
                departmentRepository.findByEmployeesIsEmpty());
    }

    // Şöbə məlumatlarını yeniləyir.
    @Transactional
    public DepartmentResponse updateDepartment(
            Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Şöbə tapılmadı"));

        if (departmentRepository.existsByName(request.name())
                && !department.getName().equals(request.name())) {
            throw new IllegalStateException(
                    "Bu şöbə adı başqa şöbədə istifadə olunur");
        }

        departmentMapper.updateEntity(request, department);

        return departmentMapper.toResponse(department);
    }

    // İşçisi olan şöbənin silinməsinə icazə vermir.
    @Transactional
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Şöbə tapılmadı"));

        if (!department.getEmployees().isEmpty()) {
            throw new IllegalStateException(
                    "İşçisi olan şöbəni silmək mümkün deyil");
        }

        departmentRepository.delete(department);
    }
}