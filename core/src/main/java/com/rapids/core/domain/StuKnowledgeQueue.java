package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author David on 17/2/28.
 */
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = { "studentId", "knowledgeId" })})
public class StuKnowledgeQueue {
    @Id
    private String id;
    private Long studentId;
    private Long knowledgeId;
    private Date lastLearnTime;
    private Status status = Status.CREATED;

    public enum Status {
        CREATED, LEARNING, FINISH;
    }
}
