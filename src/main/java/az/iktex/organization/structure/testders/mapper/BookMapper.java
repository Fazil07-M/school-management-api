package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.dto.request.BookRequest;
import az.iktex.organization.structure.testders.dto.response.BookResponse;
import az.iktex.organization.structure.testders.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = AuthorMapper.class
)
public interface BookMapper {

    @Mapping(target = "authorBooks", ignore = true)
    Book toEntity(BookRequest request);

    @Mapping(target = "authors", ignore = true)
    BookResponse toResponse(Book book);

    @Named("withAuthors")
    @Mapping(target = "authors", source = "authorBooks")
    BookResponse toDetailResponse(Book book);

    List<BookResponse> toResponseList(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorBooks", ignore = true)
    void updateEntity(
            BookRequest request,
            @MappingTarget Book book
    );
}