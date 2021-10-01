package kr.go.mapo.mpyouth.api;

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
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/api/organization")
    public ResponseEntity<CustomApiResponse<OrganizationResponse>> createOrganization(@RequestBody OrganizationRequest organizationRequest) {
        OrganizationResponse organizationResponse = organizationService.saveOrganization(organizationRequest);

        CustomApiResponse<OrganizationResponse> response = CustomApiResponse.<OrganizationResponse>builder()
                .success(ApiStatus.SUCCESS)
                .message("Organization 추가")
                .data(organizationResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

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

    @PutMapping("/api/organization/{id}")
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
