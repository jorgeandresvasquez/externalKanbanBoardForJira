package com.stw.kanban.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.ConfigOptions;
import com.stw.kanban.client.widget.gin.RequestUrlId;
import com.stw.kanban.client.widget.presenter.AbstractPresenter;
import com.stw.kanban.client.widget.presenter.KanbanBoardPresenter;
import com.stw.kanban.client.widget.view.KanbanBoardViewImpl;
import com.stw.kanban.client.widget.view.StickyNoteWidget.StickyNoteWidgetUiBinder;
import com.stw.kanban.resources.KanbanBoardResources;

public class KanbanBoardController implements AbstractPresenter, ValueChangeHandler<String> {

	private final EventBus eventBus;
	private KanbanBoardServiceAsync service;
	private HasWidgets container;
	private KanbanBoardViewImpl<Board> kanbanBoardView;
	private final KanbanBoardResources resources;
	private StickyNoteWidgetUiBinder uiBinder;
	
	@Inject
	KanbanBoardPresenter kanbanBoardPresenter;

	@Inject
	public KanbanBoardController(KanbanBoardServiceAsync rpcService, EventBus eventBus, KanbanBoardResources resources) {
		this.service = rpcService;
		this.eventBus = eventBus;
		this.resources = resources;
		this.resources.style().ensureInjected();
		uiBinder = GWT.create(StickyNoteWidgetUiBinder.class);
		bind();
	}
	
	@Override
	public void execute(HasWidgets containers) {
		this.container = containers;
		
		//History management
		if ("".equals(History.getToken())) {
			History.newItem("fList");
		} 
		else if ("fList".equals(History.getToken())){
			History.newItem("list");
		}
		else {
			History.fireCurrentHistoryState();
		}
		setupRemoteAppConfig();
	}
	
	private void initializeTimer(int refreshInterval) {
		Timer refreshTimer = new Timer() {
			@Override
			public void run() {
				if ("".equals(History.getToken())){
					History.newItem("fList");
				}
				else {
					kanbanBoardPresenter.getRemoteLoadKanbanBoardView(kanbanBoardPresenter.getRequestUrlId());
					History.newItem("list");
				}
			}
		};
		refreshTimer.scheduleRepeating(refreshInterval);
	}
	
	public void onValueChange(ValueChangeEvent<String> event) {
	    String token = event.getValue();
	    
	    if (token != null) {
	      
	    	if (token.equals("list") || token.equals("fList")) {
	    		GWT.runAsync(new RunAsyncCallback() {
	        	@Override
	        	public void onFailure(Throwable caught) {
	        		kanbanBoardPresenter.onError(caught.getMessage());
	        	}
	        	@Override
	        	public void onSuccess() {
	        		// lazily initialize our views, and keep them around to be reused
	        		if (kanbanBoardView == null) {
	        			kanbanBoardView = new KanbanBoardViewImpl<Board>(resources, uiBinder);
	              
	        		}
	        		kanbanBoardPresenter.execute(container);
	        	}
	    	});
	      }
	      else if (token.equals("add") || token.equals("edit")) {
	    	  //TODO: Not yest implemented
	      }
	    }
	} 
	
	private void bind() {
		History.addValueChangeHandler(this);
		//Bind all other handlers here, if any...
	}
	
	public void setupRemoteAppConfig() {
		// Setup timer to refresh list automatically.
		service.getConfig(new AsyncCallback<ConfigOptions>() {
			
			@Override
			public void onSuccess(ConfigOptions result) {
				kanbanBoardPresenter.execute(container);
				initializeTimer(result.getRefreshInverval());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				kanbanBoardPresenter.onError(caught.getMessage());
			}
		});
	}
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	public KanbanBoardViewImpl<Board> getKanbanBoardView() {
		return kanbanBoardView;
	}
	
	public KanbanBoardServiceAsync getService() {
		return service;
	}

/*	Would we have chosen to not use the generic KannabBoardView, the onValueChange method would
 *  look something like this. 
 *  
 *  Note: Observe that in the below code, the presenter keeps track of the views instead of the
 *  generic way where each view keeps track of it's presenter. In other words, the controller 
 *  instantiates the view in the code above and the presenter in the code below.
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
