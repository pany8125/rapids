package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author David on 17/2/28.
 */
@Entity
@Data
public class Knowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Long packId;
    @Column(unique = true)
    private String title;
    private String description;
    private String descPic;
    private String memo;
    private String memoPic;
    private Date createTime;
    private Date lastUpdateTime;
    private String editor;

    public enum Impress {
        REMEMBER, HESITATE, FORGET;
    }
}
