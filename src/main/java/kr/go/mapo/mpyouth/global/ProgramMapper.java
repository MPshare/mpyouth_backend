package kr.go.mapo.mpyouth.global;

import kr.go.mapo.mpyouth.api.ProgramDto;
import kr.go.mapo.mpyouth.api.ProgramFileRequest;
import kr.go.mapo.mpyouth.api.ProgramFileResponse;
import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.ProgramFile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProgramMapper {
    ProgramMapper instance = Mappers.getMapper(ProgramMapper.class);

    @Mapping(source = "programId", target = "id")
    @Mapping(source = "organizationId",target = "organization.id")
    @Mapping(source = "programFiles", target = "programFiles")
    Program saveDtoToProgram(ProgramDto programDto);

    @Mapping(source = "id", target = "programId")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "programFiles", target = "programFiles")
    ProgramDto getProgramToDto(Program program);

    List<ProgramDto> getProgramsToDtos(List<Program> programs);

    @Mapping(source = "programId",target = "program.id")
    ProgramFile saveDtoToProgram(ProgramFileRequest programFileRequest);

    @Mapping(source = "program.id", target = "programId")
    ProgramFileRequest getProgramToDto(ProgramFile programFile);

    ProgramFileResponse getProgramFileToDto(ProgramFile programFile);

    List<ProgramFileResponse> getProgramFileToDtos(List<ProgramFile> programFiles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDtoToProgram(ProgramDto programDto, @MappingTarget Program program);
}
