package com.stw.kanban.client.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BoardColumn implements Serializable {
	private String name;
	List<JiraIssue> issues = new ArrayList<JiraIssue>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<JiraIssue> getIssues() {
		return issues;
	}
	public void addJiraIssue(JiraIssue jiraIssue) {
		issues.add(jiraIssue);
	}
}
