package kr.go.mapo.mpyouth.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.go.mapo.mpyouth.payload.request.ProgramYouthRequest;
import kr.go.mapo.mpyouth.payload.request.ProgramUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramYouthResponse;
import kr.go.mapo.mpyouth.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Api(tags = "청소년 프로그램")
@Slf4j
public class ProgramController {
    private final ProgramService programService;


    @Operation(summary = "청소년 프로그램 상세 조회*", description = "청소년 프로그램의 상세 내용을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
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

    @Operation(summary = "청소년 프로그램 전체 조회*", description = "모든 청소년 프로그램의 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/program")
    public ResponseEntity<CustomApiResponse<Page<ProgramYouthResponse>>> getPrograms(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProgramYouthResponse> youthPrograms = programService.findYouthPrograms(pageable);

        CustomApiResponse<Page<ProgramYouthResponse>> response = CustomApiResponse.<Page<ProgramYouthResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 전체 조회")
                .data(youthPrograms)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "청소년 프로그램 저장*", description = "청소년 프로그램을 저장합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PostMapping(
            value = "/program",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CustomApiResponse<ProgramResponse>> saveProgram(
            @Validated @ModelAttribute("programRequest") ProgramYouthRequest programYouthRequest,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("programDto = " + programYouthRequest);
        log.info("{}", programYouthRequest);

        String fileUri = request.getScheme()
                + "://"
                + request.getLocalAddr()
                + ":"
                + request.getLocalPort();

        log.info("fileUri : {}", fileUri);

        ProgramResponse saveProgram = programService.saveProgram(programYouthRequest, imageFiles, fileUri);

        CustomApiResponse<ProgramResponse> response = CustomApiResponse.<ProgramResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 추가")
                .data(saveProgram)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "청소년 프로그램 수정*", description = "청소년 프로그램의 내용을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PatchMapping(value = "/program/{id}", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<CustomApiResponse<ProgramResponse>> updateProgram(
            @PathVariable("id") Long id,
            @ModelAttribute ProgramUpdateRequest updateRequest,
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            HttpServletRequest request
    ) throws IOException {

        log.info("{}", updateRequest);

        String fileUri = request.getScheme()
                + "://"
                + request.getLocalAddr()
                + ":"
                + request.getLocalPort();

        ProgramResponse updateProgramRequest = programService.updateProgram(id, updateRequest, imageFiles, fileUri);

        CustomApiResponse<ProgramResponse> response = CustomApiResponse.<ProgramResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Program 수정")
                .data(updateProgramRequest)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "청소년 프로그램 삭제*", description = "청소년 프로그램을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
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

    @Operation(summary = "청소년 프로그램 검색", description = "청소년 프로그램 내용을 검색합니다. (제목, 설명 부분일치)",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/program/search")
    public ResponseEntity<CustomApiResponse<Page<ProgramYouthResponse>>> searchProgramKeyword(
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info(pageable.toString());
        Page<ProgramYouthResponse> byTitle = programService.findByKeyword(keyword, pageable);

        log.info(String.valueOf(byTitle.getClass()));

        CustomApiResponse<Page<ProgramYouthResponse>> response = CustomApiResponse.<Page<ProgramYouthResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("타이틀 검색, 페이징")
                .data(byTitle)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
