package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.request.CourseRequest;
import az.iktex.organization.structure.testders.dto.response.CourseResponse;
import az.iktex.organization.structure.testders.dto.response.StudentResponse;
import az.iktex.organization.structure.testders.service.CourseService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Yeni kurs yaradır.
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(
            @RequestBody CourseRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(request));
    }

    // ID üzrə kursu tapır.
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // Bütün kursları qaytarır.
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {

        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // Kursa yazılmış tələbələri qaytarır.
    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentResponse>> getStudentsByCourse(
            @PathVariable Long id) {

        return ResponseEntity.ok(courseService.getStudentsByCourse(id));
    }

    // Kursa yazılmış tələbələrin sayını qaytarır.
    @GetMapping("/{id}/students/count")
    public ResponseEntity<Integer> countStudentsByCourse(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                courseService.countStudentsByCourse(id));
    }

    // Kurs məlumatlarını yeniləyir.
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequest request) {

        return ResponseEntity.ok(
                courseService.updateCourse(id, request));
    }

    // Kursu silir.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }
}