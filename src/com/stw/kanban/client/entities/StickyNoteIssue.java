package com.stw.kanban.client.entities;

public interface StickyNoteIssue {

	public Assignee getAssignee();

	public void setAssignee(Assignee assignee);

	public String getKey();

	public void setKey(String key);

	public IssuePriority getIssuePriority();

	public void setIssuePriority(IssuePriority issuePriority);

	public Project getProject();

	public void setProject(Project project);

	public String getSummary();

	public void setSummary(String summary);

	public IssueType getIssueType();

	public void setIssueType(IssueType issueType);

	public void setIssueStatus(IssueStatus issueStatus);

	public IssueStatus getIssueStatus();
}
