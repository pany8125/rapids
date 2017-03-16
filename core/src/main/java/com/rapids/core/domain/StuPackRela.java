package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author David on 17/2/28.
 */
@Data
@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = { "studentId", "packId" })},
        indexes = @Index(columnList = "studentId")
)
public class StuPackRela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long packId;
    private String packName;
    private Integer knowledgeNum;
    private Integer learnedNum;
    private Date lastLearnTime;
    private Date createTime;
    private Status status;

    public enum Status {
        WAIT, LEARNING, FINISH;
    }
}
