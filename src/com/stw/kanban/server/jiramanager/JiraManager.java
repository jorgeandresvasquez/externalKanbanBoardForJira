package com.stw.kanban.server.jiramanager;

import com.stw.kanban.client.entities.Board;

public interface JiraManager {
	Board getIssuesByKanbanBoardId(String id) throws Exception;	
}
