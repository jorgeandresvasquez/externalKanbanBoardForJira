package com.stw.kanban.client.entities;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class BoardColumn implements KanbanBoardColumn, Serializable {
	
	private String name;
	private ArrayList<StickyNoteIssue> issues = new ArrayList<StickyNoteIssue>();
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public ArrayList<StickyNoteIssue> getIssues() {
		return issues;
	}
	
	@Override
	public void addIssue(StickyNoteIssue issue) {
		JiraIssue jiraIssue = (JiraIssue) issue;
		addJiraIssue(jiraIssue);
	}
	
	public void addJiraIssue(JiraIssue jiraIssue) {
		issues.add(jiraIssue);
	}
}
