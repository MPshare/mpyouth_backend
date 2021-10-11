package kr.go.mapo.mpyouth.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.mapo.mpyouth.payload.request.VolunteerRequest;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.response.VolunteerResponse;
import kr.go.mapo.mpyouth.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Api(tags = "자원봉사")
public class VolunteerController {
    private final VolunteerService volunteerService;

    @Operation(summary = "자원봉사 상세 조회", description = "자원봉사의 상세 내용을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/volunteer/{id}")
    public ResponseEntity<CustomApiResponse<VolunteerResponse>> findVolunteer(
            @Parameter(name = "id", description = "자원봉사 id", required = true)
            @PathVariable Long id
    ) {
        VolunteerResponse volunteer = volunteerService.findVolunteer(id);

        CustomApiResponse<VolunteerResponse> response = CustomApiResponse.<VolunteerResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 단일 조회")
                .data(volunteer)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "자원봉사 저장", description = "자원봉사를 저장합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PostMapping("/volunteer")
    public ResponseEntity<CustomApiResponse<VolunteerResponse>> saveVolunteer(@RequestBody VolunteerRequest volunteerRequest) {
        VolunteerResponse volunteerResponse = volunteerService.saveVolunteer(volunteerRequest);
        CustomApiResponse<VolunteerResponse> response = CustomApiResponse.<VolunteerResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 저장")
                .data(volunteerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "자원봉사 수정", description = "자원봉사의 내용을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PatchMapping("/volunteer/{id}")
    public ResponseEntity<CustomApiResponse<VolunteerResponse>> updateVolunteer(
            @Parameter(name = "id", description = "자원봉사 id", required = true)
            @PathVariable Long id,
            @RequestBody VolunteerRequest volunteerRequest
    ) {
        VolunteerResponse volunteerResponse = volunteerService.updateVolunteer(id, volunteerRequest);

        CustomApiResponse<VolunteerResponse> response = CustomApiResponse.<VolunteerResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 수정")
                .data(volunteerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "자원봉사 삭제", description = "자원봉사를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @DeleteMapping("/volunteer/{id}")
    public ResponseEntity<CustomApiResponse<VolunteerResponse>> deleteVolunteer(
            @Parameter(name = "id", description = "자원봉사 id", required = true)
            @PathVariable Long id
    ) {
        VolunteerResponse deleteVolunteer = volunteerService.deleteVolunteer(id);

        CustomApiResponse<VolunteerResponse> response = CustomApiResponse.<VolunteerResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 삭제")
                .data(deleteVolunteer)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "자원봉사 전체 조회", description = "모든 자원봉사의 목록을 출력합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/volunteer")
    public ResponseEntity<CustomApiResponse<Page<VolunteerResponse>>> getVolunteerList(Pageable pageable) {
        Page<VolunteerResponse> volunteerList = volunteerService.findVolunteerList(pageable);

        CustomApiResponse<Page<VolunteerResponse>> response = CustomApiResponse.<Page<VolunteerResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 전체 조회")
                .data(volunteerList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "자원봉사 검색", description = "입력된 키워드가 활동이름, 활동상세에 포함된 자원봉사 목록을 출력합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/volunteer/search")
    public ResponseEntity<CustomApiResponse<Page<VolunteerResponse>>> searchVolunteer(
            @Parameter(name = "keyword", description = "검색용 키워드", required = true)
            @RequestParam("keyword") String keyword, Pageable pageable
    ) {
        Page<VolunteerResponse> volunteerResponses = volunteerService.searchVolunteer(keyword, pageable);
        CustomApiResponse<Page<VolunteerResponse>> response = CustomApiResponse.<Page<VolunteerResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 단일 조회")
                .data(volunteerResponses)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
