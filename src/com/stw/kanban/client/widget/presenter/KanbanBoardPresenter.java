package com.stw.kanban.client.widget.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.stw.kanban.client.KanbanBoardServiceAsync;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.widget.view.KanbanBoardView;


/* GWT Best Practices - Large Scale Applications Development MVP: 
 * 
 * Generic views for more flexible extensions. 
 * */
public class KanbanBoardPresenter implements AbstractPresenter, KanbanBoardView.Presenter<Board>  {
	
//	public interface Display {
//		HasWidgets getKanbanBoard();
//		Widget asWidget();
//		void setData(Board data);
//	}

	private final Label loadingLabel = new Label("Loading");
	private final KanbanBoardServiceAsync service;
	private final EventBus eventBus;
//	private Display display;
	private KanbanBoardView<Board> view;
	private Board kanbanBoard;
	private String viewId;
	
//	@Inject
//	public KanbanBoardPresenter(KanbanBoardServiceAsync rpcService, EventBus eventBus, Display display) {
//		String id = Location.getParameter("id");
//		this.service = rpcService;
//		this.eventBus = eventBus;
//		this.display = display;
//		bind();
//		loadView(id);
//	}
	
	@Inject
	public KanbanBoardPresenter(KanbanBoardServiceAsync rpcService, EventBus eventBus, KanbanBoardView<Board> view) {
		viewId = Location.getParameter("id");
		this.service = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		bind();
		loadView(viewId);
	}
	
	public void bind () {
		//Here we add all eventHandlers to the injected EventBus.
	}

	@Override
	public void execute(HasWidgets container) {
		container.clear();
//		container.add(display.asWidget());
		container.add(view.asWidget());
	}
	
	public void loadView(String id) {
		service.getKanbanBoard(id, new AsyncCallback<Board>() {
			@Override
			public void onSuccess(Board board) {
				loadKanbanBoardView(board);
			}
			
			@Override
			public void onFailure(Throwable caught) { 
				Window.alert("Error retrieving data!");
			}
		});
	}
	
	public void loadKanbanBoardView(Board board) {
		kanbanBoard = board;
		Window.setTitle(kanbanBoard.getDescription());
		
//		KanbanBoardPresenter.this.display.setData(board);
		view.setData(board);
		
		loadingLabel.setVisible(false);
	}

	@Override
	public void onLoadedBoard() {
		// TODO Auto-generated method stub	
	}

	public String getViewId() {
		return viewId;
	}
	

	
}
