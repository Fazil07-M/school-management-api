package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.request.BookRequest;
import az.iktex.organization.structure.testders.dto.response.BookResponse;
import az.iktex.organization.structure.testders.entity.Author;
import az.iktex.organization.structure.testders.entity.AuthorBook;
import az.iktex.organization.structure.testders.entity.Book;
import az.iktex.organization.structure.testders.mapper.BookMapper;
import az.iktex.organization.structure.testders.repository.AuthorBookRepository;
import az.iktex.organization.structure.testders.repository.AuthorRepository;
import az.iktex.organization.structure.testders.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorBookRepository authorBookRepository;
    private final BookMapper bookMapper;

    // Yeni kitab yaradır.
    @Transactional
    public BookResponse createBook(BookRequest request) {

        if (bookRepository.existsByTitle(request.title())) {
            throw new IllegalStateException("Bu kitab adı artıq mövcuddur");
        }

        Book book = bookMapper.toEntity(request);

        Book savedBook = bookRepository.save(book);

        return bookMapper.toResponse(savedBook);
    }

    // ID üzrə kitabı müəllifləri ilə birlikdə tapır.
    public BookResponse getBookById(Long id) {

        Book book = bookRepository.findByIdWithAuthors(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kitab tapılmadı"));

        return bookMapper.toDetailResponse(book);
    }

    // Bütün kitabları qaytarır.
    public List<BookResponse> getAllBooks() {

        return bookMapper.toResponseList(bookRepository.findAll());
    }

    // Başlığında verilmiş sözü saxlayan kitabları tapır.
    public List<BookResponse> searchBooksByTitle(String title) {

        return bookMapper.toResponseList(
                bookRepository.findByTitleContainingIgnoreCase(title));
    }


    // Kitab məlumatlarını yeniləyir.
    @Transactional
    public BookResponse updateBook(Long id, BookRequest request) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kitab tapılmadı"));

        if (bookRepository.existsByTitle(request.title())
                && !book.getTitle().equals(request.title())) {
            throw new IllegalStateException(
                    "Bu kitab adı başqa kitabda istifadə olunur");
        }

        bookMapper.updateEntity(request, book);

        return bookMapper.toResponse(book);
    }

    // Müəllifi kitaba əlavə edir.
    @Transactional
    public BookResponse addAuthorToBook(Long bookId, Long authorId) {

        Book book = bookRepository.findByIdWithAuthors(bookId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kitab tapılmadı"));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Müəllif tapılmadı"));

        if (authorBookRepository.existsByAuthorIdAndBookId(authorId, bookId)) {
            throw new IllegalStateException(
                    "Bu müəllif artıq kitaba əlavə edilib");
        }

        AuthorBook authorBook = new AuthorBook();
        authorBook.setAuthor(author);
        authorBook.setBook(book);

        book.getAuthorBooks().add(authorBook);
        author.getAuthorBooks().add(authorBook);

        authorBookRepository.save(authorBook);

        return bookMapper.toDetailResponse(book);
    }

    // Müəllifi kitabdan çıxarır.
    @Transactional
    public void removeAuthorFromBook(Long bookId, Long authorId) {

        AuthorBook authorBook = authorBookRepository
                .findByAuthorIdAndBookId(authorId, bookId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Bu müəllif-kitaba əlaqəsi tapılmadı"));

        Book book = authorBook.getBook();
        Author author = authorBook.getAuthor();

        book.getAuthorBooks().remove(authorBook);
        author.getAuthorBooks().remove(authorBook);

        authorBookRepository.delete(authorBook);
    }

    // Kitabı silir.
    @Transactional
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Kitab tapılmadı"));

        bookRepository.delete(book);
    }
}