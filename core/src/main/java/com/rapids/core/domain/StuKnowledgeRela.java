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
        indexes = @Index(columnList = "studentId, knowledgeId", unique = true)
)
public class StuKnowledgeRela {
    @Id
    private String id;
    private Long studentId;
    private Long knowledgeId;
    private Date createTime;
    private Long packId;
    /**
     * 已复习
     */
    private boolean reviewed = false;
    /**
     * 复习次数
     */
    private Integer reviewCount = 0;
    private Date lastReviewTime;
    /**
     * 是否激活
     */
    private boolean enabled = false;
    /**
     * 激活时间
     */
    private Date enableTime;

    private Date reviewTime;
    /**
     * 已熟记
     */
    private boolean memorized = false;
    /**
     * 已完成记忆/已删除 无需再复习
     */
    private boolean deleted = false;
    /**
     * 复习延迟时间点
     */
    private Long leanSeq = 0L;
    /**
     * 学生在页面重复显示几次, 记录已复习时间的时候清零
     */
    private Integer viewCount = 0;
}
