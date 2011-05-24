package com.stw.kanban.client.view;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.IssuePriority;
import com.stw.kanban.client.entities.IssueStatus;
import com.stw.kanban.client.entities.IssueType;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.client.entities.Project;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;
import com.stw.kanban.client.widget.view.StickyNoteWidget;
import com.stw.kanban.client.widget.view.StickyNoteWidget.StickyNoteWidgetUiBinder;
import com.stw.kanban.resources.KanbanBoardResources;

/*
 * Some error issues when running a GWTTestCase:
 * 
 * Q1. I get a: com.google.gwt.core.client.JavaScriptException: (null): null 
 * 
 * A1. Make sure you are compiling for the right browser! Change the 
 * 
 * 
 * */
public class StickyNoteWidgetGwtTest extends GWTTestCase {

	private KanbanBoardResources resources;
	private StickyNoteWidgetUiBinder binder;
	private StickyNoteWidget stickyNote;
	private StickyNoteIssue issue;
	private StickyNoteWidgetOptions options;
	
	@Override
	public String getModuleName() {
		return "com.stw.kanban.KanbanBoard";
	}
	
	@Override
	protected void gwtSetUp() {
		issue = createJiraStickyNoteIssue();
		resources = GWT.create(KanbanBoardResources.class
				);
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
		Assert.assertEquals("This is the issue summary.", stickyNote.getSummaryText().getText());
		Assert.assertEquals("Issue-123(Kanban Board)", stickyNote.getStickyNoteKey());
		Assert.assertEquals("John Green", stickyNote.getAssignee().getText());
		stickyNote.getTitle();
		Assert.assertEquals(resources.style().stickyNote(),stickyNote.getStyleName());
		Assert.assertTrue(stickyNote.isVisible());
		
		Assert.assertEquals("Issue-123(Kanban Board)", stickyNote.getTitelName().getText());
		Assert.assertEquals("http://typeImage.jpg", stickyNote.getTypeImageUrl());
		Assert.assertEquals("http://priorityImage.jpg", stickyNote.getPriorityImageUrl());
	}
	
	private StickyNoteIssue createJiraStickyNoteIssue() {
		StickyNoteIssue issue = new JiraIssue();
		
		Assignee assignee = new Assignee();
		assignee.setEmail("assignee@hotmail.com");
		assignee.setFullName("John Green");
		assignee.setName("jGreen");
		issue.setAssignee(assignee);
		
		IssuePriority iPriority = new IssuePriority();
		iPriority.setDescription("This is the issue priority description.");
		iPriority.setIconUrl("http://priorityImage.jpg");
		iPriority.setId("Priority-1");
		iPriority.setName("Priority 1");
		issue.setIssuePriority(iPriority);
		
		IssueStatus iStatus = new IssueStatus();
		iStatus.setDescription("This is the issue status description.");
		iStatus.setIconUrl("http://statusImage.jpg");
		iStatus.setId("Status-1");
		iStatus.setName("Status 1");
		issue.setIssueStatus(iStatus);
		
		IssueType iType = new IssueType();
		iType.setDescription("This is the issue type description");
		iType.setIconUrl("http://typeImage.jpg");
		iType.setId("Type-1");
		iType.setName("Type 1");
		issue.setIssueType(iType);
		
		issue.setKey("Issue-123");
		
		Project project = new Project();
		project.setDescription("A project description.");
		project.setId("Project-1");
		project.setName("Kanban Board");
		issue.setProject(project);
	
		issue.setSummary("This is the issue summary.");
		
		return issue;
	}

}
