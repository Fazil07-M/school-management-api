package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.entity.Course;
import az.iktex.organization.structure.testders.repository.CourseRepository;
import az.iktex.organization.structure.testders.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    // Kursda boş yerin olub-olmadığını yoxlayır.
    @Transactional(propagation = Propagation.MANDATORY)
    public void reserveSeat(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kurs tapılmadı"));

        Long enrolledCount =
                studentRepository.countStudentsByCourseId(courseId);

        if (enrolledCount >= course.getCapacity()) {
            throw new IllegalStateException(
                    "Kursda boş yer yoxdur");
        }
    }
}