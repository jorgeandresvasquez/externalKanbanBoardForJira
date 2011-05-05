package com.stw.kanban.client.widget.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;

/* GWT Best Practices: A Widget composite hides the internal implementation
 * and enable unified handling of the composite as a single widget. */
public class StickyNoteWidget extends Composite {
	
	private Label summaryText;
	private Label assignee;
	private StickyHeader stickyHeaderWidget;
	private String stickyNoteKey;
	
	/**
	 * Creates a Sticky note widget with default {@link com.stw.kanban.client.widget.StickyNoteWidgetOptions}/
	 * */
	public StickyNoteWidget() {
		this(new StickyNoteWidgetOptions());
	}
	
	/**
	 * Creates a Sticky note widget with the given {@link com.stw.kanban.client.widget.StickyNoteWidgetOptions}.
	 * 
	 * @param options Style and Width options for the widget
	 * 
	 * @see com.stw.kanban.client.widget.StickyNoteWidgetOptions
	 * */
	@Inject
	public StickyNoteWidget(StickyNoteWidgetOptions options) {
		VerticalPanel panel = new VerticalPanel();
		initWidget(panel);
		
		panel.setStyleName(options.getStyleName());
		panel.setWidth(options.getWidthPx() + "px");
		
		//Composite widgets
		summaryText = new Label();
		assignee = new Label();
		stickyHeaderWidget = new StickyHeader();
		
		panel.add(stickyHeaderWidget);
		panel.add(summaryText);
		panel.add(new HTML("<br/>"));
		panel.add(assignee);
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
		
		if (issue.getIssuePriority() != null) {
			Image priorityImage = new Image();
			priorityImage.setUrl(issue.getIssuePriority().getIconUrl());
			priorityImage.setTitle(issue.getIssuePriority().getDescription());
			stickyHeaderWidget.setPriorityImage(priorityImage);
		}
	}
	
	/* Since this widget will only ever be used by the sticky note widget it has 
	 * been made an internal class. When creating internal classes be aware that 
	 * you can't use the @Inject GIN annotation. */
	class StickyHeader extends Composite {
		private Label title; 
		private Image typeImage;
		private Image priorityImage;
		private HorizontalPanel panel;
		
		public StickyHeader() {
			panel = new HorizontalPanel();
			/* Don't forget to initialise ALL of your widgets, including the internal ones! */
			initWidget(panel); 
			
			panel.setStyleName("stickyHeader");
			panel.setWidth("100%");
			typeImage = new Image();
			title = new Label();
			title.setStyleName("stickyKey");
			panel.add(title);
		}
		
		protected Label getTitelName() {
			return title;
		}
		
		protected void setTitelName(String titleName) {
			title.setText(titleName);
		}
		
		protected void setTypeImage(Image image) {
			if (image != null) {
				panel.remove(typeImage);
			}
			this.typeImage = image;
			panel.add(typeImage);
		}
		
		protected void setPriorityImage(Image image) {
			if (image != null) {
				panel.remove(priorityImage);
			}
			this.priorityImage = image;
			panel.add(priorityImage);
		}
		
	}
	
}
