package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author David on 17/3/9.
 */
@Data
@Entity
public class StudyLog {

    @Id
    private String id;
    private Long studentId;
    private Long knowledgeId;
    private Long packId;
    private Integer learnTimes;
    private Date logTime;

}
