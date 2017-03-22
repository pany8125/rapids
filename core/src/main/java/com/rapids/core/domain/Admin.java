package com.rapids.core.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author scott
 * 
 * 2017年3月7日
 */

@Data
@Entity
public class Admin{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uid;
	private String password;
	private String name;
	private String mobile;
	private String createTime;
	private String createBy;
}
