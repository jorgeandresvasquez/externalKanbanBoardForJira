package com.stw.kanban.server.jiramanager;

import java.util.List;

public class KanbanConfig {
	
	private String id;
	
	private String description;
	
	private int maxElementsPerStep;
	
	private List<StepConfig> stepConfigList;
	
	public KanbanConfig(String id, String description, int maxElementsPerStep, List<StepConfig> stepConfigList) {
		this.id = id;
		this.description = description;
		this.maxElementsPerStep = maxElementsPerStep;
		this.stepConfigList = stepConfigList;
	}

	public String getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

	public int getMaxElementsPerStep() {
		return maxElementsPerStep;
	}
	
	public List<StepConfig> getStepConfigList() {
		return stepConfigList;
	}
	
	
	
}
