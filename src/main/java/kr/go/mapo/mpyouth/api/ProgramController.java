package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.global.ProgramMapper;
import kr.go.mapo.mpyouth.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    private final ProgramService programService;
    private final ProgramMapper programMapper;

    @GetMapping("/api/test")
    public String controllerTest() {
        return "test ok";
    }

    @GetMapping("/api/program/{id}")
    public ResponseEntity<ApiResponse<ProgramDto>> getProgram(@PathVariable("id") Long id){
        ProgramDto program = programService.findOne(id);

        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(HttpStatus.OK)
                .message("Program 단일 조회")
                .data(program)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/api/program")
    public ResponseEntity<ApiResponse<List<ProgramDto>>> getPrograms(){
        List<ProgramDto> programs = programService.findPrograms();

        ApiResponse<List<ProgramDto>> response = ApiResponse.<List<ProgramDto>>builder()
                .status(HttpStatus.OK)
                .message("Program 전체 조회")
                .data(programs)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/program")
    public ResponseEntity<ApiResponse<ProgramDto>> saveProgram(@RequestBody ProgramDto programDto) {

        System.out.println("programDto = " + programDto);
        log.info("{}", programDto);


        ProgramDto newProgramDto = programService.saveProgram(programDto);


        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(HttpStatus.CREATED)
                .message("Program 추가")
                .data(newProgramDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/program/{id}")
    public ResponseEntity<ApiResponse<ProgramDto>> updateProgram(
            @PathVariable("id") Long id,
            @RequestBody ProgramDto programDto
    ) {
        programDto.setId(id);

        log.info("{}",programDto);

        ProgramDto updateProgramDto = programService.updateProgram(programDto);

        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(HttpStatus.OK)
                .message("Program 수정")
                .data(updateProgramDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/program/{id}")
    public ResponseEntity<ApiResponse<ProgramDto>> deleteProgram(@PathVariable("id") Long id) {
        log.info("id : {}", id);
        ProgramDto deleteProgramDto = programService.findOne(id);
        programService.deleteProgram(id);

        ApiResponse<ProgramDto> response = ApiResponse.<ProgramDto>builder()
                .status(HttpStatus.OK)
                .message("Program 삭제")
                .data(deleteProgramDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
