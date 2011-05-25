package com.stw.kanban.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.atlassian.jira.rpc.soap.client.JiraSoapService;
import com.atlassian.jira.rpc.soap.client.JiraSoapServiceService;
import com.atlassian.jira.rpc.soap.client.JiraSoapServiceServiceLocator;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.name.Names;
import com.stw.kanban.client.KanbanBoardService;
import com.stw.kanban.server.jiramanager.DummyJiraIssueManager;
import com.stw.kanban.server.jiramanager.JiraCache;
import com.stw.kanban.server.jiramanager.JiraManager;
import com.stw.kanban.server.jiramanager.JiraManagerImpl;
import com.stw.kanban.server.jiramanager.KanbanConfigDao;
import com.stw.kanban.server.jiramanager.KanbanConfigXmlParser;
import com.stw.kanban.server.jiramanager.XmlKanbanConfigDao;

public class DefaultBindingModule extends AbstractModule {

	private String configFile = "default.xml";
	private String jiraSoapUrl = "http://localhost:8090";
	private boolean mockJiraManager = false;
	
	@Override
	protected void configure() {
		loadProperties(binder());
		String xmlKanbanConfig = Thread.currentThread().getContextClassLoader().getResource(configFile).getFile();
		//TODO:  Inject the XSD path as well to the parser
		JiraSoapServiceService jiraSoapServiceGetter = new JiraSoapServiceServiceLocator();
		JiraSoapService soapService;
		try {
			soapService = jiraSoapServiceGetter.getJirasoapserviceV2(new URL(jiraSoapUrl));
		} catch (Exception e) {
			// TODO Analyze when this Exception is thrown and decide if the ideal SOAP object to inject is a JiraSoapService
			throw new RuntimeException(e);
		}
		bind(KanbanBoardService.class).to(KanbanBoardServiceImpl.class);
		bind(KanbanConfigDao.class).to(XmlKanbanConfigDao.class);
		if (mockJiraManager) {
			bind(JiraManager.class).to(DummyJiraIssueManager.class);
		} else {
			bind(JiraManager.class).to(JiraManagerImpl.class);
		}
		bind(JiraCache.class);
		bind(AppConfig.class);
		bind(JiraSoapService.class).toInstance(soapService);
		bindConstant().annotatedWith(KanbanConfigXmlParser.XmlKanbanConfigPath.class).to(xmlKanbanConfig);
	}

	//TODO:  Look into changing named properties for constants since they can be identified with refactoring
	private void loadProperties(Binder binder) {
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/default.properties");
		Properties appProperties = new Properties();
		try {
			appProperties.load(stream);
			Names.bindProperties(binder, appProperties);
		} catch (IOException e) {
			// This is the preferred way to tell Guice something went wrong
			binder.addError(e);
		}
		configFile = appProperties.getProperty("jira.kanban.configFile");
		jiraSoapUrl = appProperties.getProperty("jira.soap.url");
		mockJiraManager = Boolean.parseBoolean(appProperties.getProperty("mockJiraManager"));
	}

}
