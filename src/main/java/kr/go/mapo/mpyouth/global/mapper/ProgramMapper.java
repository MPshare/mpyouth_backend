package kr.go.mapo.mpyouth.global.mapper;

import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.ProgramFile;
import kr.go.mapo.mpyouth.domain.ProgramThumbnail;
import kr.go.mapo.mpyouth.payload.request.ProgramFileRequest;
import kr.go.mapo.mpyouth.payload.request.ProgramRequest;
import kr.go.mapo.mpyouth.payload.request.ProgramUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.ProgramFileResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramThumbnailResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramYouthResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProgramMapper.class, OrganizationMapper.class, CategoryMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ProgramMapper {

    @Value("${file.api_path}")
//    @Autowired
    private String apiPath;

    @Mapping(source = "programId", target = "id")
    @Mapping(source = "organizationId", target = "organization.id")
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "programFiles", target = "programFiles")
    public abstract Program saveDtoToProgram(ProgramRequest programRequest);

    @Mapping(source = "id", target = "programId")
    @Mapping(source = "programFiles", target = "programFiles")
    @Mapping(source = "category", target = "category")
    public abstract ProgramResponse getProgramToDto(Program program);

    @Mapping(source = "id", target = "programId")
//    @Mapping(source = "organization", target = "organization")
    @Mapping(source = "programThumbnail", target = "thumbnail")
    public abstract ProgramYouthResponse getProgramsToYouths(Program program);

    public abstract List<ProgramYouthResponse> getProgramsToYouths(List<Program> programs);

    @Mapping(source = "programId", target = "program.id")
    public abstract ProgramFile saveDtoToProgram(ProgramFileRequest programFileRequest);

    @Mapping(source = "program.id", target = "programId")
    public abstract ProgramFileRequest getProgramToDto(ProgramFile programFile);

    @Mapping(target = "fileUri", source = "fileName", qualifiedByName = "filePath")
    public abstract ProgramThumbnailResponse getThumbnailToDtos(ProgramThumbnail programThumbnail);

    @Named("filePath")
    public String filePath(String fileName) {
        return apiPath + fileName;
    }


    @Mapping(target = "fileUri", source = "fileName", qualifiedByName = "filePath")
    public abstract ProgramFileResponse getProgramFileToDto(ProgramFile programFile);


    public abstract List<ProgramFileResponse> getProgramFileToDtos(List<ProgramFile> programFiles);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateDtoToProgram(ProgramUpdateRequest programRequest, @MappingTarget Program program);


}
