package com.stw.kanban.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;


public interface KanbanBoardResources extends ClientBundle {

	public static final KanbanBoardResources INSTANCE = GWT.create(KanbanBoardResources.class);
	
	//The default global Css resources.
	@Source("KanbanBoard.css")
	KanbanboardCssResources style();
	
	//The default global Image resources
	@Source("whiteBlock.png")
	ImageResource emptyImageWhite();
	
	@Source("ajaxLoader.gif")
	ImageResource loadingImage();
	
	public interface KanbanboardCssResources extends CssResource {
		//Sticky Note Css resources.
		String stickyNote();
		
		String stickyNoteHeader();
		
		String stickyNoteKey();
		
		String borderColumnHeader();
		
		String borderColumnOdd();
		
		String borderColumnEven();
	}
}
