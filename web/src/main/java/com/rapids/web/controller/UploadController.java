package com.rapids.web.controller;

import com.rapids.core.domain.Knowledge;
import com.rapids.core.domain.StuKnowledgeRela;
import com.rapids.core.domain.Student;
import com.rapids.core.repo.KnowledgeRepo;
import com.rapids.core.repo.StuKnowledgeRelaRepo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @author David on 17/3/8.
 */
@RestController
@RequestMapping("/my")
@ConfigurationProperties("rapids.static")
@SuppressWarnings({"unused", "WeakerAccess"})
public class UploadController extends LoginedController{

    private Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    private @Setter @Getter String webPath;
    private @Setter @Getter String localPath;
    private @Autowired KnowledgeRepo knowledgeRepo;
    private @Autowired StuKnowledgeRelaRepo stuKnowledgeRelaRepo;
    private @Value("${server.website-path}") String websitePath;

    @PostMapping("/knowledge")
    public ResponseEntity uploadKnowledge(
            @RequestParam(value = "descPic", required = false) MultipartFile descFile,
            @RequestParam(value = "memoPic", required = false) MultipartFile memoFile,
            @Valid KnowledgeReq knowledgeReq) throws IOException, ImageReadException, URISyntaxException {

        LOGGER.info("upload knowledge : {}", knowledgeReq);
        Knowledge knowledge = new Knowledge();
        BeanUtils.copyProperties(knowledgeReq, knowledge);
        knowledge.setDescPic(checkAndSaveFile(descFile));
        knowledge.setDescPic(checkAndSaveFile(memoFile));
        knowledge.setCreateTime(new Date());
        knowledge.setPackId(-1L);
        knowledge.setName(knowledge.getTitle());
        knowledge.setEditor(createEditorName());
        createKnowledge(knowledge, currentStudent());
        return ResponseEntity.status(HttpStatus.FOUND).location(new URI(websitePath + "/upload_success.html")).build();
    }

    private String createEditorName() {
        return "student:" + currentStudent().getId();
    }

    @Transactional
    public void createKnowledge(Knowledge knowledge, Student student) {
        knowledgeRepo.save(knowledge);
        StuKnowledgeRela stuKnowledgeRela = new StuKnowledgeRela();
        stuKnowledgeRela.setId(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()));
        stuKnowledgeRela.setKnowledgeId(knowledge.getId());
        stuKnowledgeRela.setStudentId(student.getId());
        stuKnowledgeRela.setCreateTime(new Date());
        stuKnowledgeRela.setPackId(-1L);
        stuKnowledgeRela.setEnabled(true);
        stuKnowledgeRelaRepo.save(stuKnowledgeRela);
    }

    @GetMapping("/checkName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void checkNameUnique(@PathVariable String name) {
        if(StringUtils.isBlank(name)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        Knowledge knowledge = knowledgeRepo.findByNameAndEditor(name, createEditorName());
        if(null != knowledge) {
            throw new HttpClientErrorException(HttpStatus.FOUND);
        }
    }

    private String checkAndSaveFile(MultipartFile file) throws IOException {
        try {
            if(0 != file.getSize()) {
                String fileType = Imaging.getImageInfo(file.getBytes()).getFormat().getName();
                String subPath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
                File localStaticFilePath = new File(localPath + subPath);
                FileUtils.forceMkdir(localStaticFilePath);
                String newFileName = DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()) + "." + fileType;
                File newFile = new File(localStaticFilePath + newFileName);
                file.transferTo(newFile);
                LOGGER.debug("desc file saved : {}", newFile);
                return webPath + subPath + newFileName;
            }
        } catch (ImageReadException e) {
            LOGGER.info("image format unknown, file : {}", file.getOriginalFilename());
            throw new HttpClientErrorException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        return null;
    }

    @Data
    private static class KnowledgeReq {
        @NotBlank
        @Length(min = 1, max = 50)
        private String title;
        @NotBlank
        @Length(min = 1, max = 200)
        private String description;
        @Length(max = 200)
        private String memo;
    }
}
