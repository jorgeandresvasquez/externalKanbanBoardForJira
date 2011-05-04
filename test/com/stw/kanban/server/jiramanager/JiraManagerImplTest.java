package com.stw.kanban.server.jiramanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.atlassian.jira.rpc.soap.beans.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.IssuePriority;
import com.stw.kanban.client.entities.IssueStatus;
import com.stw.kanban.client.entities.IssueType;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.client.entities.Project;
import com.stw.kanban.server.AppConfig;


public class JiraManagerImplTest {

	private JiraManager jiraManager;
	private KanbanConfigDao kanbanConfigDaoMock;
	private JiraCache jiraCacheMock;
	private JiraSoapService soapServiceMock;
	private AppConfig appConfig;
	private KanbanConfig kanbanConfigTest;
	private Map<String, RemoteIssue[]> remoteIssuesByQueryMap;
	private Map<String, IssueType> issueTypeMap;
	private Map<String, IssueStatus> issueStatusMap;
	private Map<String, IssuePriority> issuePriorityMap;
	private Map<String, Assignee> assigneeMap;
	private Map<String, Project> projectMap;
	
	//Constants
	private static final String HIGH = "High";
	private static final String MEDIUM = "Medium";
	private static final String PROJECT_1 = "PROJECT_1";
	private static final String PROJECT_2 = "PROJECT_2";
	private static final String BUG = "Bug";
	private static final String FEATURE = "Feature";
	private static final String OPEN = "Open";
	private static final String RE_OPENED = "Reopened";
	private static final String IN_PROGRESS = "In Progress";
	private static final String MIKE = "Mike";
	private static final String PEDRO = "Pedro";
	private static final String TOM = "Tom";
	
	private static final String KANBAN_ID = "sampleConfigId";
	
	private static final String KANBAN_CONFIG_DESCRIPTION = "An example kanbanConfig";
	
	@Before
	public void setup() {
		//Create mock objects
		appConfig = new AppConfig("username", "password", "aUrl", true);
		kanbanConfigDaoMock = createMock(KanbanConfigDao.class);
		jiraCacheMock = createMock(JiraCache.class);
		soapServiceMock = createMock(JiraSoapService.class);
		jiraManager = new JiraManagerImpl(kanbanConfigDaoMock, jiraCacheMock, soapServiceMock, appConfig);
		reset(kanbanConfigDaoMock);
		reset(jiraCacheMock);
		reset(soapServiceMock);
		
		List<StepConfig> stepConfigList = new ArrayList<StepConfig>();
		StepConfig stepConfig1 = new StepConfig();
		stepConfig1.setName("Not Started");
		stepConfig1.setQuery("project IN (PROJECT_1, PROJECT_2) AND status IN (Open, Reopened)");
		stepConfigList.add(stepConfig1);
		//Mock remoteIssues associated to the previous query
		remoteIssuesByQueryMap = new HashMap<String, RemoteIssue[]>();
		RemoteIssue remoteIssue1_1 = new RemoteIssue();
		remoteIssue1_1.setAssignee(PEDRO);
		remoteIssue1_1.setPriority(HIGH);
		remoteIssue1_1.setStatus(RE_OPENED);
		remoteIssue1_1.setType(BUG);
		remoteIssue1_1.setKey("533");
		remoteIssue1_1.setProject(PROJECT_1);
		remoteIssue1_1.setSummary("authentication module not working");
		RemoteIssue remoteIssue1_2 = new RemoteIssue();
		remoteIssue1_2.setAssignee(MIKE);
		remoteIssue1_2.setPriority(MEDIUM);
		remoteIssue1_2.setStatus(OPEN);
		remoteIssue1_2.setType(FEATURE);
		remoteIssue1_2.setKey("450");
		remoteIssue1_2.setProject(PROJECT_2);
		remoteIssue1_2.setSummary("Integrate with Twitter");
		remoteIssuesByQueryMap.put(stepConfig1.getQuery(), new RemoteIssue[]{remoteIssue1_1, remoteIssue1_2});
		
		StepConfig stepConfig2 = new StepConfig();
		stepConfig2.setName("Development");
		stepConfig2.setQuery("project IN (PROJECT_1, PROJECT_2) AND status IN (\"In Development\", \"In Progress\")");
		stepConfigList.add(stepConfig2);
		
		RemoteIssue remoteIssue2_1 = new RemoteIssue();
		remoteIssue2_1.setAssignee(TOM);
		remoteIssue2_1.setPriority(HIGH);
		remoteIssue2_1.setStatus(IN_PROGRESS);
		remoteIssue2_1.setType(BUG);
		remoteIssue2_1.setKey("201");
		remoteIssue2_1.setProject(PROJECT_2);
		remoteIssue2_1.setSummary("Unable to connect to database");
		remoteIssuesByQueryMap.put(stepConfig2.getQuery(), new RemoteIssue[]{remoteIssue2_1});
		
		//Define IssueType, IssueStatus, IssuePriority, Assignee, and Project objects associated to the RemoteIssues
		issueTypeMap = new HashMap<String, IssueType>();
		issueStatusMap = new HashMap<String, IssueStatus>();
		issuePriorityMap = new HashMap<String, IssuePriority>();
		assigneeMap = new HashMap<String, Assignee>();
		projectMap = new HashMap<String, Project>();

		IssueType bugType = new IssueType();
		bugType.setId(BUG);
		bugType.setName("BugName");
		issueTypeMap.put(bugType.getId(), bugType);
		IssueType featureType = new IssueType();
		featureType.setId(FEATURE);
		featureType.setName("FeatureName");
		issueTypeMap.put(featureType.getId(), featureType);
		IssueStatus reopenedStatus = new IssueStatus();
		reopenedStatus.setId(RE_OPENED);
		reopenedStatus.setName("ReopenedName");
		IssueStatus openStatus = new IssueStatus();
		openStatus.setId(OPEN);
		openStatus.setName("OpenName");
		IssueStatus inProgressStatus = new IssueStatus();
		inProgressStatus.setId(IN_PROGRESS);
		inProgressStatus.setName("In Progress Name");	
		issueStatusMap.put(reopenedStatus.getId(), reopenedStatus);
		issueStatusMap.put(openStatus.getId(), openStatus);
		issueStatusMap.put(inProgressStatus.getId(), inProgressStatus);
		IssuePriority highPriority = new IssuePriority();
		highPriority.setId(HIGH);
		highPriority.setName("HighName");
		IssuePriority mediumPriority = new IssuePriority();
		mediumPriority.setId(MEDIUM);
		mediumPriority.setName("MediumName");
		issuePriorityMap.put(highPriority.getId(), highPriority);
		issuePriorityMap.put(mediumPriority.getId(), mediumPriority);
		Assignee mike = new Assignee();
		mike.setName(MIKE);
		mike.setFullName("Mike Jones");
		Assignee pedro = new Assignee();
		pedro.setName(PEDRO);
		pedro.setFullName("Pedro Perez");
		Assignee tom = new Assignee();
		tom.setName(TOM);
		tom.setFullName("Thomas Mulcair");
		assigneeMap.put(mike.getName(), mike);
		assigneeMap.put(pedro.getName(), pedro);
		assigneeMap.put(tom.getName(), tom);
		
		Project project1 = new Project();
		project1.setId(PROJECT_1);
		project1.setName("PROJECT_1_Name");
		projectMap.put(project1.getId(), project1);
		Project project2 = new Project();
		project2.setId(PROJECT_2);
		project2.setName("PROJECT_2_Name");
		projectMap.put(project2.getId(), project2);
		
		StepConfig stepConfig3 = new StepConfig();
		stepConfig3.setName("QA Testing");
		stepConfig3.setQuery("project IN (PROJECT_1, PROJECT_2) AND status IN (\"QA Testing\")");
		stepConfigList.add(stepConfig3);
		//For path where the response is null
		remoteIssuesByQueryMap.put(stepConfig3.getQuery(), null);
		
		StepConfig stepConfig4 = new StepConfig();
		stepConfig4.setName("Ready for Release");
		stepConfig4.setQuery("project IN (PROJECT_1, PROJECT_2) AND status IN (\"Ready for Release\")");
		stepConfigList.add(stepConfig4);
		//For path where the response is an empty array
		remoteIssuesByQueryMap.put(stepConfig4.getQuery(), new RemoteIssue[]{});
		kanbanConfigTest = new KanbanConfig(KANBAN_ID, KANBAN_CONFIG_DESCRIPTION, 10, stepConfigList);
	}
	
	@Test
	public void testGetIssuesByKanbanBoardValidId() throws Exception {
		//Setup mock returns
		String outputToken = "outputToken";
		expect(kanbanConfigDaoMock.getKanbanConfig(KANBAN_ID)).andReturn(kanbanConfigTest);
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(outputToken);
		for (Map.Entry<String, RemoteIssue[]> remoteIssueEntry : remoteIssuesByQueryMap.entrySet()) {
			expect(soapServiceMock.getIssuesFromJqlSearch(outputToken, remoteIssueEntry.getKey(), kanbanConfigTest.getMaxElementsPerStep())).andReturn(remoteIssueEntry.getValue());
		}
		
		expect(jiraCacheMock.getIssueTypeById(BUG, kanbanConfigTest.getId())).andReturn(issueTypeMap.get(BUG));
		expect(jiraCacheMock.getIssueTypeById(BUG, kanbanConfigTest.getId())).andReturn(issueTypeMap.get(BUG));
		expect(jiraCacheMock.getIssueTypeById(FEATURE, kanbanConfigTest.getId())).andReturn(issueTypeMap.get(FEATURE));
		
		expect(jiraCacheMock.getIssueStatusById(RE_OPENED, kanbanConfigTest.getId())).andReturn(issueStatusMap.get(RE_OPENED));
		expect(jiraCacheMock.getIssueStatusById(OPEN, kanbanConfigTest.getId())).andReturn(issueStatusMap.get(OPEN));
		expect(jiraCacheMock.getIssueStatusById(IN_PROGRESS, kanbanConfigTest.getId())).andReturn(issueStatusMap.get(IN_PROGRESS));
		
		expect(jiraCacheMock.getIssuePriorityById(HIGH, kanbanConfigTest.getId())).andReturn(issuePriorityMap.get(HIGH));
		expect(jiraCacheMock.getIssuePriorityById(HIGH, kanbanConfigTest.getId())).andReturn(issuePriorityMap.get(HIGH));
		expect(jiraCacheMock.getIssuePriorityById(MEDIUM, kanbanConfigTest.getId())).andReturn(issuePriorityMap.get(MEDIUM));
		
		expect(jiraCacheMock.getAssigneeByUserName(PEDRO, kanbanConfigTest.getId())).andReturn(assigneeMap.get(PEDRO));
		expect(jiraCacheMock.getAssigneeByUserName(MIKE, kanbanConfigTest.getId())).andReturn(assigneeMap.get(MIKE));
		expect(jiraCacheMock.getAssigneeByUserName(TOM, kanbanConfigTest.getId())).andReturn(assigneeMap.get(TOM));	
		
		expect(jiraCacheMock.getProjectByKey(PROJECT_1, kanbanConfigTest.getId())).andReturn(projectMap.get(PROJECT_1));
		expect(jiraCacheMock.getProjectByKey(PROJECT_2, kanbanConfigTest.getId())).andReturn(projectMap.get(PROJECT_2));
		expect(jiraCacheMock.getProjectByKey(PROJECT_2, kanbanConfigTest.getId())).andReturn(projectMap.get(PROJECT_2));
		
		replay(kanbanConfigDaoMock);
		replay(soapServiceMock);
		replay(jiraCacheMock);
		Board board = jiraManager.getIssuesByKanbanBoardId(KANBAN_ID);
		assertNotNull(board);
		assertEquals(kanbanConfigTest.getDescription(), board.getDescription());
		assertEquals(kanbanConfigTest.getStepConfigList().size(), board.getColumns().size());
		
		//The order of the elements in stepConfigList should de the same as the output in board.getColumns()
		for (int i = 0; i < board.getColumns().size(); i++) {
			StepConfig currentStepConfig = kanbanConfigTest.getStepConfigList().get(i);
			BoardColumn currentBoardColumn = board.getColumns().get(i);
			assertEquals(currentStepConfig.getName(), currentBoardColumn.getName());
		}
		//The first column:  "Not Started" must have returned two JiraIssues
		List<JiraIssue> jiraIssuesFirstColumn = board.getColumns().get(0).getIssues();
		assertEquals(2, jiraIssuesFirstColumn.size());
		
		//Compare the properties of the RemoteIssues returned by the mocks for the first column with those of the output JiraIssues
		JiraIssue jiraIssue1_1 = jiraIssuesFirstColumn.get(0);
		assertEquals(assigneeMap.get(PEDRO), jiraIssue1_1.getAssignee());
		assertEquals(issuePriorityMap.get(HIGH), jiraIssue1_1.getIssuePriority());
		assertEquals(issueStatusMap.get(RE_OPENED), jiraIssue1_1.getIssueStatus());
		assertEquals(issueTypeMap.get(BUG), jiraIssue1_1.getIssueType());
		assertEquals("533", jiraIssue1_1.getKey());
		assertEquals(projectMap.get(PROJECT_1), jiraIssue1_1.getProject());
		assertEquals("authentication module not working", jiraIssue1_1.getSummary());
		
		JiraIssue jiraIssue1_2 = jiraIssuesFirstColumn.get(1);
		assertEquals(assigneeMap.get(MIKE), jiraIssue1_2.getAssignee());
		assertEquals(issuePriorityMap.get(MEDIUM), jiraIssue1_2.getIssuePriority());
		assertEquals(issueStatusMap.get(OPEN), jiraIssue1_2.getIssueStatus());
		assertEquals(issueTypeMap.get(FEATURE), jiraIssue1_2.getIssueType());
		assertEquals("450", jiraIssue1_2.getKey());
		assertEquals(projectMap.get(PROJECT_2), jiraIssue1_2.getProject());
		assertEquals("Integrate with Twitter", jiraIssue1_2.getSummary());
		
		//The second column:  "Development" must have returned two JiraIssues
		List<JiraIssue> jiraIssuesSecondColumn = board.getColumns().get(1).getIssues();
		assertEquals(1, jiraIssuesSecondColumn.size());
		
		//Compare the properties of the RemoteIssues returned by the mocks for the second column with those of the output JiraIssues
		JiraIssue jiraIssue2_1 = jiraIssuesSecondColumn.get(0);
		assertEquals(assigneeMap.get(TOM), jiraIssue2_1.getAssignee());
		assertEquals(issuePriorityMap.get(HIGH), jiraIssue2_1.getIssuePriority());
		assertEquals(issueStatusMap.get(IN_PROGRESS), jiraIssue2_1.getIssueStatus());
		assertEquals(issueTypeMap.get(BUG), jiraIssue2_1.getIssueType());
		assertEquals("201", jiraIssue2_1.getKey());
		assertEquals(projectMap.get(PROJECT_2), jiraIssue2_1.getProject());
		assertEquals("Unable to connect to database", jiraIssue2_1.getSummary());
		
		//The third ("QA Testing") and last columns ("Ready for Release") must have returned 0 JiraIssues each
		List<JiraIssue> jiraIssuesThirdColumn = board.getColumns().get(2).getIssues();
		assertEquals(0, jiraIssuesThirdColumn.size());
		List<JiraIssue> jiraIssuesFourthColumn = board.getColumns().get(3).getIssues();
		assertEquals(0, jiraIssuesFourthColumn.size());
		
		verify(kanbanConfigDaoMock);
		verify(soapServiceMock);
		verify(jiraCacheMock);
	}
	
}
