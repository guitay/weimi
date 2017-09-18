/**
 * 
 */
package com.tiancom.pas.activiti.bean;

import com.tiancom.pas.common.result.Model;

public class TaskBean<T extends Model> {

	private T bean;

	public TaskBean() {
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}
}
