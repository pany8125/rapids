package com.rapids.manage.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @author zhipeng.tian
 * 
 * 2014年9月29日
 */
@Service
public class UploadService {
	private static final Logger LOGGER = LoggerFactory
            .getLogger(UploadService.class);
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Value("${file.download.url}")
	private String downloadUrl;
	
	public void uploadFile(MultipartFile file,HttpServletResponse response){
		try{
			String result = null;
	        Writer writer = response.getWriter();
	        String md5 = "";
	        if (file.getSize() <= 0) {
	            writer.write("{'success': false,'msg': 'uploadfailed'}");
	            writer.close();
	            writer.flush();
	            return;
	        }
	
	        try {
	        	LOGGER.info("/upload/upload.do Upload  file {} ...", file.getName());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        	String tempPath = sdf.format(new Date())+"/";
	        	String path = uploadPath+tempPath;
				File filec = new File(path);
				if(!filec.exists()){
					filec.mkdirs();
				}
	            String str = file.getOriginalFilename();
	            
	            // 将文件名进行改变，以防止覆盖
	            String fileName = UUID.randomUUID().toString().replace("-", "") + str.substring(str.lastIndexOf("."));
	            
	            file.transferTo(new File(path + fileName));
	            
	            FileInputStream fis = new FileInputStream(new File(path + fileName));
	            md5 = DigestUtils.md5Hex(fis);
	            fis.close();
	            
	            result = downloadUrl + tempPath + fileName;
	
	        } catch (Exception ex) {
	            LOGGER.error("Upload files in folder {} failure", file.getOriginalFilename(), ex);
	            writer.write("{'success': false,'msg': 'uploadfailed'}");
	            writer.close();
	            writer.flush();
	            return;
	        }
	        response.setContentType("text/html; charset=utf-8");
	    	
	    	writer.write("{'success': true, 'msg': '" + result + "', 'size': '" + file.getSize() + "', 'md5': '" + md5 + "'}");
	    	writer.close();
	    	writer.flush();
		}catch(Exception ex){
			LOGGER.error("upload error",ex);
		}
	}
}
