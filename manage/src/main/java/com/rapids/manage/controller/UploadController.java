package com.rapids.manage.controller;

import com.rapids.manage.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by scott on 4/14/17.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/pic", method = RequestMethod.POST)
    public void upload(@RequestParam(value = "picFile") MultipartFile file,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.uploadService.uploadFile(file, response);
        LOGGER.info("upload file");
    }

    @RequestMapping(value = "/memo", method = RequestMethod.POST)
    public void uploadMemo(@RequestParam(value = "memoFile") MultipartFile file,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.uploadService.uploadFile(file, response);
        LOGGER.info("upload file");
    }

}
