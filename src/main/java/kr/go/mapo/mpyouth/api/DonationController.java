package kr.go.mapo.mpyouth.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.go.mapo.mpyouth.payload.request.DonationRequest;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.response.DonationResponse;
import kr.go.mapo.mpyouth.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getDonation(@PathVariable("id") Long id) {
        DonationResponse donation = donationService.getDonation(id);

        CustomApiResponse<?> response = CustomApiResponse.builder()
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
    public ResponseEntity<CustomApiResponse<List<DonationResponse>>> getDonations() {
        List<DonationResponse> donations = donationService.getDonations();

        CustomApiResponse<List<DonationResponse>> response = CustomApiResponse.<List<DonationResponse>>builder()
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
    public ResponseEntity<?> saveDonation(@RequestBody DonationRequest donationRequest) {
        log.info(String.valueOf(donationRequest));
        DonationResponse donationResponse = donationService.saveDonation(donationRequest);

        CustomApiResponse<?> response = CustomApiResponse.builder()
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
    @PutMapping("/donation/{id}")
    public ResponseEntity<?> updateDonation(@PathVariable("id") Long id, @RequestBody DonationRequest donationRequest) {
        log.info(String.valueOf(donationRequest));
        DonationResponse updateDonation = donationService.updateDonation(id, donationRequest);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("재능기부 수정")
                .data(updateDonation)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "청소년 프로그램 상세 조회", description = "청소년 프로그램의 상세 내용을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @DeleteMapping("/donation/{id}")
    public ResponseEntity<?> deleteDonation(@PathVariable("id") Long id) {
        DonationResponse deleteDonation = donationService.deleteDonation(id);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("재능기부 삭제")
                .data(deleteDonation)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
