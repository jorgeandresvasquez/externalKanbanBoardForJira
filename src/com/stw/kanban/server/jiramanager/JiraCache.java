package com.stw.kanban.server.jiramanager;

import java.util.HashMap;
import java.util.Map;
import com.atlassian.jira.rpc.soap.beans.RemoteIssueType;
import com.atlassian.jira.rpc.soap.beans.RemotePriority;
import com.atlassian.jira.rpc.soap.beans.RemoteProject;
import com.atlassian.jira.rpc.soap.beans.RemoteStatus;
import com.atlassian.jira.rpc.soap.beans.RemoteUser;
import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.IssuePriority;
import com.stw.kanban.client.entities.IssueStatus;
import com.stw.kanban.client.entities.IssueType;
import com.stw.kanban.client.entities.Project;
import com.stw.kanban.server.AppConfig;

@Singleton
public class JiraCache {

	private Map<String, IssueType> issueTypeMap;
	private Map<String, IssueStatus> issueStatusMap;
	private Map<String, IssuePriority> issuePriorityMap;
	private Map<String, Assignee> assigneeMap;
	private Map<String, Project> projectMap;

	private JiraSoapService soapService;
	private boolean initializationRequired;
	private AppConfig appConfig;
	
	@Inject
	public JiraCache(JiraSoapService soapService, AppConfig appConfig) {
		this.soapService = soapService;
		initializationRequired = true;
		this.appConfig = appConfig;
	}
	
	private void initializeCachingMaps(String kanbanBoardId) throws Exception {
		String authenticationToken = soapService.login(appConfig.getUserName(), appConfig.getPassword());
		issueStatusMap = new HashMap<String, IssueStatus>();
		RemoteStatus[] remoteStatusArr = soapService.getStatuses(authenticationToken);
		if (remoteStatusArr != null) {
			for (RemoteStatus remoteStatus : remoteStatusArr) {
				IssueStatus issueStatus = new IssueStatus();
				issueStatus.setDescription(remoteStatus.getDescription());
				issueStatus.setId(remoteStatus.getId());
				issueStatus.setName(remoteStatus.getName());
				issueStatus.setIconUrl(remoteStatus.getIcon());
				issueStatusMap.put(issueStatus.getId(), issueStatus);
			}
		}
		issuePriorityMap = new HashMap<String, IssuePriority>();
		RemotePriority[] remotePriorityArr = soapService.getPriorities(authenticationToken);
		if (remotePriorityArr != null) {
			for (RemotePriority remotePriority : remotePriorityArr) {
				IssuePriority issuePriority = new IssuePriority();
				issuePriority.setId(remotePriority.getId());
				issuePriority.setName(remotePriority.getName());
				issuePriority.setDescription(remotePriority.getDescription());
				issuePriority.setIconUrl(remotePriority.getIcon());
				issuePriorityMap.put(issuePriority.getId(), issuePriority);
			}
		}
		issueTypeMap = new HashMap<String, IssueType>();
		RemoteIssueType[] remoteIssueTypeArr = soapService.getIssueTypes(authenticationToken);
		if (remoteIssueTypeArr != null) {
			for (RemoteIssueType remoteIssueType : remoteIssueTypeArr) {
				IssueType issueType = new IssueType();
				issueType.setDescription(remoteIssueType.getDescription());
				issueType.setId(remoteIssueType.getId());
				issueType.setName(remoteIssueType.getName());
				issueType.setIconUrl(remoteIssueType.getIcon());
				issueTypeMap.put(issueType.getId(), issueType);
			}
		}
		assigneeMap = new HashMap<String, Assignee>();
		projectMap = new HashMap<String, Project>();
		initializationRequired = false;
	}
	
	private void verifyInitialization(String kanbanBoardId) throws Exception {
		if (initializationRequired) {
			initializeCachingMaps(kanbanBoardId);
		}
	}
	
	public IssuePriority getIssuePriorityById(String priorityId, String kanbanBoardId) throws Exception {
		verifyInitialization(kanbanBoardId);
		return issuePriorityMap.get(priorityId);
	}
	
	public IssueStatus getIssueStatusById(String statusId, String kanbanBoardId) throws Exception {
		verifyInitialization(kanbanBoardId);
		return issueStatusMap.get(statusId);
	}
	
	public IssueType getIssueTypeById(String issueTypeId, String kanbanBoardId) throws Exception {
		verifyInitialization(kanbanBoardId);
		return issueTypeMap.get(issueTypeId);
	}
	
	/**
	 * This method is named getProjectByKey instead of getProjectById like the others since a Project has both a key and an id and 
	 * in this case the search is done by the key.
	 * @param projectKey
	 * @return
	 * @throws Exception
	 */
	public Project getProjectByKey(String projectKey, String kanbanBoardId) throws Exception {
		verifyInitialization(kanbanBoardId);
		Project project = projectMap.get(projectKey);
		if (project == null) {
			String authenticationToken = soapService.login(appConfig.getUserName(), appConfig.getPassword());
			RemoteProject remoteProject = soapService.getProjectByKey(authenticationToken, projectKey);
			if (remoteProject != null) {
				project = new Project();
				project.setName(remoteProject.getName());
				project.setDescription(remoteProject.getDescription());
				project.setId(remoteProject.getKey());
				projectMap.put(projectKey, project);
			}
		}
		return project;
	}
	
	public Assignee getAssigneeByUserName(String userName, String kanbanBoardId) throws Exception {
		verifyInitialization(kanbanBoardId);
		Assignee assignee = assigneeMap.get(userName);
		if (assignee == null) {
			String authenticationToken = soapService.login(appConfig.getUserName(), appConfig.getPassword());
			RemoteUser remoteUser = soapService.getUser(authenticationToken, userName);
			if (remoteUser != null) {
				assignee = new Assignee();
				assignee.setName(remoteUser.getName());
				assignee.setFullName(remoteUser.getFullname());
				assignee.setEmail(remoteUser.getEmail());
				assigneeMap.put(remoteUser.getName(), assignee);
			}
		}
		return assignee;
	}
	
	public void resetCache() {
		initializationRequired = true;
	}

}
