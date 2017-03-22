package com.rapids.core.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author scott
 * 
 * 2017年3月7日
 */

@Data
// @Entity
public class AdminRoleMember{
	private int adminId;
	private int roleId;
}
