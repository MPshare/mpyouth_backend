package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.payload.ApiStatus;
import kr.go.mapo.mpyouth.payload.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.ProgramRequest;
import kr.go.mapo.mpyouth.payload.ProgramResponse;
import kr.go.mapo.mpyouth.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/program")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    private final ProgramService programService;

    //                    @ApiResponses({
//                            @ApiResponse(code = 200, message = "MpYouth API 테스트"),
//                            @ApiResponse(code = 500, message = "서버 오류"),
//                    })


    @GetMapping("/program/{id}")
    public ResponseEntity<CustomApiResponse<ProgramResponse>> getProgram(@PathVariable("id") Long id) {
        ProgramResponse findProgram = programService.findOne(id);

        CustomApiResponse<ProgramResponse> response = CustomApiResponse.<ProgramResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 단일 조회")
                .data(findProgram)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/program")
    public ResponseEntity<CustomApiResponse<List<ProgramResponse>>> getPrograms() {
        List<ProgramResponse> programs = programService.findPrograms();

        CustomApiResponse<List<ProgramResponse>> response = CustomApiResponse.<List<ProgramResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 전체 조회")
                .data(programs)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/program", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<?> saveProgram(
            @Valid @ModelAttribute ProgramRequest programRequest,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("programDto = " + programRequest);
        log.info("{}", programRequest);

        String fileUri = request.getScheme()
                + "://"
                + request.getLocalAddr()
                + ":"
                + request.getLocalPort();

        log.info("fileUri : {}", fileUri);

        ProgramResponse saveProgram = programService.saveProgram(programRequest, imageFiles, fileUri);

        CustomApiResponse<ProgramResponse> response = CustomApiResponse.<ProgramResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 추가")
                .data(saveProgram)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping(value = "/program/{id}", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<CustomApiResponse<ProgramResponse>> updateProgram(
            @PathVariable("id") Long id,
            @Validated @ModelAttribute ProgramRequest programRequest,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            HttpServletRequest request
    ) throws IOException {
        programRequest.setProgramId(id);

        log.info("{}", programRequest);

        String fileUri = request.getScheme()
                + "://"
                + request.getLocalAddr()
                + ":"
                + request.getLocalPort();

        ProgramResponse updateProgramRequest = programService.updateProgram(programRequest, imageFiles, fileUri);

        CustomApiResponse<ProgramResponse> response = CustomApiResponse.<ProgramResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 수정")
                .data(updateProgramRequest)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/program/{id}")
    public ResponseEntity<CustomApiResponse<ProgramResponse>> deleteProgram(@PathVariable("id") Long id) {
        log.info("id : {}", id);
        ProgramResponse deleteProgramRequest = programService.deleteProgram(id);

        CustomApiResponse<ProgramResponse> response = CustomApiResponse.<ProgramResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 삭제")
                .data(deleteProgramRequest)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
