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
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;
import com.stw.kanban.client.widget.gin.BoardColumnStyle;
import com.stw.kanban.client.widget.view.StickyNoteWidget.StickyNoteWidgetUiBinder;
import com.stw.kanban.resources.KanbanBoardResources;

/* This widget is a widget implemented without a UIBinder due to its dynamic nature. 
 * Since we don't know how many sticky notes that will be loaded from start. */
public class BoardColumnWidget extends Composite {
	
	private final Label columnName;
	private ArrayList<StickyNoteWidget> stickyNotes;
	private VerticalPanel panel;
	private StickyNoteWidgetUiBinder uiBinder;
	private KanbanBoardResources resources;
	
	@Inject //GIN Injection annotation for this particular string: interface BoardColumnStyleName
	public BoardColumnWidget(@BoardColumnStyle String styleName, KanbanBoardResources resources, StickyNoteWidgetUiBinder uiBinder) { 
		this.resources = resources;
		this.resources.style().ensureInjected();
		this.uiBinder = uiBinder;
		
		panel = new VerticalPanel();
		/* If you are not using the UiBinder to declare your widgets: 
		 * Don't forget to initialise ALL of your widgets, including the internal ones! */
		initWidget(panel);
		stickyNotes = new ArrayList<StickyNoteWidget>();
		
		columnName = new Label();
		columnName.setStylePrimaryName(resources.style().borderColumnHeader());
		
		panel.add(columnName);
		panel.add(new HTML("<br/>"));

		if (styleName.equals(resources.style().borderColumnEven())) {
			panel.addStyleName(resources.style().borderColumnEven());
		} else {
			panel.addStyleName(resources.style().borderColumnOdd());
		}
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
		if (boardColumn == null) {
			throw new IllegalArgumentException("The application tried to set a column without any data!");
		}
		columnName.setText(boardColumn.getName());
		
		StickyNoteWidget stickyNoteWidget = null;
		for (StickyNoteIssue issue : boardColumn.getIssues()) {
			stickyNoteWidget = new StickyNoteWidget(resources, new StickyNoteWidgetOptions(resources), uiBinder);
			stickyNoteWidget.setData(issue);
			stickyNotes.add(stickyNoteWidget);
			panel.add(stickyNoteWidget);
		}
	}
	
}
