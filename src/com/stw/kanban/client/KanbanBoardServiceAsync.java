package com.stw.kanban.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.stw.kanban.client.entities.Board;

public interface KanbanBoardServiceAsync {
	void getKanbanBoard(String id, AsyncCallback<Board> callback) throws IllegalArgumentException;
}
