package com.stw.kanban.client.view;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;
import com.stw.kanban.client.widget.view.StickyNoteWidget;
import com.stw.kanban.client.widget.view.StickyNoteWidget.StickyNoteWidgetUiBinder;
import com.stw.kanban.resources.KanbanBoardResources;


public class StickyNoteWidgetGwtTest extends AbstractKanbanBoardGwtTestCase {

	private KanbanBoardResources resources;
	private StickyNoteWidgetUiBinder binder;
	private StickyNoteWidget stickyNote;
	private StickyNoteIssue issue;
	private StickyNoteWidgetOptions options;
	
	@Override
	protected void gwtSetUp() {
		issue = createJiraStickyNoteIssue();
		resources = GWT.create(KanbanBoardResources.class);
		binder = GWT.create(StickyNoteWidgetUiBinder.class);
		options = new StickyNoteWidgetOptions();
	}
	
	@Override
	protected void gwtTearDown() {
		
	}

	@Test
	public void testStickyNoteWidget_WithDataTest() {
		Assert.assertNotNull(resources);
		Assert.assertNotNull(binder);
		Assert.assertNotNull(options);
		stickyNote = new StickyNoteWidget(resources, options, binder);
		Assert.assertNotNull(stickyNote);
		stickyNote.setData(issue);
		Assert.assertEquals("This is the issue 1 summary.", stickyNote.getSummaryText().getText());
		Assert.assertEquals("Issue-1(Kanban Board)", stickyNote.getStickyNoteKey());
		Assert.assertEquals("John Green", stickyNote.getAssignee().getText());
		Assert.assertEquals("", stickyNote.getTitle());
		Assert.assertEquals(resources.style().stickyNote(),stickyNote.getStyleName());
		Assert.assertTrue(stickyNote.isVisible());
		
		Assert.assertEquals("Issue-1(Kanban Board)", stickyNote.getTitelName().getText());
		Assert.assertEquals("http://typeImage.jpg", stickyNote.getTypeImageUrl());
		Assert.assertEquals("http://priorityImage.jpg", stickyNote.getPriorityImageUrl());
	}
	
	@Test
	public void testStickyNoteWidget_WithNullDataTest() {
		Assert.assertNotNull(resources);
		Assert.assertNotNull(binder);
		Assert.assertNotNull(options);
		stickyNote = new StickyNoteWidget(resources, options, binder);
		Assert.assertNotNull(stickyNote);
		// We are expecting an exception in this test. The following code substitutes: 
		// org.junit.Rule and org.junit.rules.ExpectedException since they are not available 
		// for the GWTTestCase.
		try {
			stickyNote.setData(null);
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
	}

}
