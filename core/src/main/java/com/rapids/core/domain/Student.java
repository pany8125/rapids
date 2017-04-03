package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author David on 17/2/28.
 */
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gradeId;
    @Column(unique = true)
    private String mobile;
    private String password;
    private String name;
    private Integer age;
    private Integer sex;
    private String email;
    private Date createTime;
    private String editor;
    private Date lastUpdateTime;
}
