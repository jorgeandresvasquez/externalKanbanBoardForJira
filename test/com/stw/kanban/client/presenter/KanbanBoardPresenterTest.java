package com.stw.kanban.client.presenter;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.reset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.stw.kanban.client.KanbanBoardServiceAsync;
import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.IssuePriority;
import com.stw.kanban.client.entities.IssueStatus;
import com.stw.kanban.client.entities.IssueType;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.client.entities.Project;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.presenter.KanbanBoardPresenter;
import com.stw.kanban.client.widget.view.KanbanBoardView;


public class KanbanBoardPresenterTest {

	private KanbanBoardServiceAsync mockService;
	private EventBus mockEventBus;
	private KanbanBoardView<Board> mockView;
	private Board mockBoard;
	private KanbanBoardPresenter kanbanBoardPresenter;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		mockService = createStrictMock(KanbanBoardServiceAsync.class);
		mockEventBus = new SimpleEventBus();
		mockView = createStrictMock(KanbanBoardView.class);
		mockBoard = createMockBoardWithJiraIssues();
		kanbanBoardPresenter = new KanbanBoardPresenter("id", mockService, mockEventBus, mockView);
		reset(mockService);
		reset(mockView);
	}
	
	@Test
	public void loadViewWithCompleteBoard() {
		kanbanBoardPresenter.loadView(mockBoard);
		Assert.assertNotNull(kanbanBoardPresenter.getKanbanBoardView());
		Assert.assertEquals("id", kanbanBoardPresenter.getRequestUrlId());
	}
	
	@Test
	public void loadViewWithNullBoard() {
		kanbanBoardPresenter.loadView(null);
		Assert.assertNotNull(kanbanBoardPresenter.getKanbanBoardView());
		Assert.assertEquals("id", kanbanBoardPresenter.getRequestUrlId());
	}
	
	private Board createMockBoardWithJiraIssues() {
		Board board = new Board();
		board.setDescription("This is a Kanban board!");
		
		BoardColumn columnReady = new BoardColumn();
		columnReady.setName("Ready");
		columnReady.addIssue(createIssueMock());
		
		StickyNoteIssue issue2 = createIssueMock();
		issue2.setKey("Issue-2");
		issue2.setSummary("Issue 2 summary.");
		columnReady.addIssue(issue2);
		
		StickyNoteIssue issue3 = createIssueMock();
		issue3.setKey("Issue-3");
		issue3.setSummary("Issue 3 summary.");
		columnReady.addIssue(issue3);
		
		board.addColumn(columnReady);
		
		BoardColumn columnDevelopment = new BoardColumn();
		columnDevelopment.setName("In Development");

		StickyNoteIssue issue4 = createIssueMock();
		issue4.setKey("Issue-4");
		issue4.setSummary("Issue 4 summary.");
		columnReady.addIssue(issue4);
		
		StickyNoteIssue issue5 = createIssueMock();
		issue5.setKey("Issue-5");
		issue5.setSummary("Issue 5 summary.");
		columnDevelopment.addIssue(issue5);
		board.addColumn(columnDevelopment);
		
		BoardColumn columnReview = new BoardColumn();
		columnReview.setName("Code Review");
		
		StickyNoteIssue issue6 = createIssueMock();
		issue6.setKey("Issue-6");
		issue6.setSummary("Issue 6 summary.");
		columnReview.addIssue(issue6);
		
		StickyNoteIssue issue7 = createIssueMock();
		issue7.setKey("Issue-7");
		issue7.setSummary("Issue 7 summary.");
		columnReview.addIssue(issue7);
		
		StickyNoteIssue issue8 = createIssueMock();
		issue8.setKey("Issue-8");
		issue8.setSummary("Issue 8 summary.");
		columnReview.addIssue(issue8);
		
		StickyNoteIssue issue9 = createIssueMock();
		issue9.setKey("Issue-9");
		issue9.setSummary("Issue 9 summary.");
		columnReview.addIssue(issue9);
		
		board.addColumn(columnReview);
		return board;
	}
	
	public StickyNoteIssue createIssueMock() {

		Assignee assignee1 = new Assignee();
		assignee1.setEmail("hulk@hotmail.com");
		assignee1.setFullName("John Kahn");
		assignee1.setName("hulk");
	
		IssuePriority issuePriority1 = new IssuePriority();
		issuePriority1.setDescription("High Priority");
		issuePriority1.setId("P!");
		issuePriority1.setName("Priority Issue");
	
		IssueStatus issueStatus1 = new IssueStatus();
		issueStatus1.setDescription("Not done.");
		issueStatus1.setIconUrl("");
		issueStatus1.setId("ND");
		issueStatus1.setName("Not Done");
	
		IssueType issueType1 = new IssueType();
		issueType1.setDescription("Issue type 1.");
		issueType1.setIconUrl("");
		issueType1.setId("T1");
		issueType1.setName("Issue type 1");
		
		Project project1 = new Project();
		project1.setDescription("Project 1.");
		project1.setId("Project-1");
		project1.setName("Project 1");
	
		BoardColumn columnReady = new BoardColumn();
		columnReady.setName("Ready for Dev");
	
		StickyNoteIssue issue = new JiraIssue();
		issue.setAssignee(assignee1);
		issue.setIssuePriority(issuePriority1);
		issue.setIssueStatus(issueStatus1);
		issue.setIssueType(issueType1);
		issue.setKey("Issue-1");
		issue.setProject(project1);
		issue.setSummary("Issue 1 summary.");
		
		return issue;
	}
	
}
