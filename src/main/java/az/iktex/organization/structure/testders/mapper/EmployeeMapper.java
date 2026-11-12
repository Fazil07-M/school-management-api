
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

    // Yeni işçi yaradılarkən ID və şöbə ayrıca təyin olunur.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    Employee toEntity(EmployeeRequest request);

    // İşçi məlumatları ilə birlikdə şöbənin ID və adı qaytarılır.
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    EmployeeResponse toResponse(Employee employee);

    // İşçilərin siyahısı EmployeeResponse siyahısına çevrilir.
    List<EmployeeResponse> toResponseList(List<Employee> employees);

    // Mövcud işçinin məlumatları yenilənir.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    void updateEntity(
            EmployeeRequest request,
            @MappingTarget Employee employee
    );
}
