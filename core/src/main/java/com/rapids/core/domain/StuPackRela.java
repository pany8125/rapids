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
    /**
     * 最后一次记住知识点的时间,即将知识点标记为"完成记忆",无需再复习的时间
     */
    private Date lastLearnTime;
    private Date createTime;
    private Status status;

    public enum Status {
        WAIT, LEARNING, FINISH;
    }
}
