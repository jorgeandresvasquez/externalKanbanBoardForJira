package com.stw.kanban.client;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.stw.kanban.client.view.BoardColumnWidgetGwtTest;
import com.stw.kanban.client.view.StickyNoteWidgetGwtTest;

public class KanbanBoardViewsGWTTestSuite extends GWTTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for the Kanban board views");
	    suite.addTestSuite(BoardColumnWidgetGwtTest.class);
	    suite.addTestSuite(StickyNoteWidgetGwtTest.class);
	    
		return suite;
	}
}
