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

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "name", source = "author.name")
    @Mapping(target = "surname", source = "author.surname")
    AuthorResponse toResponse(AuthorBook authorBook);


    @Mapping(target = "authorBooks", ignore = true)
    Author toEntity(AuthorRequest request);

    AuthorResponse toResponse(Author author);

    List<AuthorResponse> toResponseList(List<Author> authors);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorBooks", ignore = true)
    void updateEntity(
            AuthorRequest request,
            @MappingTarget Author author
    );
}