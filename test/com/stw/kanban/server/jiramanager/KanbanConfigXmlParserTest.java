package com.stw.kanban.server.jiramanager;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class KanbanConfigXmlParserTest {

	
	private KanbanConfigXmlParser kanbanConfigXmlParser;

	@Test(expected=KanbanConfigParsingException.class)
	public void testParseMissingAttributes() throws KanbanConfigParsingException {
		kanbanConfigXmlParser = new KanbanConfigXmlParser(retrieveFullResourcePath("testInvalidConfigMissingAttributes.xml"));
		kanbanConfigXmlParser.parse();
	}
	
	@Test(expected=KanbanConfigParsingException.class)
	public void testParseMissingElements() throws KanbanConfigParsingException {
		kanbanConfigXmlParser = new KanbanConfigXmlParser(retrieveFullResourcePath("testInvalidConfigMissingElements.xml"));
		kanbanConfigXmlParser.parse();
	}
	
	@Test(expected=KanbanConfigParsingException.class)
	public void testParseRepeatedFilterIds() throws KanbanConfigParsingException {
		kanbanConfigXmlParser = new KanbanConfigXmlParser(retrieveFullResourcePath("testInvalidConfigRepeatedIds.xml"));
		kanbanConfigXmlParser.parse();
	}
	
	@Test
	public void testParseValidXmlFIle() throws Exception {
		kanbanConfigXmlParser = new KanbanConfigXmlParser(retrieveFullResourcePath("testValidConfig.xml"));
		KanbanConfig testingKanbanConfig = kanbanConfigXmlParser.getKanbanConfig("testingKanbanTeamBlack");
		assertNotNull(testingKanbanConfig);
		assertEquals(testingKanbanConfig.getMaxElementsPerStep(), 500);
		assertEquals(testingKanbanConfig.getDescription(), "This is a testing kanban Config");
		assertEquals(testingKanbanConfig.getId(), "testingKanbanTeamBlack");
		List<StepConfig> stepConfigList = testingKanbanConfig.getStepConfigList();
		StepConfig stepConfig1 = stepConfigList.get(0);
		assertEquals(stepConfig1.getName(), "Begin");
		assertEquals(stepConfig1.getQuery(), "project IN (BLACK, DM, STWCS, LSRVC, EAF) AND AND assignee IN (javasquez, jroberge, mmclean, dolczak, sbolduc, ddelautre, crapauzu) status IN (Open, Reopened, \"Awaiting Development\", \"Pending Resource Allocation\") AND assignee IN (javasquez, jroberge, mmclean, dolczak, sbolduc, ddelautre, crapauzu)");
		StepConfig stepConfig2 = stepConfigList.get(1);
		assertEquals(stepConfig2.getName(), "Step2");
		assertEquals(stepConfig2.getQuery(), "project IN (BLACK, DM, STWCS, LSRVC, EAF) AND status IN (\"In Development\", \"In Progress\")");
		StepConfig stepConfig3 = stepConfigList.get(2);
		assertEquals(stepConfig3.getName(), "Step3");
		assertEquals(stepConfig3.getQuery(), "status IN (\"Peer Reviewing\") AND assignee IN (javasquez, jroberge, mmclean, dolczak, sbolduc, ddelautre, crapauzu)");
		StepConfig stepConfig4 = stepConfigList.get(3);
		assertEquals(stepConfig4.getName(), "End");
		assertEquals(stepConfig4.getQuery(), "project IN (BLACK, DM, STWCS, LSRVC, EAF) AND status IN (\"Ready for QA\", \"Integration Testing\")");
	}
	
	@Test(expected=KanbanConfigNotFoundException.class)
	public void testRetrieveNonExistentKanbanConfig() throws Exception {
		kanbanConfigXmlParser = new KanbanConfigXmlParser(retrieveFullResourcePath("testValidConfig.xml"));
		kanbanConfigXmlParser.getKanbanConfig("nonExistentKanbanId");
	}	
	
	private String retrieveFullResourcePath(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResource("com/stw/kanban/server/jiramanager/resources/" + fileName).getFile();
	}
}
