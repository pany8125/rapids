package com.rapids.manage.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhipeng.tian
 * 
 * 2014年9月25日
 */
@Data
public class ExtEntity<T> {
	private long result = 0;
	private List<T> rows;
}
