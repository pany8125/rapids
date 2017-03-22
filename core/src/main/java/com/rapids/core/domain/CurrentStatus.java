package com.rapids.core.domain;
/**
 * @author scott
 *
 * 2017年3月7日
 * 
 */

public enum CurrentStatus {
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	LOGICAL_DELETED("LOGICAL_DETELTED");
	private final String status;
	
	private CurrentStatus(String status){
		this.status = status;
	}
	
	public String value(){
		return status;
	}
}
