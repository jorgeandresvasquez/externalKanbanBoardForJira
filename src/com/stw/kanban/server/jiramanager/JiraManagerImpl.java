package com.stw.kanban.server.jiramanager;

import java.util.List;
import org.apache.axis.AxisProperties;
import com.atlassian.jira.rpc.soap.beans.RemoteIssue;
import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.google.inject.Inject;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.server.AppConfig;

public class JiraManagerImpl implements JiraManager {

	private KanbanConfigDao kanbanConfigDao;
	private JiraCache jiraCache;
	private JiraSoapService soapService;
	private AppConfig appConfig;

	@Inject
	public JiraManagerImpl(KanbanConfigDao kanbanConfigDao, JiraCache jiraCache, JiraSoapService soapService, AppConfig appConfig) {
		this.kanbanConfigDao = kanbanConfigDao;
		this.jiraCache = jiraCache;
		this.soapService = soapService;
		this.appConfig = appConfig;
	}

	@Override
	public Board getIssuesByKanbanBoardId(String id) throws Exception {
		KanbanConfig kanbanConfig = kanbanConfigDao.getKanbanConfig(id);
		if (appConfig.isSkipSslValidation()) {
			AxisProperties.setProperty("axis.socketSecureFactory","org.apache.axis.components.net.SunFakeTrustSocketFactory");
		}
		String authenticationToken = soapService.login(appConfig.getUserName(), appConfig.getPassword());
		Board outputBoard = new Board();
		outputBoard.setDescription(kanbanConfig.getDescription());
		List<StepConfig> stepConfigs = kanbanConfig.getStepConfigList();
		for (StepConfig stepConfig : stepConfigs) {
			RemoteIssue[] issues = soapService.getIssuesFromJqlSearch(authenticationToken, stepConfig.getQuery(), kanbanConfig.getMaxElementsPerStep());
			BoardColumn boardColumn = new BoardColumn();
			boardColumn.setName(stepConfig.getName());
			if (issues != null) {
				for (RemoteIssue remoteIssue : issues) {
					JiraIssue jiraIssue = new JiraIssue();
					String userName = remoteIssue.getAssignee();
					if (userName != null) {
						jiraIssue.setAssignee(jiraCache.getAssigneeByUserName(userName, id));
					}
					jiraIssue.setIssuePriority(jiraCache.getIssuePriorityById(remoteIssue.getPriority(), id));
					jiraIssue.setIssueStatus(jiraCache.getIssueStatusById(remoteIssue.getStatus(), id));
					jiraIssue.setIssueType(jiraCache.getIssueTypeById(remoteIssue.getType(), id));
					jiraIssue.setKey(remoteIssue.getKey());
					jiraIssue.setSummary(remoteIssue.getSummary());
					jiraIssue.setProject(jiraCache.getProjectByKey(remoteIssue.getProject(), id));
					boardColumn.addJiraIssue(jiraIssue);
				}
			}
			outputBoard.addColumn(boardColumn);
		}
		return outputBoard;

	}

}
