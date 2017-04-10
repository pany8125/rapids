package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author zhipeng.tian
 *         <p>
 *         2014年9月24日
 */
@Data
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
//    private Integer parentID;
//    private String parentName;
    private String url;
    private Boolean leaf;
//    private CurrentStatus status;
//    private Date createTime;
//    private String createdBy;
//    private String updateby;
//    private Integer roleid;
    private Integer orderNum;

}
