package com.stw.kanban.client.view;

import com.google.gwt.junit.client.GWTTestCase;
import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.IssuePriority;
import com.stw.kanban.client.entities.IssueStatus;
import com.stw.kanban.client.entities.IssueType;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.client.entities.Project;
import com.stw.kanban.client.entities.StickyNoteIssue;


/*
 * Some error issues when running a GWTTestCase:
 * 
 * Issue 1.		I get the exception: com.google.gwt.core.client.JavaScriptException: (null): null 
 * Solution. 	Make sure you are compiling for the right browser! 
 *     			Check the Kanbanboard.gwt.xml file's <set-property name="user.agent" value="<your browser>"/> 
 * 
 * 
 * */
public class AbstractKanbanBoardGwtTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.stw.kanban.KanbanBoard";
	}

	public StickyNoteIssue createJiraStickyNoteIssue() {
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
		
		issue.setKey("Issue-1");
		
		Project project = new Project();
		project.setDescription("A project description.");
		project.setId("Project-1");
		project.setName("Kanban Board");
		issue.setProject(project);
	
		issue.setSummary("This is the issue 1 summary.");
		
		return issue;
	}

}
