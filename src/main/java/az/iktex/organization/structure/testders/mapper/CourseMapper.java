
package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.dto.request.CourseRequest;
import az.iktex.organization.structure.testders.dto.response.CourseResponse;
import az.iktex.organization.structure.testders.entity.Course;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    // Yeni Course yaradılarkən ID verilənlər bazası tərəfindən yaradılır.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    Course toEntity(CourseRequest request);

    // Course obyektindən CourseResponse yaradılır.
    CourseResponse toResponse(Course course);

    // Course siyahısı CourseResponse siyahısına çevrilir.
    List<CourseResponse> toResponseList(List<Course> courses);

    // Mövcud Course obyektinin məlumatları yenilənir.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    void updateEntity(
            CourseRequest request,
            @MappingTarget Course course
    );
}
