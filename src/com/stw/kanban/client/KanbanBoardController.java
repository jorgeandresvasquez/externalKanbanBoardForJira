package com.stw.kanban.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.widget.presenter.AbstractPresenter;
import com.stw.kanban.client.widget.presenter.KanbanBoardPresenter;
import com.stw.kanban.client.widget.view.KanbanBoardViewImpl;

public class KanbanBoardController implements AbstractPresenter, ValueChangeHandler<String> {

	private final EventBus eventBus;
	private final KanbanBoardServiceAsync service;
	private HasWidgets container;
	private KanbanBoardViewImpl<Board> kanbanBoardView;

	@Inject
	public KanbanBoardController(KanbanBoardServiceAsync rpcService, EventBus eventBus) {
		this.service = rpcService;
		this.eventBus = eventBus;
		bind();
	}
	
	@Override
	public void execute(HasWidgets containers) {
		this.container = containers;
		
		//History management
		if ("".equals(History.getToken())) {
			History.newItem("list");
		} else {
			History.fireCurrentHistoryState();
		}
	}
	
	private void bind() {
		History.addValueChangeHandler(this);
		//Bind all other handlers here, if any...
	}
	
	public void onValueChange(ValueChangeEvent<String> event) {
	    String token = event.getValue();
	    
	    if (token != null) {
	      
	    	if (token.equals("list")) {
	    		GWT.runAsync(new RunAsyncCallback() {
	        	@Override
	        	public void onFailure(Throwable caught) {
	        		Window.alert(caught.getMessage());
	        	}
	        	@Override
	        	public void onSuccess() {
	        		// lazily initialize our views, and keep them around to be reused
	        		if (kanbanBoardView == null) {
	        			kanbanBoardView = new KanbanBoardViewImpl<Board>();
	              
	        		}
	        		new KanbanBoardPresenter(service, eventBus, new KanbanBoardViewImpl<Board>()).execute(container);
	        	}
	    	});
	      }
	      else if (token.equals("add") || token.equals("edit")) {

	      }
	    }
	} 
	
	
/*	
 * 
 * 	@Override
 * 	public void onValueChange(ValueChangeEvent<String> event) {
 * 		String token = event.getValue();
 * 
 * 		if (token != null) {
 * 			AbstractPresenter presenter = null;
 * 			
 * 			if (token.equals("list")) {
 * 				presenter = new KanbanBoardPresenter(service, eventBus, new KanbanBoardViewImpl());
 * 			} else if (token.equals("add")) {
 * 				//
 * 			} else if (token.equals("edit")) {
 * 				//
 * 			}
 * 
 * 			if (presenter != null) {
 * 				presenter.execute(container);
 * 			}
 * 		}
 * 	}
 * 
 */
}
