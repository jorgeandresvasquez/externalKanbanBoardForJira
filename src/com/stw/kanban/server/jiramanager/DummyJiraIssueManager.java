package com.stw.kanban.server.jiramanager;

import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.client.entities.Project;

public class DummyJiraIssueManager implements JiraManager {

	@Override
	public Board getIssuesByKanbanBoardId(String id) throws Exception {
		return createStubBoard();
	}

	
	private Board createStubBoard() {
		Board board = new Board();
		board.setDescription("Team Black");
		
		BoardColumn column = new BoardColumn();
		column.setName("Ready For Dev");
		
		column.addIssue(stubJiraIssue());
		column.addIssue(stubJiraIssue());
		
		BoardColumn column2 = new BoardColumn();
		column2.setName("In Development");
		column2.addIssue(stubJiraIssue());
		column2.addIssue(stubJiraIssue());
		column2.addIssue(stubJiraIssue());
		
		BoardColumn column3 = new BoardColumn();
		column3.setName("Code Review");
		column3.addIssue(stubJiraIssue());
		
		board.addColumn(column);
		board.addColumn(column2);
		board.addColumn(column3);
		
		return board;
	}
	
	private JiraIssue stubJiraIssue() {
		Project p = new Project();
		p.setName("Abc");
		
		Assignee assignee = new Assignee();
		assignee.setFullName("Mike McLean");
		
		JiraIssue issue = new JiraIssue();
		issue.setProject(p);
		issue.setKey("MYSQL-18");
		issue.setAssignee(assignee);
		issue.setSummary("Sprinkler complaining about incoming packet length");
		
		return issue;
	}
}
