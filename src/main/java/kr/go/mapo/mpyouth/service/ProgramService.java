package kr.go.mapo.mpyouth.service;


import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.ProgramFile;
import kr.go.mapo.mpyouth.domain.ProgramThumbnail;
import kr.go.mapo.mpyouth.exception.NotFoundProgramException;
import kr.go.mapo.mpyouth.global.mapper.ProgramMapper;
import kr.go.mapo.mpyouth.payload.request.ProgramFileRequest;
import kr.go.mapo.mpyouth.payload.request.ProgramRequest;
import kr.go.mapo.mpyouth.payload.request.ProgramUpdateRequest;
import kr.go.mapo.mpyouth.payload.response.ProgramResponse;
import kr.go.mapo.mpyouth.payload.response.ProgramYouthResponse;
import kr.go.mapo.mpyouth.repository.ProgramFileRepository;
import kr.go.mapo.mpyouth.repository.ProgramRepository;
import kr.go.mapo.mpyouth.repository.ProgramThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramFileRepository programFileRepository;
    private final ProgramThumbnailRepository programThumbnailRepository;
    private final ProgramMapper programMapper;
    private final EntityManager entityManager;
    private final ResourceLoader resourceLoader;

    @Value("${file.dir}")
    String dir;

    @Value("${file.uri_path}")
    String uriPath;

    @Transactional
    public ProgramResponse saveProgram(ProgramRequest programRequest, List<MultipartFile> imageFiles, String fileUri) throws IOException {
        String filePath = fileUri + uriPath;

        log.info("filePath : {}", filePath);

        Program newProgram = programMapper.saveDtoToProgram(programRequest);
        programRepository.save(newProgram);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            MultipartFile firstImage = imageFiles.get(0);

            ProgramThumbnail programThumbnail = makeThumbnail(firstImage);
            if (programThumbnail != null) {
                programThumbnail.setProgram(newProgram);
                programThumbnailRepository.save(programThumbnail);
            }
        }


        List<ProgramFile> programFiles = saveImageFiles(newProgram, imageFiles);

        programFileRepository.saveAll(programFiles);

        //


        //

        entityManager.flush();
        entityManager.clear();

        Program findProgram = programRepository.findById(newProgram.getId()).orElseThrow(() -> new NotFoundProgramException("조건에 맞는 프로그램이 없습니다."));

        ProgramResponse programToDto = programMapper.getProgramToDto(findProgram);

        return programToDto;

    }

    private List<ProgramFile> saveImageFiles(Program program, List<MultipartFile> imageFiles) throws IOException {
        List<ProgramFile> result = new ArrayList<>();

        if (imageFiles != null && !imageFiles.isEmpty()) {

            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) {

                    BufferedImage image = ImageIO.read(imageFile.getInputStream());

                    String newName = UUID.randomUUID()
                            + "."
                            + FilenameUtils.getExtension(imageFile.getOriginalFilename());

                    ProgramFileRequest programFile = ProgramFileRequest.builder()
                            .originalFileName(imageFile.getOriginalFilename())
                            .fileName(newName)
                            .filePath(dir + newName)
                            .fileSize(imageFile.getSize() / 1024)
                            .programId(program.getId())
                            .build();

                    imageFile.transferTo(new File(dir + newName));

                    ProgramFile newProgramFile = programMapper.saveDtoToProgram(programFile);

//                    Program program = programRepository.findById(programId).orElseThrow(() -> new NotFoundProgramException("조건에 맞는 프로그램이 없습니다."));
                    newProgramFile.setProgram(program);

//                    programFileRepository.save(newProgramFile);
                    result.add(newProgramFile);
                }
            }
        }
        return result;
    }


    private ProgramThumbnail makeThumbnail(MultipartFile firstImage) throws IOException {
        if (!firstImage.isEmpty()) {
            BufferedImage read = ImageIO.read(firstImage.getInputStream());
            BufferedImage resize = Scalr.resize(read, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, 200);

            String thumbnailName = "thumb_" + UUID.randomUUID()
                    + "."
                    + FilenameUtils.getExtension(firstImage.getOriginalFilename());

            log.info("originalName : {}, fileName : {}, filePath : {}",
                    firstImage.getOriginalFilename(),
                    thumbnailName,
                    dir + thumbnailName
            );

            ImageIO.write(resize,
                    Objects.requireNonNull(FilenameUtils.getExtension(firstImage.getOriginalFilename())),
                    new File(dir + thumbnailName)
            );

            File file = new File(dir + thumbnailName);



            log.info("thumbnail size : {}", file.length() / 1024);


            return ProgramThumbnail.builder()
                    .originalFileName(firstImage.getOriginalFilename())
                    .fileName(thumbnailName)
                    .filePath(dir + thumbnailName)
                    .fileSize(file.length() / 1024)
                    .build();
        } else {
            return null;
        }
    }

    @Transactional
    public ProgramResponse updateProgram(Long id, ProgramUpdateRequest programRequest, List<MultipartFile> imageFiles, String fileUri) throws IOException {
//        Long updateId = programRequest.getProgramId();
        String filePath = fileUri + uriPath;
        Program updateProgram = programRepository.findById(id).orElseThrow(() -> new NotFoundProgramException("조건에 맞는 프로그램이 없습니다."));

        if (imageFiles != null && !imageFiles.isEmpty()) {
            updateProgram.getProgramFiles().clear();

            List<ProgramFile> programFiles = saveImageFiles(updateProgram, imageFiles);

            programFileRepository.saveAll(programFiles);

            entityManager.flush();
            entityManager.clear();

            updateProgram = programRepository.findById(id).orElseThrow(() -> new NotFoundProgramException("조건에 맞는 프로그램이 없습니다."));
        }
        //
        //

        programMapper.updateDtoToProgram(programRequest, updateProgram);

        return programMapper.getProgramToDto(updateProgram);
    }


    public Page<ProgramYouthResponse> findYouthPrograms(Pageable pageable) {
        Page<Program> programs = programRepository.findPrograms(pageable);
        Page<ProgramYouthResponse> result = programs.map(programMapper::getProgramsToYouths);


        return result;
    }

    public ProgramResponse findOne(Long programId) {
        Program findProgram = programRepository.findById(programId).orElseThrow(() -> new NotFoundProgramException("조건에 맞는 프로그램이 없습니다."));

        log.info("findProgram : {}", findProgram);


        ProgramResponse programToDto = programMapper.getProgramToDto(findProgram);

        log.info("programToDto : {}", programToDto);


        return programToDto;
    }

    @Transactional
    public ProgramResponse deleteProgram(Long programId) {
        Program program = programRepository.findById(programId).orElseThrow(() -> new NotFoundProgramException("조건에 맞는 프로그램이 없습니다."));
        programRepository.deleteById(programId);

        return programMapper.getProgramToDto(program);
    }

    public Page<ProgramYouthResponse> findByKeyword(String keyword, Pageable pageable) {
//        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Program> findPrograms = programRepository.findProgramByKeyword(keyword, pageable);

        return findPrograms.map(programMapper::getProgramsToYouths);

    }

}
