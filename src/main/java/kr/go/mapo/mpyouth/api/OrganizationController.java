package kr.go.mapo.mpyouth.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.go.mapo.mpyouth.payload.request.OrganizationRequest;
import kr.go.mapo.mpyouth.payload.response.CustomApiResponse;
import kr.go.mapo.mpyouth.payload.response.OrganizationResponse;
import kr.go.mapo.mpyouth.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "청소년 기관")
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @Operation(summary = "청소년 기관정보 저장*", description = "청소년 기관 정보를 저장합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PostMapping("/api/organization")
    public ResponseEntity<CustomApiResponse<OrganizationResponse>> createOrganization(
//           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "기관 저장용 Request",
//           content = @Content(schema = @Schema(implementation = OrganizationRequest.class)))
           @RequestBody OrganizationRequest organizationRequest
    ) {
        OrganizationResponse organizationResponse = organizationService.saveOrganization(organizationRequest);

        CustomApiResponse<OrganizationResponse> response = CustomApiResponse.<OrganizationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Organization 추가")
                .data(organizationResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "청소년 기관 상세 조회*", description = "모든 청소년 기관의 상세 내용을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/api/organization/{id}")
    public ResponseEntity<CustomApiResponse<OrganizationResponse>> getOrganization(@PathVariable("id") Long id) {
        OrganizationResponse findOrganization = organizationService.findOne(id);

        CustomApiResponse<OrganizationResponse> response = CustomApiResponse.<OrganizationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("기관 단일 검색")
                .data(findOrganization)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "청소년 기관 전체 조회", description = "모든 청소년 기관의 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @GetMapping("/api/organization")
    public ResponseEntity<CustomApiResponse<List<OrganizationResponse>>> getOrganizations() {
        List<OrganizationResponse> organizations = organizationService.findOrganizations();

        CustomApiResponse<List<OrganizationResponse>> response = CustomApiResponse.<List<OrganizationResponse>>builder()
                .success(ApiStatus.SUCCESS)
                .message("기관 전체 검색")
                .data(organizations)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "청소년 기관 수정*", description = "청소년 기관정보의 내용을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @PatchMapping("/api/organization/{id}")
    public ResponseEntity<CustomApiResponse<OrganizationResponse>> updateOrganization(
            @PathVariable("id") Long id,
            @RequestBody OrganizationRequest organizationRequest
    ) {
        OrganizationResponse organizationResponse = organizationService.updateOrganization(id, organizationRequest);

        CustomApiResponse<OrganizationResponse> response = CustomApiResponse.<OrganizationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Organization 수정")
                .data(organizationResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "청소년 기관 삭제*", description = "청소년 기관 정보를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND")
            })
    @DeleteMapping("/api/organization/{id}")
    public ResponseEntity<CustomApiResponse<OrganizationResponse>> updateOrganization(@PathVariable("id") Long id) {
        OrganizationResponse organizationResponse = organizationService.deleteOrganization(id);

        CustomApiResponse<OrganizationResponse> response = CustomApiResponse.<OrganizationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Organization 삭제")
                .data(organizationResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
