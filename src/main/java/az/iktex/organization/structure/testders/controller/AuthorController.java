package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.request.AuthorRequest;
import az.iktex.organization.structure.testders.dto.response.AuthorResponse;
import az.iktex.organization.structure.testders.service.AuthorService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // Yeni müəllif yaradır.
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(
            @RequestBody AuthorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.createAuthor(request));
    }

    // ID üzrə müəllifi tapır.
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    // Bütün müəllifləri qaytarır.
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {

        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    // Ölkəyə görə müəllifləri tapır.
    @GetMapping("/country/{country}")
    public ResponseEntity<List<AuthorResponse>> getAuthorsByCountry(
            @PathVariable String country) {

        return ResponseEntity.ok(
                authorService.getAuthorsByCountry(country));
    }

    // Kitabı olmayan müəllifləri qaytarır.
    @GetMapping("/without-books")
    public ResponseEntity<List<AuthorResponse>> getAuthorsWithoutBooks() {

        return ResponseEntity.ok(
                authorService.getAuthorsWithoutBooks());
    }

    // Müəllif məlumatlarını yeniləyir.
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @RequestBody AuthorRequest request) {

        return ResponseEntity.ok(
                authorService.updateAuthor(id, request));
    }

    // Müəllifi silir.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }
}