package com.stw.kanban.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.stw.kanban.resources.KanbanBoardResources;

public class StickyNoteWidgetOptions {
	KanbanBoardResources defaultResources = GWT.create(KanbanBoardResources.class);
	
	private String styleName;
	
	/**
	 * Creates the default sticky note options using the global Kanban board resources.
	 * */
	public StickyNoteWidgetOptions() {
		this.styleName = defaultResources.style().stickyNote();
	}
	
	/**
	 * Creates the  sticky note options based on the given Kanban board resources.
	 * */
	@Inject
	public StickyNoteWidgetOptions(KanbanBoardResources style) {
		this.styleName = style.style().stickyNote();
	}

	/**
	 * Returns the sticky note style name.
	 * */
	public String getStyleName() {
		return styleName;
	}
	
	/**
	 * Sets the sticky note style name with the given name.
	 * */
	public void setStyleName(String style) {
		this.styleName = style;
	}
	
}
