package com.stw.kanban.client.widget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.JiraIssue;

public class BoardColumnWidget extends Composite {
	
	public BoardColumnWidget(BoardColumn boardColumn) {
		Label label = new Label(boardColumn.getName());
		label.setStylePrimaryName("columnHeader");
		
		VerticalPanel panel = new VerticalPanel();
		panel.add(label);
		
		for (JiraIssue issue : boardColumn.getIssues()) {
			panel.add(new StickyNoteWidget(issue));	
		}
		
		initWidget(panel);
	}
	
}
