package com.rapis.web.test;

import com.rapids.core.CoreConfig;
import com.rapids.core.domain.Knowledge;
import com.rapids.web.WebBoot;
import com.rapids.web.controller.StudyController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author David on 17/4/8.
 */
public class TestStudyController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void coverTimestamp() throws ParseException {
        String date = "2017-04-09 20:00:00";
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime());
    }
}
