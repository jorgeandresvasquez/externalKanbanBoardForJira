package com.stw.kanban.client.widget.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
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

	@Inject
	private KanbanBoardServiceAsync service;
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
		container.add(view.asWidget()); 	// container.add(display.asWidget()); 
	}
	
	public void loadView(String id) {
		view.maskView(true);
		service.getKanbanBoard(id, new AsyncCallback<Board>() {
			@Override
			public void onSuccess(Board board) {
				loadKanbanBoardView(board);
				onLoadedBoard();
			}
			
			@Override
			public void onFailure(Throwable caught) { 
				view.maskView(false);
				view.loadError(caught.getMessage());
			}
		});
	}
	
	public void loadKanbanBoardView(Board board) {
		kanbanBoard = board;
		view.setViewTitle(kanbanBoard.getDescription()); 	// KanbanBoardPresenter.this.display.setData(board);
		view.setData(board);
	}

	@Override
	public void onLoadedBoard() {
		view.maskView(false);
	}


	public String getViewId() {
		return viewId;
	}
	
}
