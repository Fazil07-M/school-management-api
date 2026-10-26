package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.request.CourseRequest;
import az.iktex.organization.structure.testders.dto.response.CourseResponse;
import az.iktex.organization.structure.testders.dto.response.StudentResponse;
import az.iktex.organization.structure.testders.entity.Course;
import az.iktex.organization.structure.testders.entity.Student;
import az.iktex.organization.structure.testders.mapper.CourseMapper;
import az.iktex.organization.structure.testders.mapper.StudentMapper;
import az.iktex.organization.structure.testders.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;

    // Yeni kurs yaradır.
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {

        if (courseRepository.existsByTitle(request.title())) {
            throw new IllegalStateException("Bu kurs adı artıq mövcuddur");
        }

        Course course = courseMapper.toEntity(request);

        Course savedCourse = courseRepository.save(course);

        return courseMapper.toResponse(savedCourse);
    }

    // ID üzrə kursu tapır.
    public CourseResponse getCourseById(Long id) {

        Course course = courseRepository.findByIdWithStudents(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kurs tapılmadı"));

        return courseMapper.toResponse(course);
    }

    // Bütün kursları qaytarır.
    public List<CourseResponse> getAllCourses() {

        return courseMapper.toResponseList(courseRepository.findAll());
    }

    // Kursun bütün tələbələrini qaytarır.
    public List<StudentResponse> getStudentsByCourse(Long courseId) {

        Course course = courseRepository.findByIdWithStudents(courseId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kurs tapılmadı"));

        return studentMapper.toResponseList(
                List.copyOf(course.getStudents()));
    }

    // Kursun tələbə sayını qaytarır.
    public Integer countStudentsByCourse(Long courseId) {

        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Kurs tapılmadı");
        }

        return courseRepository.countStudentsByCourseId(courseId);
    }

    // Kurs məlumatlarını yeniləyir.
    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kurs tapılmadı"));

        if (courseRepository.existsByTitle(request.title())
                && !course.getTitle().equals(request.title())) {
            throw new IllegalStateException(
                    "Bu kurs adı başqa kursda istifadə olunur");
        }

        courseMapper.updateEntity(request, course);

        return courseMapper.toResponse(course);
    }

    // Kursu silir və tələbələrlə əlaqəsini təmizləyir.
    @Transactional
    public void deleteCourse(Long id) {

        Course course = courseRepository.findByIdWithStudents(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kurs tapılmadı"));

        for (Student student : course.getStudents()) {
            student.removeCourse(course);
        }

        courseRepository.delete(course);
    }
}