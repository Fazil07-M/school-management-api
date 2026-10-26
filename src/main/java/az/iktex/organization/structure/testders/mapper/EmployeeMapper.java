package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.dto.request.EmployeeRequest;
import az.iktex.organization.structure.testders.dto.response.EmployeeResponse;
import az.iktex.organization.structure.testders.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "department", ignore = true)
    Employee toEntity(EmployeeRequest request);

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    EmployeeResponse toResponse(Employee employee);

    List<EmployeeResponse> toResponseList(List<Employee> employees);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    void updateEntity(
            EmployeeRequest request,
            @MappingTarget Employee employee
    );
}