package kr.go.mapo.mpyouth.api;

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
public class VolunteerController {
    private final VolunteerService volunteerService;

    @GetMapping("/volunteer/{id}")
    public ResponseEntity<?> findVolunteer(@PathVariable Long id){
        VolunteerResponse volunteer = volunteerService.findVolunteer(id);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 단일 조회")
                .data(volunteer)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/volunteer")
    public ResponseEntity<?> saveVolunteer(@RequestBody VolunteerRequest volunteerRequest){
        VolunteerResponse volunteerResponse = volunteerService.saveVolunteer(volunteerRequest);
        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 저장")
                .data(volunteerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/volunteer/{id}")
    public ResponseEntity<?> updateVolunteer(@PathVariable Long id, @RequestBody VolunteerRequest volunteerRequest){
        VolunteerResponse volunteerResponse = volunteerService.updateVolunteer(id, volunteerRequest);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 수정")
                .data(volunteerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/volunteer/{id}")
    public ResponseEntity<?> deleteVolunteer(@PathVariable Long id){
        VolunteerResponse deleteVolunteer = volunteerService.deleteVolunteer(id);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 삭제")
                .data(deleteVolunteer)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/volunteer")
    public ResponseEntity<?> getVolunteerList(Pageable pageable){
        Page<VolunteerResponse> volunteerList = volunteerService.findVolunteerList(pageable);

        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 전체 조회")
                .data(volunteerList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/volunteer/search")
    public ResponseEntity<?> searchVolunteer(@RequestParam("keyword") String keyword, Pageable pageable){
        Page<VolunteerResponse> volunteerResponses = volunteerService.searchVolunteer(keyword, pageable);
        CustomApiResponse<?> response = CustomApiResponse.builder()
                .success(ApiStatus.SUCCESS)
                .message("자원봉사 단일 조회")
                .data(volunteerResponses)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
