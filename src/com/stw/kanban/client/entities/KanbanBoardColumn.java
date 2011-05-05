package com.stw.kanban.client.entities;

import java.util.ArrayList;

public interface KanbanBoardColumn {
	
	public String getName();
	
	public void setName(String name);

	public ArrayList<? extends StickyNoteIssue> getIssues();
	
	public void addIssue(StickyNoteIssue issue);
	
}
