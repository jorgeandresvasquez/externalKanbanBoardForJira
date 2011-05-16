package com.stw.kanban.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.ConfigOptions;

public interface KanbanBoardServiceAsync {

	void getKanbanBoard(String id, AsyncCallback<Board> callback);
	
	void getConfig(AsyncCallback<ConfigOptions> callback);

}
