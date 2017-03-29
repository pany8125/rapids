package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author David on 17/2/22.
 */
@Entity
@Data
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Type type;

    private String description;


    private String createTime;
    private String createBy;

    @SuppressWarnings({"unused", "WeakerAccess"})
    public enum Type {
        ENGLISH, MATH, LITERATURE
    }

}
