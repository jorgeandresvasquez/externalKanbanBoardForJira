package com.stw.kanban.client.widget.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.stw.kanban.client.KanbanBoardServiceAsync;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.widget.gin.RequestUrlId;
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
//	private String viewRequestUrlId;
	private String requestUrlId;
	
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
	public KanbanBoardPresenter(@RequestUrlId String requestUrlId, KanbanBoardServiceAsync rpcService, EventBus eventBus, KanbanBoardView<Board> view) {
//		viewRequestUrlId = Location.getParameter("id");
		this.requestUrlId = requestUrlId;
		this.service = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		
		bind();
	}
	
	public void bind () {
		//Here we add all eventHandlers to the injected EventBus.
	}

	@Override
	public void execute(HasWidgets container) {
		container.clear();
		container.add(view.asWidget()); 	// container.add(display.asWidget()); 
//		getRemoteLoadKanbanBoardView(viewRequestUrlId);
		getRemoteLoadKanbanBoardView(Location.getParameter(requestUrlId));
	}
	
	public void getRemoteLoadKanbanBoardView(String requestViewUrlId) {
		view.maskView(true);
		service.getKanbanBoard(requestViewUrlId, new AsyncCallback<Board>() {
			@Override
			public void onSuccess(Board board) {
				loadView(board);
				onBoardLoaded();
			}
			
			@Override
			public void onFailure(Throwable caught) { 
				view.maskView(false);
				view.loadError(caught.getMessage());
			}
		});
	}
	
	public void loadView(Board board) {
		if (board == null) {
			return;
		} else {
			kanbanBoard = board;
			view.setViewTitle(kanbanBoard.getDescription()); 	// KanbanBoardPresenter.this.display.setData(board);
			view.setData(board);
		}
	}

	@Override
	public void onBoardLoaded() {
		view.maskView(false);
	}
	
	public void onError(String errorMessage) {
		view.loadError(errorMessage);
	}
	
	public String getRequestUrlId() {
		return requestUrlId;
	}
	
	public KanbanBoardView<Board> getKanbanBoardView() {
		return view;
	}
}
