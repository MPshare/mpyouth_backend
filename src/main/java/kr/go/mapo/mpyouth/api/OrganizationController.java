package kr.go.mapo.mpyouth.api;

import kr.go.mapo.mpyouth.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/api/organization")
    public ResponseEntity<ApiResponse<OrganizationResponse>> createOrganization(@RequestBody OrganizationRequest organizationRequest) {
        OrganizationResponse organizationResponse = organizationService.saveOrganization(organizationRequest);

        ApiResponse<OrganizationResponse> response = ApiResponse.<OrganizationResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message("Organization 추가")
                .data(organizationResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/api/organization/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getOrganization(@PathVariable("id") Long id) {
        OrganizationResponse findOrganization = organizationService.findOne(id);

        ApiResponse<OrganizationResponse> response = ApiResponse.<OrganizationResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message("기관 단일 검색")
                .data(findOrganization)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/organization")
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> getOrganizations() {
        List<OrganizationResponse> organizations = organizationService.findOrganizations();

        ApiResponse<List<OrganizationResponse>> response = ApiResponse.<List<OrganizationResponse>>builder()
                .status(ApiStatus.SUCCESS)
                .message("기관 전체 검색")
                .data(organizations)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/organization/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(
            @PathVariable("id") Long id,
            @RequestBody OrganizationRequest organizationRequest
    ) {
        OrganizationResponse organizationResponse = organizationService.updateOrganization(id, organizationRequest);

        ApiResponse<OrganizationResponse> response = ApiResponse.<OrganizationResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message("Organization 수정")
                .data(organizationResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/organization/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(@PathVariable("id") Long id) {
        OrganizationResponse organizationResponse = organizationService.deleteOrganization(id);

        ApiResponse<OrganizationResponse> response = ApiResponse.<OrganizationResponse>builder()
                .status(ApiStatus.SUCCESS)
                .message("Organization 삭제")
                .data(organizationResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
