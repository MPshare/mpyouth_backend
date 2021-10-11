package kr.go.mapo.mpyouth.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.go.mapo.mpyouth.payload.request.LifeLongEduRequest;
import kr.go.mapo.mpyouth.payload.request.RequestPage;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.response.LifeLongEduResponse;
import kr.go.mapo.mpyouth.service.LifeLongEduService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Api(tags = "평생교육")
public class LifeLongEduController {
    private final LifeLongEduService lifeLongEduService;

    @Operation(summary = "평생교육 상세 조회", description = "평생교육의 상세 내용을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/life-long-edu/{id}")
    public ResponseEntity<CustomApiResponse<LifeLongEduResponse>> findLifeLongEdu(@PathVariable Long id) {
        LifeLongEduResponse lifeLongEdu = lifeLongEduService.findEdu(id);

        CustomApiResponse<LifeLongEduResponse> response = CustomApiResponse.<LifeLongEduResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 단일 조회")
                .data(lifeLongEdu)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "평생교육 저장", description = "평생교육을 저장합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PostMapping("/life-long-edu")
    public ResponseEntity<CustomApiResponse<LifeLongEduResponse>> saveLifeLongEdu(@RequestBody LifeLongEduRequest lifeLongEduRequest) {
        LifeLongEduResponse LifeLongEduResponse = lifeLongEduService.saveEdu(lifeLongEduRequest);
        CustomApiResponse<LifeLongEduResponse> response = CustomApiResponse.<LifeLongEduResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 저장")
                .data(LifeLongEduResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "평생교육 수정", description = "평생교육의 내용을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PatchMapping("/life-long-edu/{id}")
    public ResponseEntity<CustomApiResponse<LifeLongEduResponse>> updateLifeLongEdu(@PathVariable Long id, @RequestBody LifeLongEduRequest lifeLongEduRequest) {
        LifeLongEduResponse LifeLongEduResponse = lifeLongEduService.updateEdu(id, lifeLongEduRequest);

        CustomApiResponse<LifeLongEduResponse> response = CustomApiResponse.<LifeLongEduResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 수정")
                .data(LifeLongEduResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "평생교육 삭제", description = "평생교육을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @DeleteMapping("/life-long-edu/{id}")
    public ResponseEntity<CustomApiResponse<LifeLongEduResponse>> deleteLifeLongEdu(@PathVariable Long id) {
        LifeLongEduResponse deleteLifeLongEdu = lifeLongEduService.deleteEdu(id);

        CustomApiResponse<LifeLongEduResponse> response = CustomApiResponse.<LifeLongEduResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 삭제")
                .data(deleteLifeLongEdu)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "평생교육 전체 조회", description = "모든 평생교육의 목록을 출력합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/life-long-edu")
    public ResponseEntity<CustomApiResponse<Page<LifeLongEduResponse>>> getLifeLongEduList(@PageableDefault(size = 10) Pageable pageable) {
        Page<LifeLongEduResponse> lifeLongEduList = lifeLongEduService.findEduList(pageable);

        CustomApiResponse<Page<LifeLongEduResponse>> response = CustomApiResponse.<Page<LifeLongEduResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 전체 조회")
                .data(lifeLongEduList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "평생교육 검색", description = "입력된 키워드가 활동이름, 활동상세에 포함된 평생교육 목록을 출력합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/life-long-edu/search")
    public ResponseEntity<CustomApiResponse<Page<LifeLongEduResponse>>> searchLifeLongEdu(@RequestParam("keyword") String keyword, @Parameter(schema = @Schema(implementation = RequestPage.class)) @PageableDefault(size = 10) Pageable pageable) {
        Page<LifeLongEduResponse> LifeLongEduResponses = lifeLongEduService.searchEdu(keyword, pageable);
        CustomApiResponse<Page<LifeLongEduResponse>> response = CustomApiResponse.<Page<LifeLongEduResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("평생교육 단일 조회")
                .data(LifeLongEduResponses)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
