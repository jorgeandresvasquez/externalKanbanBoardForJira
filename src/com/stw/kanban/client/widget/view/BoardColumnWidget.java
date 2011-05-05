package com.stw.kanban.client.widget.view;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.stw.kanban.client.entities.KanbanBoardColumn;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.gin.BoardColumnStyle;

public class BoardColumnWidget extends Composite {
	
	private final Label columnName;
	private ArrayList<StickyNoteWidget> stickyNotes;
	private VerticalPanel panel;
	
	@Inject //GIN Injection annotation for this particular string: interface BoardColumnStyleName
	public BoardColumnWidget(@BoardColumnStyle String styleName) { 
		panel = new VerticalPanel();
		initWidget(panel);
		stickyNotes = new ArrayList<StickyNoteWidget>();
		
		columnName = new Label();
		columnName.setStylePrimaryName("columnHeader");
		
		panel.add(columnName);
		panel.add(new HTML("<br/>"));

		panel.addStyleName(styleName);
	}
	
	public HasText getColumnName() {
		return columnName;
	}
	
	public ArrayList<Widget> getStickyNotes() {
		ArrayList<Widget> stickyNoteWidgets = new ArrayList<Widget>();
		
		for (StickyNoteWidget stickyNote : stickyNotes) {
			stickyNoteWidgets.add(stickyNote.asWidget());
		}
		
		return stickyNoteWidgets;
	}
	
	public void setData(KanbanBoardColumn boardColumn) {
		columnName.setText(boardColumn.getName());
		
		StickyNoteWidget stickyNoteWidget = null;
		for (StickyNoteIssue issue : boardColumn.getIssues()) {
			stickyNoteWidget = new StickyNoteWidget();
			stickyNoteWidget.setData(issue);
			stickyNotes.add(stickyNoteWidget);
			panel.add(stickyNoteWidget);
		}
	}
	
}
