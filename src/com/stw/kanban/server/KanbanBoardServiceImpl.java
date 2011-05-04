package com.stw.kanban.server;

import com.atlassian.jira.rpc.exception.RemoteAuthenticationException;
import com.google.inject.Inject;
import com.stw.kanban.client.BoardRetrievalException;
import com.stw.kanban.client.KanbanBoardService;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.server.jiramanager.JiraManager;

public class KanbanBoardServiceImpl implements	KanbanBoardService {
	private JiraManager jiraManager;
	
	@Inject
	public KanbanBoardServiceImpl(JiraManager jiraManager) {
		this.jiraManager = jiraManager;
	}
	
	@Override
	public Board getKanbanBoard(String id) throws Exception {
		try {
			return jiraManager.getIssuesByKanbanBoardId(id);
		} catch(Exception e) {
			BoardRetrievalException exception = new BoardRetrievalException();
			exception.setMessage(e.getMessage());
			//This specific instance doesn't have any message in the exception
			if (e instanceof RemoteAuthenticationException) {
				exception.setMessage("Invalid credentials to access jira");
			}
			throw exception;
		}  
	}
	
}
