package com.stw.kanban.server.jiramanager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({	
	// com.stw.statservices.transfercount
	KanbanConfigXmlParserTest.class,
	JiraManagerImplTest.class,
	JiraCacheTest.class
})
public class JiraManagerTestSuite {

}
