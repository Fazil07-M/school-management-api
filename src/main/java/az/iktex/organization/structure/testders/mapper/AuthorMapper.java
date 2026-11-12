
package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.dto.request.AuthorRequest;
import az.iktex.organization.structure.testders.dto.response.AuthorResponse;
import az.iktex.organization.structure.testders.entity.Author;
import az.iktex.organization.structure.testders.entity.AuthorBook;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    // AuthorBook obyektindən AuthorResponse yaradılır.
    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "name", source = "author.name")
    @Mapping(target = "surname", source = "author.surname")
    @Mapping(target = "country", source = "author.country")
    AuthorResponse toResponse(AuthorBook authorBook);

    // AuthorRequest obyektindən Author yaradılır.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorBooks", ignore = true)
    Author toEntity(AuthorRequest request);

    // Author obyektindən AuthorResponse yaradılır.
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "country", source = "country")
    AuthorResponse toResponse(Author author);

    // Author siyahısı AuthorResponse siyahısına çevrilir.
    List<AuthorResponse> toResponseList(List<Author> authors);

    // Mövcud Author obyektinin məlumatları yenilənir.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorBooks", ignore = true)
    void updateEntity(
            AuthorRequest request,
            @MappingTarget Author author
    );
}
