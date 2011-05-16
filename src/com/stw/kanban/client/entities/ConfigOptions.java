package com.stw.kanban.client.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConfigOptions implements Serializable {

	private int refreshInverval = 30000;

	public void setRefreshInverval(int refreshInverval) {
		this.refreshInverval = refreshInverval;
	}

	public int getRefreshInverval() {
		return refreshInverval;
	} 
	
}
