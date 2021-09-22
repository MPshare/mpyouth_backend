package kr.go.mapo.mpyouth.global;

import kr.go.mapo.mpyouth.api.ProgramDto;
import kr.go.mapo.mpyouth.domain.Program;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProgramMapper {
    ProgramMapper instance = Mappers.getMapper(ProgramMapper.class);

    Program saveDtoToProgram(ProgramDto programDto);

    ProgramDto getProgramToDto(Program program);

    List<ProgramDto> getProgramsToDtos(List<Program> programs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToProgram(ProgramDto programDto, @MappingTarget Program program);
}
