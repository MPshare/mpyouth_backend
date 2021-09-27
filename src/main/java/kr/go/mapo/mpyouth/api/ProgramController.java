package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    private final ProgramService programService;

    @GetMapping("/api/test")
    public String controllerTest() {
        return "test ok";
    }

    @GetMapping("/api/program/{id}")
    public ResponseEntity<ApiResponse<ProgramDto>>getProgram(@PathVariable("id") Long id) {
        ProgramDto findProgram = programService.findOne(id);

        if(findProgram == null){
            ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                    .status(ApiStatus.FAIL)
                    .message("Program 조회 대상이 없습니다.")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(ApiStatus.SUCCESS)
                .message("Program 단일 조회")
                .data(findProgram)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/program")
    public ResponseEntity<ApiResponse<List<ProgramDto>>> getPrograms() {
        List<ProgramDto> programs = programService.findPrograms();

        ApiResponse<List<ProgramDto>> response = ApiResponse.<List<ProgramDto>>builder()
                .status(ApiStatus.SUCCESS)
                .message("Program 전체 조회")
                .data(programs)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/api/program", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<ApiResponse<ProgramDto>> saveProgram(
            @ModelAttribute ProgramDto programDto,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles
    ) throws IOException {
        System.out.println("programDto = " + programDto);
        log.info("{}", programDto);

        ProgramDto saveProgram = programService.saveProgram(programDto, imageFiles);

        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(ApiStatus.SUCCESS)
                .message("Program 추가")
                .data(saveProgram)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/program/{id}")
    public ResponseEntity<ApiResponse<ProgramDto>> updateProgram(
            @PathVariable("id") Long id,
            @RequestBody ProgramDto programDto
    ) {
        programDto.setProgramId(id);

        log.info("{}", programDto);

        ProgramDto updateProgramDto = programService.updateProgram(programDto);

        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(ApiStatus.SUCCESS)
                .message("Program 수정")
                .data(updateProgramDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/program/{id}")
    public ResponseEntity<ApiResponse<ProgramDto>> deleteProgram(@PathVariable("id") Long id) {
        log.info("id : {}", id);
        ProgramDto deleteProgramDto = programService.deleteProgram(id);

        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(ApiStatus.SUCCESS)
                .message("Program 삭제")
                .data(deleteProgramDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
