/**
 * 
 */
package com.stw.kanban.resources;

import com.google.gwt.resources.client.CssResource;

/**
 * @author dolczak
 * 
 */
public interface KanbanBoard extends CssResource {

	String summary();

	@ClassName("gwt-DialogBox")
	String gwtDialogBox();

	String stickyNoteWidth();

	String columnHeader();

	String sendButton();

	String serverResponseLabelError();

	String taped();

	String stickyNote();

	String dialogVPanel();

	String stickyNoteHeader();

	String columnEven();

	String columnOdd();

	String stickyNoteKey();

}
