package com.stw.kanban.client.widget.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;
import com.stw.kanban.resources.KanbanBoardResources;

/* GWT Best Practices: A Widget composite hides the internal implementation
 * and enable unified handling of the composite as a single widget. */
public class StickyNoteWidget extends Composite {
	
	@UiTemplate("StickyNoteWidget.ui.xml")
	public interface StickyNoteWidgetUiBinder extends UiBinder<Widget, StickyNoteWidget> {}
	
	private static final StickyNoteWidgetUiBinder defaultBinder = GWT.create(StickyNoteWidgetUiBinder.class);

	@UiField 
	Label summaryText;
	
	@UiField 
	Label assignee;
	
	@UiField(provided=true) 
	NoteHeader stickyHeaderWidget;
	
	private final StickyNoteWidgetOptions options;
	private final StickyNoteWidgetUiBinder uiBinder;
	private String stickyNoteKey;
	
//	/**
//	 * Creates a Sticky note widget with default {@link com.stw.kanban.client.widget.StickyNoteWidgetOptions}/
//	 * */
//	public @UiConstructor StickyNoteWidget(final StickyNoteWidgetUiBinder uiBinder) {
//		this(new StickyNoteWidgetOptions(), defaultBinder);
//	}
	
	/**
	 * Creates a Sticky note widget with the given {@link com.stw.kanban.client.widget.StickyNoteWidgetOptions}.
	 * 
	 * @param options Style and Width options for the widget
	 * @param uiBinder created by <code>GWT.create(StickyNoteWidgetUiBinder.class)</code>
	 * 
	 * @see com.stw.kanban.client.widget.StickyNoteWidgetOptions
	 * */
	/* We can only have one UiBinder constructor annotation per view. */
	@Inject
	public @UiConstructor StickyNoteWidget(KanbanBoardResources resources, StickyNoteWidgetOptions options, final StickyNoteWidgetUiBinder uiBinder) {
		stickyHeaderWidget = new NoteHeader(resources, null);
		if (uiBinder == null) {
			this.uiBinder = defaultBinder;
		} else {
			this.uiBinder = uiBinder;
		}
		this.options = options;
		Widget widget = this.uiBinder.createAndBindUi(this);
		widget.setStyleName(resources.style().stickyNote());
		initWidget(this.uiBinder.createAndBindUi(this));
		
	}
	
	/* The Composite Interface: This is the external composite interface 
	 * we have chosen to expose for this widget. */
	/**
	 * Gets the summary text for the Sticky Note.
	 * */
	public HasText getSummaryText() {
		return summaryText;
	}
	
	/**
	 * Gets the assignee value for the Sticky Note.
	 * */
	public HasText getAssignee() {
		return assignee;
	}
	
	public String getStickyNoteKey() {
		return stickyNoteKey;
	}
	
	public StickyNoteWidgetOptions getStickyNoteWidgetOptions() {
		return options;
	}
	
	/**
	 * Sets the StickyNote widget's data given a StickyNote issue.
	 * */
	public void setData(StickyNoteIssue issue) {
		if (issue == null) {
			return;
		}
		summaryText.setText(issue.getSummary());
		if (issue.getAssignee() != null) { 
			assignee.setText(issue.getAssignee().getFullName());
		}
		
		//Setting data in the internal StickyHeader widget
		stickyNoteKey = issue.getKey() + "(" + issue.getProject().getName() + ")";
		stickyHeaderWidget.setTitelName(stickyNoteKey);

		if (issue.getIssueType()  != null) {
			Image typeImage = new Image();
			typeImage.setUrl(issue.getIssueType().getIconUrl());
			typeImage.setTitle(issue.getIssueType().getDescription());
			stickyHeaderWidget.setTypeImage(typeImage);
		}
		else {
			stickyHeaderWidget.setTypeImage(null);
		}
		
		if (issue.getIssuePriority() != null) {
			Image priorityImage = new Image();
			priorityImage.setUrl(issue.getIssuePriority().getIconUrl());
			priorityImage.setTitle(issue.getIssuePriority().getDescription());
			stickyHeaderWidget.setPriorityImage(priorityImage);
		}
		else {
			stickyHeaderWidget.setPriorityImage(null);
		}
	}
	
}
