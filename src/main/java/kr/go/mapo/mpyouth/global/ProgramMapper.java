package kr.go.mapo.mpyouth.global;

import kr.go.mapo.mpyouth.api.ProgramDto;
import kr.go.mapo.mpyouth.domain.Program;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProgramMapper {
    ProgramMapper instance = Mappers.getMapper(ProgramMapper.class);

    @Mapping(source = "organizationId",target = "organization.id")
    Program saveDtoToProgram(ProgramDto programDto);

    @Mapping(source = "organization.id", target = "organizationId")
    ProgramDto getProgramToDto(Program program);

    List<ProgramDto> getProgramsToDtos(List<Program> programs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToProgram(ProgramDto programDto, @MappingTarget Program program);
}
