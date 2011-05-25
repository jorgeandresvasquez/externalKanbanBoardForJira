package com.stw.kanban.client.view;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.stw.kanban.client.AbstractKanbanBoardGwtTestCase;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;
import com.stw.kanban.client.widget.view.BoardColumnWidget;
import com.stw.kanban.client.widget.view.StickyNoteWidget;
import com.stw.kanban.client.widget.view.StickyNoteWidget.StickyNoteWidgetUiBinder;
import com.stw.kanban.resources.KanbanBoardResources;

/*
 * Some error issues when running a GWTTestCase:
 * 
 * Issue 1.		I get the exception: com.google.gwt.core.client.JavaScriptException: (null): null 
 * Solution. 	Make sure you are compiling for the right browser! 
 *     			Check the Kanbanboard.gwt.xml file's <set-property name="user.agent" value="<your browser>"/> 
 * 
 * 
 * */
public class BoardColumnWidgetGwtTest extends AbstractKanbanBoardGwtTestCase {

	private KanbanBoardResources resources;
	private StickyNoteIssue issue1;
	private StickyNoteIssue issue2;
	private StickyNoteWidgetOptions options;
	private BoardColumnWidget boardColumnWidget;
	private BoardColumn column;
	private StickyNoteWidgetUiBinder uiBinder;

	
	@Override
	protected void gwtSetUp() {
		resources = GWT.create(KanbanBoardResources.class);
		uiBinder =  GWT.create(StickyNoteWidgetUiBinder.class);
		options = new StickyNoteWidgetOptions();
	}
	
	@Override
	protected void gwtTearDown() {
		
	}

	@Test
	public void testBoardColumnWidget_WithIssuesTest() {
		Assert.assertNotNull(resources);
		Assert.assertNotNull(uiBinder);
		Assert.assertNotNull(options);
		
		boardColumnWidget = new BoardColumnWidget("stickyNote", resources, uiBinder);
		Assert.assertNotNull(boardColumnWidget);
		
		issue1 = createJiraStickyNoteIssue();
		issue2 = createJiraStickyNoteIssue();
		issue2.setKey("Issue-2");
		issue2.setSummary("This is the issue 2 summary.");
		
		column = new BoardColumn();
		column.setName("Development");
		column.addIssue(issue1);
		column.addIssue(issue2);
		
		boardColumnWidget.setData(column);
		
		Assert.assertEquals("Development", boardColumnWidget.getColumnName().getText());
		ArrayList<Widget> stickyNoteList = boardColumnWidget.getStickyNotes();
		Assert.assertEquals(2, stickyNoteList.size());
		Widget widget1 = stickyNoteList.get(0);
		Assert.assertEquals(StickyNoteWidget.class, widget1.getClass());
	}
	
	@Test
	public void testBoardColumnWidget_WithoutIssuesTest() {
		Assert.assertNotNull(resources);
		Assert.assertNotNull(uiBinder);
		Assert.assertNotNull(options);
		
		boardColumnWidget = new BoardColumnWidget("stickyNote", resources, uiBinder);
		Assert.assertNotNull(boardColumnWidget);
		
		column = new BoardColumn();
		column.setName("Development");
		
		boardColumnWidget.setData(column);
		
		Assert.assertEquals("Development", boardColumnWidget.getColumnName().getText());
		ArrayList<Widget> stickyNoteList = boardColumnWidget.getStickyNotes();
		Assert.assertEquals(0, stickyNoteList.size());
	}
	
	@Test
	public void testBoardColumnWidget_WithNullIssueTest() {
		Assert.assertNotNull(resources);
		Assert.assertNotNull(uiBinder);
		Assert.assertNotNull(options);
		
		boardColumnWidget = new BoardColumnWidget("stickyNote", resources, uiBinder);
		Assert.assertNotNull(boardColumnWidget);
		StickyNoteIssue issue = null;
		
		column = new BoardColumn();
		column.addIssue(issue);
		column.setName("Development");
		Assert.assertNotNull(column);
		try {
			boardColumnWidget.setData(column);
		}
		catch (IllegalArgumentException expectedException) {
			if (expectedException.getMessage().equals("The application tried to set a sticky note without any data!")) {
				//expected exception and the correct message!
			}
			else {
				fail("The correct exception but wrong message! Verify the message!");
			}
		}
		catch (Exception notExpectedException) {
			fail("An IllegalArgumentException was expected!");
		}
		ArrayList<Widget> stickyNoteList = boardColumnWidget.getStickyNotes();
		Assert.assertEquals(0, stickyNoteList.size());
	}
	
	@Test
	public void testBoardColumnWidget_WithNullColumnTest() {
		Assert.assertNotNull(resources);
		Assert.assertNotNull(uiBinder);
		Assert.assertNotNull(options);
		
		boardColumnWidget = new BoardColumnWidget("stickyNote", resources, uiBinder);
		Assert.assertNotNull(boardColumnWidget);
		
		column = null;
		// We are expecting an exception in this test. The following code substitutes: 
		// org.junit.Rule and org.junit.rules.ExpectedException since they are not available 
		// for the GWTTestCase.
		try {
			boardColumnWidget.setData(column);
		} 
		catch (IllegalArgumentException expectedException) {
			if (expectedException.getMessage().equals("The application tried to set a column without any data!")) {
				//expected exception and the correct message!
			}
			else {
				fail("The correct exception but wrong message! Verify the message!");
			}
		}
		catch (Exception notExpectedException) {
			fail("An IllegalArgumentException was expected!");
		}
	}
}
