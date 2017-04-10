package com.rapids.core.domain;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author scott
 * 
 * 2017年3月7日
 */

@Data
@Entity
@Table(
		indexes = @Index(columnList = "adminId, roleId", unique = true)
)
public class AdminRoleMember{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer adminId;
	private Integer roleId;
}
