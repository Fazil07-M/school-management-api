
package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.dto.request.PassportRequest;
import az.iktex.organization.structure.testders.dto.response.PassportResponse;
import az.iktex.organization.structure.testders.entity.Passport;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    // Yeni pasport yaradılarkən ID və istifadəçi ayrıca təyin olunur.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Passport toEntity(PassportRequest request);

    // Passport obyektindən PassportResponse yaradılır.
    PassportResponse toResponse(Passport passport);

    // Passport siyahısı PassportResponse siyahısına çevrilir.
    List<PassportResponse> toResponseList(List<Passport> passports);

    // Mövcud pasportun məlumatları yenilənir.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(
            PassportRequest request,
            @MappingTarget Passport passport
    );
}
