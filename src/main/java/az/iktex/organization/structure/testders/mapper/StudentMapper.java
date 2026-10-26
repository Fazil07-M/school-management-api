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

    @Mapping(target = "courses", ignore = true)
    Student toEntity(StudentRequest request);

    @Mapping(target = "courses", ignore = true)
    StudentResponse toResponse(Student student);

    @Named("withCourses")
    @Mapping(target = "courses", source = "courses")
    StudentResponse toDetailResponse(Student student);

    List<StudentResponse> toResponseList(List<Student> students);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void updateEntity(
            StudentRequest request,
            @MappingTarget Student student
    );
}