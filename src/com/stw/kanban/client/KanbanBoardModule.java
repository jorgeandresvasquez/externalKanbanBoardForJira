package com.stw.kanban.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.stw.kanban.client.widget.gin.KanbanBoardGinInjector;

public class KanbanBoardModule implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Creation of the Gin injector
		KanbanBoardGinInjector ginInjector = GWT.create(KanbanBoardGinInjector.class);
		
		// We only need to inject via the gin injector on the top level view. All injections lower 
		// in the view hierarchy is injected automatically using bindings in the KanbanBoardInjectorModule. 
		RootPanel.get().add(ginInjector.getKanbanBoardView().asWidget());	
		KanbanBoardController controller = ginInjector.getKanbanBoardController();
		
		controller.execute(RootPanel.get());
	}
	
}
