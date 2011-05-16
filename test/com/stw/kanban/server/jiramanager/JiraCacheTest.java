package com.stw.kanban.server.jiramanager;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.atlassian.jira.rpc.soap.beans.RemoteIssueType;
import com.atlassian.jira.rpc.soap.beans.RemotePriority;
import com.atlassian.jira.rpc.soap.beans.RemoteProject;
import com.atlassian.jira.rpc.soap.beans.RemoteStatus;
import com.atlassian.jira.rpc.soap.beans.RemoteUser;
import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.IssuePriority;
import com.stw.kanban.client.entities.IssueStatus;
import com.stw.kanban.client.entities.IssueType;
import com.stw.kanban.client.entities.Project;
import com.stw.kanban.server.AppConfig;


public class JiraCacheTest {

	private JiraCache jiraCache;
	private AppConfig appConfig;
	private JiraSoapService soapServiceMock;
	private RemotePriority remotePriorityHigh;
	private RemotePriority remotePriorityMedium;
	private RemotePriority[] remotePriorityArr;
	private RemoteStatus remoteStatusOpen;
	private RemoteStatus[] remoteStatusArr;
	private RemoteIssueType remoteIssueTypeBug;
	private RemoteIssueType[] remoteIssueTypeArr;
	private RemoteProject remoteProject1;
	private RemoteUser remoteUserJorge;
	
	//Constants
	private static final String HIGH = "High";
	private static final String MEDIUM = "Medium";
	private static final String OPEN = "Open";
	private static final String PROJECT_1 = "PROJECT_1";
	private static final String BUG = "Bug";
	private static final String JORGE = "Jorge";
	private static final String KANBAN_BOARD_ID = "kanbanId";
	private KanbanConfig kanbanConfigTest;
	private static final String AUTHENTICATION_TOKEN = "authenticationToken";
	private static final String UNKNOWN = "UNKNOWN";
	
	
	@Before
	public void setup() {
		//Create mock objects
		appConfig = new AppConfig("username", "password", "aUrl", true, 30000);
		soapServiceMock = createMock(JiraSoapService.class);
		jiraCache = new JiraCache(soapServiceMock, appConfig);
		reset(soapServiceMock);
		
		remotePriorityHigh = new RemotePriority();
		remotePriorityHigh.setId(HIGH);
		remotePriorityHigh.setName("HighName");
		remotePriorityHigh.setDescription("HighDescription");
		remotePriorityMedium = new RemotePriority();
		remotePriorityMedium.setId(MEDIUM);
		remotePriorityMedium.setName("MediumName");
		remotePriorityMedium.setDescription("MediumDescription");
		remotePriorityArr = new RemotePriority[]{remotePriorityHigh, remotePriorityMedium};
		
		remoteStatusOpen = new RemoteStatus();
		remoteStatusOpen.setId(OPEN);
		remoteStatusOpen.setName("openName");
		remoteStatusOpen.setDescription("openDescription");
		remoteStatusArr = new RemoteStatus[]{remoteStatusOpen};
		
		remoteProject1 = new RemoteProject();
		remoteProject1.setId(PROJECT_1);
		remoteProject1.setName("project1Name");
		
		remoteIssueTypeBug = new RemoteIssueType();
		remoteIssueTypeBug.setId(BUG);
		remoteIssueTypeArr = new RemoteIssueType[]{remoteIssueTypeBug};
		
		remoteUserJorge = new RemoteUser();
		remoteUserJorge.setName(JORGE);
	}
	
	@Test
	public void testGetIssuePriorityByValidPriorityId() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(remotePriorityArr);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		replay(soapServiceMock);
		IssuePriority issuePriorityHigh = jiraCache.getIssuePriorityById(HIGH, KANBAN_BOARD_ID);
		//We call it again to make sure it doesn't execute the getKanbanConfig, login, getPriorities, getStatuses, and getIssueTypes methods again
		IssuePriority issuePriorityMedium = jiraCache.getIssuePriorityById(MEDIUM, KANBAN_BOARD_ID);
		assertNotNull(issuePriorityHigh);
		assertNotNull(issuePriorityMedium);
		assertEquals(remotePriorityHigh.getId(), issuePriorityHigh.getId());
		assertEquals(remotePriorityMedium.getId(), issuePriorityMedium.getId());
		verify(soapServiceMock);
	}
	
	@Test
	public void testGetIssuePriorityByInvalidPriorityId() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(remotePriorityArr);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		replay(soapServiceMock);
		IssuePriority issuePriority = jiraCache.getIssuePriorityById(UNKNOWN, KANBAN_BOARD_ID);
		assertNull(issuePriority);
		verify(soapServiceMock);
	}
	
	@Test
	public void testGetIssueStatusByValidStatusId() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(remoteStatusArr);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		replay(soapServiceMock);
		IssueStatus issueStatusOpen = jiraCache.getIssueStatusById(OPEN, KANBAN_BOARD_ID);
		assertNotNull(issueStatusOpen);
		assertEquals(remoteStatusOpen.getId(), issueStatusOpen.getId());
		verify(soapServiceMock);
	}
	
	@Test
	public void testGetIssueStatusByInvalidStatusId() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(remoteStatusArr);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		replay(soapServiceMock);
		IssueStatus issueStatusUnknown = jiraCache.getIssueStatusById(UNKNOWN, KANBAN_BOARD_ID);
		assertNull(issueStatusUnknown);
		verify(soapServiceMock);
	}
	
	@Test
	public void testGetIssueTypeByValidIssueTypeId() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(remoteIssueTypeArr);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		replay(soapServiceMock);
		IssueType issueTypeBug = jiraCache.getIssueTypeById(BUG, KANBAN_BOARD_ID);
		assertNotNull(issueTypeBug);
		assertEquals(remoteIssueTypeBug.getId(), issueTypeBug.getId());
		verify(soapServiceMock);
	}
	
	@Test
	public void testGetIssueTypeByInvalidIssueTypeId() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(remoteIssueTypeArr);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		replay(soapServiceMock);
		IssueType issueTypeUnknown = jiraCache.getIssueTypeById(UNKNOWN, KANBAN_BOARD_ID);
		assertNull(issueTypeUnknown);
		verify(soapServiceMock);
	}
	
	@Test
	public void testGetProjectByValidKey() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expectLastCall().times(2);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getProjectByKey(AUTHENTICATION_TOKEN, PROJECT_1)).andReturn(remoteProject1);
		replay(soapServiceMock);
		Project project1 = jiraCache.getProjectByKey(PROJECT_1, KANBAN_BOARD_ID);
		assertNotNull(project1);
		assertEquals(remoteProject1.getKey(), project1.getId());
		verify(soapServiceMock);
	}
	
	@Test
	public void testGetProjectByInvalidKey() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expectLastCall().times(2);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getProjectByKey(AUTHENTICATION_TOKEN, UNKNOWN)).andReturn(null);
		replay(soapServiceMock);
		Project unknownProject = jiraCache.getProjectByKey(UNKNOWN, KANBAN_BOARD_ID);
		assertNull(unknownProject);
		verify(soapServiceMock);
	}
	
	@Test
	public void testAssigneeByValidUserName() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expectLastCall().times(2);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getUser(AUTHENTICATION_TOKEN, JORGE)).andReturn(remoteUserJorge);
		replay(soapServiceMock);
		Assignee assigneeJorge = jiraCache.getAssigneeByUserName(JORGE, KANBAN_BOARD_ID);
		assertNotNull(assigneeJorge);
		assertEquals(remoteUserJorge.getName(), assigneeJorge.getName());
		verify(soapServiceMock);
	}
	
	@Test
	public void testAssigneeByInValidUserName() throws Exception {
		expect(soapServiceMock.login(appConfig.getUserName(), appConfig.getPassword())).andReturn(AUTHENTICATION_TOKEN);
		expectLastCall().times(2);
		expect(soapServiceMock.getPriorities(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getIssueTypes(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getStatuses(AUTHENTICATION_TOKEN)).andReturn(null);
		expect(soapServiceMock.getUser(AUTHENTICATION_TOKEN, UNKNOWN)).andReturn(null);
		replay(soapServiceMock);
		Assignee unknownAssignee = jiraCache.getAssigneeByUserName(UNKNOWN, KANBAN_BOARD_ID);
		assertNull(unknownAssignee);
		verify(soapServiceMock);
	}		
	
}
