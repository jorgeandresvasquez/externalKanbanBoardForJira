package com.stw.kanban.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.widget.BoardColumnWidget;

public class KanbanBoardEntryPoint implements EntryPoint {
	private final KanbanBoardServiceAsync service = GWT.create(KanbanBoardService.class);
	private Label loadingLabel = new Label("Loading");
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String id = Location.getParameter("id");
		
		RootPanel.get().add(loadingLabel);
		
		service.getKanbanBoard(id, new AsyncCallback<Board>() {
			@Override
			public void onSuccess(Board board) {
				Window.setTitle(board.getDescription());
				RootPanel.get().add(new MainPanel(board));
				loadingLabel.setVisible(false);
			}
			
			@Override
			public void onFailure(Throwable caught) { 
				Window.alert(caught.getMessage());
			}
		});
	}

	class MainPanel extends HorizontalPanel {
		public MainPanel(Board board) {
			setHeight("90%");
			setWidth("90%");
			
			for (BoardColumn c : board.getColumns()) {
				add(new BoardColumnWidget(c));
			}
		}

	}
}
