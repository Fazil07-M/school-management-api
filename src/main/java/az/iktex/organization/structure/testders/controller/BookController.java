package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.dto.request.BookRequest;
import az.iktex.organization.structure.testders.dto.response.BookResponse;
import az.iktex.organization.structure.testders.service.BookService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Yeni kitab yaradır.
    @PostMapping
    public ResponseEntity<BookResponse> createBook(
            @RequestBody BookRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.createBook(request));
    }

    // ID üzrə kitabı müəllifləri ilə birlikdə tapır.
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(
            @PathVariable Long id) {

        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // Bütün kitabları qaytarır.
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Başlığa görə kitabları axtarır.
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooksByTitle(
            @RequestParam String title) {

        return ResponseEntity.ok(
                bookService.searchBooksByTitle(title));
    }



    // Müəllifi kitaba əlavə edir.
    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<BookResponse> addAuthorToBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.addAuthorToBook(bookId, authorId));
    }

    // Müəllifi kitabdan çıxarır.
    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> removeAuthorFromBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {

        bookService.removeAuthorFromBook(bookId, authorId);

        return ResponseEntity.noContent().build();
    }

    // Kitab məlumatlarını yeniləyir.
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequest request) {

        return ResponseEntity.ok(
                bookService.updateBook(id, request));
    }

    // Kitabı silir.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}