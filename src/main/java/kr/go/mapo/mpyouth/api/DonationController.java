package kr.go.mapo.mpyouth.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.go.mapo.mpyouth.payload.request.DonationRequest;
import kr.go.mapo.mpyouth.payload.request.DonationUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.response.DonationResponse;
import kr.go.mapo.mpyouth.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "재능기부")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @Operation(summary = "재능기부 상세 조회", description = "재능기부의 상세 내용을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/donation/{id}")
    public ResponseEntity<CustomApiResponse<DonationResponse>> getDonation(@PathVariable("id") Long id) {
        DonationResponse donation = donationService.getDonation(id);

        CustomApiResponse<DonationResponse> response = CustomApiResponse.<DonationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("재능기부 단일조회")
                .data(donation)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "재능기부 전체 조회", description = "재능기부의 전체 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/donation")
    public ResponseEntity<CustomApiResponse<Page<DonationResponse>>> getDonations(@PageableDefault(size = 10) Pageable pageable) {
        Page<DonationResponse> donations = donationService.getDonations(pageable);

        CustomApiResponse<Page<DonationResponse>> response = CustomApiResponse.<Page<DonationResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("전체조회")
                .data(donations)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "재능기부 저장", description = "재능기부를 저장합니다",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PostMapping("/donation")
    public ResponseEntity<CustomApiResponse<DonationResponse>> saveDonation(@Validated @RequestBody DonationRequest donationRequest) {
        log.info(String.valueOf(donationRequest));
        DonationResponse donationResponse = donationService.saveDonation(donationRequest);

        CustomApiResponse<DonationResponse> response = CustomApiResponse.<DonationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("재능기부 저장")
                .data(donationResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "재능기부 수정", description = "재능기부 내용을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PatchMapping("/donation/{id}")
    public ResponseEntity<CustomApiResponse<DonationResponse>> updateDonation(@PathVariable("id") Long id, @RequestBody DonationUpdateRequest donationUpdateRequest) {
        log.info(String.valueOf(donationUpdateRequest));
        DonationResponse updateDonation = donationService.updateDonation(id, donationUpdateRequest);

        CustomApiResponse<DonationResponse> response = CustomApiResponse.<DonationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("재능기부 수정")
                .data(updateDonation)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "재능기부 삭제", description = "재능기부를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @DeleteMapping("/donation/{id}")
    public ResponseEntity<CustomApiResponse<DonationResponse>> deleteDonation(@PathVariable("id") Long id) {
        DonationResponse deleteDonation = donationService.deleteDonation(id);

        CustomApiResponse<DonationResponse> response = CustomApiResponse.<DonationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("재능기부 삭제")
                .data(deleteDonation)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "재능기부 검색", description = "입력된 키워드가 활동이름, 활동상세에 포함된 재능기부 목록을 출력합니다. (제목, 설명 부분일치)",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/donation/search")
    public ResponseEntity<CustomApiResponse<Page<DonationResponse>>> searchDonation(@RequestParam("keyword") String keyword, @PageableDefault(size = 10) Pageable pageable) {
        Page<DonationResponse> donationResponses = donationService.searchDonation(keyword, pageable);

        CustomApiResponse<Page<DonationResponse>> response = CustomApiResponse.<Page<DonationResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("재능기부 검색")
                .data(donationResponses)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
