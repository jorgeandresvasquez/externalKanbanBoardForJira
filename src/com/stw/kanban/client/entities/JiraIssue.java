package com.stw.kanban.client.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JiraIssue implements Serializable {

	private Assignee assignee;
	
	private String key;
	
	private IssuePriority issuePriority;
	
	private Project project;
	
	private String summary;
	
	private IssueType issueType;
	
	private IssueStatus issueStatus;

	public Assignee getAssignee() {
		return assignee;
	}

	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public IssuePriority getIssuePriority() {
		return issuePriority;
	}

	public void setIssuePriority(IssuePriority issuePriority) {
		this.issuePriority = issuePriority;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public void setIssueStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}

	public IssueStatus getIssueStatus() {
		return issueStatus;
	}
	
}
