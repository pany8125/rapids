package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zhipeng.tian
 *         <p>
 *         2014年9月29日
 */

@Data
@Entity
public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name; // 角色名
    private String description; // 角色描述
    private String status;
}
