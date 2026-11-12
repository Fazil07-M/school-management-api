
package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.dto.request.StudentRequest;
import az.iktex.organization.structure.testders.dto.response.StudentResponse;
import az.iktex.organization.structure.testders.entity.Student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = CourseMapper.class
)
public interface StudentMapper {

    // Yeni tələbə yaradılarkən ID və kurslar ayrıca idarə olunur.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Student toEntity(StudentRequest request);

    // Adi cavabda kurslar daxil edilmir.
    @Mapping(target = "courses", ignore = true)
    StudentResponse toResponse(Student student);

    // Ətraflı cavabda tələbənin kursları da göstərilir.
    @Named("withCourses")
    @Mapping(target = "courses", source = "courses")
    StudentResponse toDetailResponse(Student student);

    // Tələbələrin siyahısı StudentResponse siyahısına çevrilir.
    List<StudentResponse> toResponseList(List<Student> students);

    // Mövcud tələbənin məlumatları yenilənir.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void updateEntity(
            StudentRequest request,
            @MappingTarget Student student
    );
}
