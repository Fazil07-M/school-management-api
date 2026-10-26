package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.request.EmployeeRequest;
import az.iktex.organization.structure.testders.dto.response.EmployeeResponse;
import az.iktex.organization.structure.testders.entity.Department;
import az.iktex.organization.structure.testders.entity.Employee;
import az.iktex.organization.structure.testders.mapper.EmployeeMapper;
import az.iktex.organization.structure.testders.repository.DepartmentRepository;
import az.iktex.organization.structure.testders.repository.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    // Yeni işçi yaradır.
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Şöbə tapılmadı"));

        if (employeeRepository.existsByNameAndSurnameAndDepartmentId(
                request.name(), request.surname(), request.departmentId())) {
            throw new IllegalStateException(
                    "Bu adda işçi artıq həmin şöbədə mövcuddur");
        }

        Employee employee = employeeMapper.toEntity(request);
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    // ID üzrə işçini tapır.
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findByIdWithDepartment(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("İşçi tapılmadı"));

        return employeeMapper.toResponse(employee);
    }

    // Bütün işçiləri qaytarır.
    public List<EmployeeResponse> getAllEmployees() {

        return employeeMapper.toResponseList(
                employeeRepository.findAll());
    }

    // Müəyyən şöbənin işçilərini qaytarır.
    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId) {

        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Şöbə tapılmadı");
        }

        return employeeMapper.toResponseList(
                employeeRepository.findByDepartmentId(departmentId));
    }

    // Maaş aralığına görə işçiləri tapır.
    public List<EmployeeResponse> getEmployeesBySalaryRange(
            BigDecimal minSalary, BigDecimal maxSalary) {

        if (minSalary.compareTo(maxSalary) > 0) {
            throw new IllegalArgumentException(
                    "Minimum maaş maksimum maaşdan böyük ola bilməz");
        }

        return employeeMapper.toResponseList(
                employeeRepository.findBySalaryBetween(minSalary, maxSalary));
    }

    // Bütün işçilərin orta maaşını hesablayır.
    public BigDecimal getAverageSalary() {

        Double average = employeeRepository.findAverageSalary();

        return average == null
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(average);
    }

    // İşçi məlumatlarını yeniləyir.
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("İşçi tapılmadı"));

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Şöbə tapılmadı"));

        if (employeeRepository.existsByNameAndSurnameAndDepartmentId(
                request.name(), request.surname(), request.departmentId())
                && (!employee.getName().equals(request.name())
                || !employee.getSurname().equals(request.surname())
                || !employee.getDepartment().getId().equals(request.departmentId()))) {

            throw new IllegalStateException(
                    "Bu adda işçi artıq həmin şöbədə mövcuddür");
        }

        employeeMapper.updateEntity(request, employee);
        employee.setDepartment(department);

        return employeeMapper.toResponse(employee);
    }

    // İşçini silir.
    @Transactional
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("İşçi tapılmadı"));

        employeeRepository.delete(employee);
    }
}