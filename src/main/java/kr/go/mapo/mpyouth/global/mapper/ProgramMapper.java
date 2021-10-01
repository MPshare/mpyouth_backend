package kr.go.mapo.mpyouth.global.mapper;

import kr.go.mapo.mpyouth.payload.ProgramRequest;
import kr.go.mapo.mpyouth.payload.ProgramFileRequest;
import kr.go.mapo.mpyouth.payload.ProgramFileResponse;
import kr.go.mapo.mpyouth.payload.ProgramResponse;
import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.ProgramFile;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProgramMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ProgramMapper {

    @Value("${file.api_path}")
//    @Autowired
    private String apiPath;

    @Mapping(source = "programId", target = "id")
    @Mapping(source = "organizationId", target = "organization.id")
    @Mapping(source = "programFiles", target = "programFiles")
    public abstract Program saveDtoToProgram(ProgramRequest programRequest);

    @Mapping(source = "id", target = "programId")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    @Mapping(source = "programFiles", target = "programFiles")
    public abstract ProgramResponse getProgramToDto(Program program);

    public abstract List<ProgramResponse> getProgramsToDtos(List<Program> programs);

    @Mapping(source = "programId", target = "program.id")
    public abstract ProgramFile saveDtoToProgram(ProgramFileRequest programFileRequest);

    @Mapping(source = "program.id", target = "programId")
    public abstract ProgramFileRequest getProgramToDto(ProgramFile programFile);

    @Named("filePath")
    public String filePath(String fileName) {
        return apiPath + fileName;
    }


    @Mapping(target = "fileUri", source = "fileName", qualifiedByName = "filePath")
    public abstract ProgramFileResponse getProgramFileToDto(ProgramFile programFile);


    public abstract List<ProgramFileResponse> getProgramFileToDtos(List<ProgramFile> programFiles);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateDtoToProgram(ProgramRequest programRequest, @MappingTarget Program program);


}
