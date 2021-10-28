package kr.go.mapo.mpyouth.service;


import kr.go.mapo.mpyouth.domain.Program;
import kr.go.mapo.mpyouth.domain.ProgramFile;
import kr.go.mapo.mpyouth.domain.ProgramThumbnail;
import kr.go.mapo.mpyouth.global.mapper.ProgramMapper;
import kr.go.mapo.mpyouth.payload.request.ProgramFileRequest;
import kr.go.mapo.mpyouth.payload.request.ProgramYouthRequest;
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
import javax.persistence.NoResultException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public ProgramResponse saveProgram(ProgramYouthRequest programYouthRequest, List<MultipartFile> imageFiles, String fileUri) throws IOException {
        String filePath = fileUri + uriPath;

        log.info("filePath : {}", filePath);

        Program newProgram = programMapper.saveDtoToProgram(programYouthRequest);
        programRepository.save(newProgram);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            MultipartFile firstImage = imageFiles.get(0);

            ProgramThumbnail programThumbnail = makeThumbnail(firstImage);
            thumbnailCheck(newProgram, programThumbnail);
        }

        List<ProgramFile> programFiles = saveImageFileList(newProgram, imageFiles);

        programFileRepository.saveAll(programFiles);

        entityManager.flush();
        entityManager.clear();

        Program findProgram = findEntity(newProgram.getId());

        return programMapper.getProgramToDto(findProgram);

    }

    @Transactional
    public ProgramResponse updateProgram(Long id, ProgramUpdateRequest programRequest, List<MultipartFile> imageFiles, String fileUri) throws IOException {
        String filePath = fileUri + uriPath;
        Program updateProgram = findEntity(id);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            updateProgram.getProgramFiles().clear();

            List<ProgramFile> programFiles = saveImageFileList(updateProgram, imageFiles);

            programFileRepository.saveAll(programFiles);

            entityManager.flush();
            entityManager.clear();

            updateProgram = findEntity(id);
        }

        programMapper.updateDtoToProgram(programRequest, updateProgram);

        return programMapper.getProgramToDto(updateProgram);
    }


    public Page<ProgramYouthResponse> findYouthPrograms(Pageable pageable) {
        Page<Program> programs = programRepository.findPrograms(pageable);
        Page<ProgramYouthResponse> result = programs.map(programMapper::getProgramsToYouths);

        return result;
    }

    public ProgramResponse findOne(Long programId) {
        Program findProgram = findEntity(programId);

        return programMapper.getProgramToDto(findProgram);
    }

    @Transactional
    public ProgramResponse deleteProgram(Long programId) {
        Program program = findEntity(programId);
        programRepository.deleteById(programId);

        return programMapper.getProgramToDto(program);
    }

    public Page<ProgramYouthResponse> findByKeyword(String keyword, Pageable pageable) {
        Page<Program> findPrograms = programRepository.findProgramByKeyword(keyword, pageable);

        return findPrograms.map(programMapper::getProgramsToYouths);

    }

    private ProgramThumbnail makeThumbnail(MultipartFile firstImage) throws IOException {
        if (!firstImage.isEmpty()) {
            BufferedImage read = ImageIO.read(firstImage.getInputStream());
            BufferedImage resize = Scalr.resize(read, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, 200);

            String thumbnailName = "thumb_" + UUID.randomUUID()
                    + "."
                    + FilenameUtils.getExtension(firstImage.getOriginalFilename());

            ImageIO.write(resize,
                    Objects.requireNonNull(FilenameUtils.getExtension(firstImage.getOriginalFilename())),
                    new File(dir + thumbnailName)
            );

            File file = new File(dir + thumbnailName);

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

    private Program findEntity(Long id) {
        return programRepository.findById(id).orElseThrow(() -> new NoResultException("조건에 맞는 프로그램이 없습니다."));
    }

    private void thumbnailCheck(Program newProgram, ProgramThumbnail programThumbnail) {
        if (programThumbnail != null) {
            programThumbnail.setProgram(newProgram);
            programThumbnailRepository.save(programThumbnail);
        }
    }

    private List<ProgramFile> saveImageFileList(Program program, List<MultipartFile> imageFiles) throws IOException {
        List<ProgramFile> result = new ArrayList<>();

        if (imageFiles != null && !imageFiles.isEmpty()) {

            saveFiles(program, imageFiles, result);
        }
        return result;
    }

    private void saveFiles(
            Program program, List<MultipartFile> imageFiles, List<ProgramFile> result
    ) throws IOException {
        for (MultipartFile imageFile : imageFiles) {
            saveFile(program, result, imageFile);
        }
    }

    private void saveFile(Program program, List<ProgramFile> result, MultipartFile imageFile) throws IOException {
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

            newProgramFile.setProgram(program);

            result.add(newProgramFile);
        }
    }

}
