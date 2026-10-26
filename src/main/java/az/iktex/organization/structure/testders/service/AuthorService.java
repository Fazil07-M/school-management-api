package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.dto.request.AuthorRequest;
import az.iktex.organization.structure.testders.dto.response.AuthorResponse;
import az.iktex.organization.structure.testders.entity.Author;
import az.iktex.organization.structure.testders.mapper.AuthorMapper;
import az.iktex.organization.structure.testders.repository.AuthorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    // Yeni müəllif yaradır.
    @Transactional
    public AuthorResponse createAuthor(AuthorRequest request) {

        Author author = authorMapper.toEntity(request);

        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toResponse(savedAuthor);
    }

    // ID üzrə müəllifi tapır.
    public AuthorResponse getAuthorById(Long id) {

        Author author = authorRepository.findByIdWithBooks(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Müəllif tapılmadı"));

        return authorMapper.toResponse(author);
    }

    // Bütün müəllifləri qaytarır.
    public List<AuthorResponse> getAllAuthors() {

        return authorMapper.toResponseList(authorRepository.findAll());
    }

    // Ölkəyə görə müəllifləri tapır.
    public List<AuthorResponse> getAuthorsByCountry(String country) {

        return authorMapper.toResponseList(
                authorRepository.findByCountry(country));
    }

    // Heç bir kitabı olmayan müəllifləri qaytarır.
    public List<AuthorResponse> getAuthorsWithoutBooks() {

        return authorMapper.toResponseList(
                authorRepository.findByAuthorBooksIsEmpty());
    }

    // Müəllif məlumatlarını yeniləyir.
    @Transactional
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Müəllif tapılmadı"));

        authorMapper.updateEntity(request, author);

        return authorMapper.toResponse(author);
    }

    // Müəllifi silir.
    @Transactional
    public void deleteAuthor(Long id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Müəllif tapılmadı"));

        authorRepository.delete(author);
    }
}