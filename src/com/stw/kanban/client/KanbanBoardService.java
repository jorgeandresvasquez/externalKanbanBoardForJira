package com.stw.kanban.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.stw.kanban.client.entities.Board;

@RemoteServiceRelativePath("GWT.rpc")
public interface KanbanBoardService extends RemoteService {
	Board getKanbanBoard(String id) throws Exception;
}
