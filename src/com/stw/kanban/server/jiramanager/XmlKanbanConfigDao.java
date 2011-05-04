package com.stw.kanban.server.jiramanager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XmlKanbanConfigDao implements KanbanConfigDao {

	private KanbanConfigXmlParser kanbanConfigXmlParser;
	
	@Inject
	public XmlKanbanConfigDao(KanbanConfigXmlParser kanbanConfigXmlParser) {
		this.kanbanConfigXmlParser = kanbanConfigXmlParser;
	}

	@Override
	public KanbanConfig getKanbanConfig(String kanbanConfigId) throws Exception {
		return kanbanConfigXmlParser.getKanbanConfig(kanbanConfigId);
	}
	
}
