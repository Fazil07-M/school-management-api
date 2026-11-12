
package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.dto.request.DepartmentRequest;
import az.iktex.organization.structure.testders.dto.response.DepartmentResponse;
import az.iktex.organization.structure.testders.entity.Department;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    // Yeni Department yaradılarkən ID və işçilər ayrıca idarə olunur.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Department toEntity(DepartmentRequest request);

    // Department obyektindən DepartmentResponse yaradılır.
    DepartmentResponse toResponse(Department department);

    // Department siyahısı DepartmentResponse siyahısına çevrilir.
    List<DepartmentResponse> toResponseList(List<Department> departments);

    // Mövcud Department obyektinin məlumatları yenilənir.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employees", ignore = true)
    void updateEntity(
            DepartmentRequest request,
            @MappingTarget Department department
    );
}
