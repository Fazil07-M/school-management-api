package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.request.StudentRequest;
import az.iktex.organization.structure.testders.dto.response.StudentResponse;
import az.iktex.organization.structure.testders.entity.Student;
import az.iktex.organization.structure.testders.entity.Course;
import az.iktex.organization.structure.testders.mapper.StudentMapper;
import az.iktex.organization.structure.testders.repository.StudentRepository;
import az.iktex.organization.structure.testders.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;

    // Yeni tələbə yaradır.
    @Transactional
    public StudentResponse createStudent(StudentRequest request) {

        Student student = studentMapper.toEntity(request);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponse(savedStudent);
    }

    // ID üzrə tələbəni kursları ilə birlikdə tapır.
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findByIdWithCourses(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tələbə tapılmadı"));

        return studentMapper.toDetailResponse(student);
    }

    // Bütün tələbələri qaytarır.
    public List<StudentResponse> getAllStudents() {

        return studentMapper.toResponseList(studentRepository.findAll());
    }

    // Ad üzrə tələbələri tapır.
    public List<StudentResponse> getStudentsByName(String name) {

        return studentMapper.toResponseList(
                studentRepository.findByName(name));
    }

    // Heç bir kursa yazılmamış tələbələri qaytarır.
    public List<StudentResponse> getStudentsWithoutCourses() {

        return studentMapper.toResponseList(
                studentRepository.findByCoursesIsEmpty());
    }

    // Tələbəyə kurs əlavə edir.
    @Transactional
    public StudentResponse enrollStudent(Long studentId, Long courseId) {

        Student student = studentRepository.findByIdWithCourses(studentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tələbə tapılmadı"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kurs tapılmadı"));

        if (student.getCourses().contains(course)) {
            throw new IllegalStateException(
                    "Tələbə artıq bu kursa yazılıb");
        }

        student.addCourse(course);

        return studentMapper.toDetailResponse(student);
    }

    // Tələbəni kursdan çıxarır.
    @Transactional
    public StudentResponse unenrollStudent(Long studentId, Long courseId) {

        Student student = studentRepository.findByIdWithCourses(studentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tələbə tapılmadı"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kurs tapılmadı"));

        if (!student.getCourses().contains(course)) {
            throw new IllegalStateException(
                    "Tələbə bu kursa yazılmayıb");
        }

        student.removeCourse(course);

        return studentMapper.toDetailResponse(student);
    }

    // Tələbə məlumatlarını yeniləyir.
    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tələbə tapılmadı"));

        studentMapper.updateEntity(request, student);

        return studentMapper.toResponse(student);
    }

    // Tələbənin kurs sayını qaytarır.
    public Integer countStudentCourses(Long studentId) {

        return studentRepository.countStudentCourses(studentId);
    }

    // Tələbəni silir.
    @Transactional
    public void deleteStudent(Long id) {

        Student student = studentRepository.findByIdWithCourses(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tələbə tapılmadı"));

        student.getCourses().clear();

        studentRepository.delete(student);
    }
}