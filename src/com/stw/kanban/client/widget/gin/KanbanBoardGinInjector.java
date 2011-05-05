package com.stw.kanban.client.widget.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.stw.kanban.client.KanbanBoardController;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.widget.view.KanbanBoardViewImpl;

/* The injector methods provide a bridge between the Guice and non-Guice world.
 * Note that you only need to create injector methods for classes that you would directly 
 * access in the top-level initialization code, such as the UI classes to install in your 
 * RootPanel. You don't need to create injector methods for lower-level classes that will be 
 * automatically injected. */

@GinModules(KanbanBoardInjectorModule.class) //Associating the module with the injector
public interface KanbanBoardGinInjector extends Ginjector {

	public KanbanBoardViewImpl<Board> getKanbanBoardView();
	
	public KanbanBoardController getKanbanBoardController();
	
}
