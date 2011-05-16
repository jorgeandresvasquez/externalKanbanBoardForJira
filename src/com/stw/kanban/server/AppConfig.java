package com.stw.kanban.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class AppConfig {

	private String userName;
	private String password;
	private String jiraSoapUrl;
	private boolean skipSslValidation;
	private int refreshInterval;
	
	@Inject
	public AppConfig(@Named("jira.soap.userName") String userName, @Named("jira.soap.password") String password, @Named("jira.soap.url") String jiraSoapUrl, 
			@Named("jira.soap.skipSslValidation") boolean skipSslValidation, @Named("refreshInterval") int refreshInterval) {
		this.userName = userName;
		this.password = password;
		this.jiraSoapUrl = jiraSoapUrl;
		this.skipSslValidation = skipSslValidation;
		this.refreshInterval = refreshInterval;
	}


	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public String getJiraSoapUrl() {
		return jiraSoapUrl;
	}


	public boolean isSkipSslValidation() {
		return skipSslValidation;
	}
	
	public int getRefreshInterval() {
		return refreshInterval;
	}
	
}
