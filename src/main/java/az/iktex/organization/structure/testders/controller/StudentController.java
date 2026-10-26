package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.request.StudentRequest;
import az.iktex.organization.structure.testders.dto.response.StudentResponse;
import az.iktex.organization.structure.testders.service.StudentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // Yeni tələbə yaradır.
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(
            @RequestBody StudentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(request));
    }

    // ID üzrə tələbəni kursları ilə birlikdə tapır.
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Bütün tələbələri qaytarır.
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Ad üzrə tələbələri tapır.
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponse>> getStudentsByName(
            @RequestParam String name) {

        return ResponseEntity.ok(studentService.getStudentsByName(name));
    }

    // Heç bir kursu olmayan tələbələri qaytarır.
    @GetMapping("/without-courses")
    public ResponseEntity<List<StudentResponse>> getStudentsWithoutCourses() {

        return ResponseEntity.ok(
                studentService.getStudentsWithoutCourses());
    }

    // Tələbəni kursa yazır.
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponse> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.enrollStudent(studentId, courseId));
    }

    // Tələbəni kursdan çıxarır.
    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponse> unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                studentService.unenrollStudent(studentId, courseId));
    }

    // Tələbə məlumatlarını yeniləyir.
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentRequest request) {

        return ResponseEntity.ok(
                studentService.updateStudent(id, request));
    }

    // Tələbənin kurs sayını qaytarır.
    @GetMapping("/{id}/courses/count")
    public ResponseEntity<Integer> countStudentCourses(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                studentService.countStudentCourses(id));
    }

    // Tələbəni silir.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}