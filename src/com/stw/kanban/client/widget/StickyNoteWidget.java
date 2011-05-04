package com.stw.kanban.client.widget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.stw.kanban.client.entities.Assignee;
import com.stw.kanban.client.entities.JiraIssue;

public class StickyNoteWidget extends Composite {
	
	public StickyNoteWidget(JiraIssue issue, StickyNoteWidgetOptions options) {
		VerticalPanel panel = new VerticalPanel();
		panel.setStyleName(options.getStyleName());
		panel.setWidth(options.getWidthPx() + "px");
		
		panel.add(new StickyHeader(issue));
		panel.add(new SummaryText(issue));
		panel.add(new HTML("<br/>"));
		panel.add(new AssigneeLabel(issue.getAssignee()));
		
		initWidget(panel);
	}
	
	public StickyNoteWidget(JiraIssue issue) {
		this(issue, new StickyNoteWidgetOptions());
	}
	
	class SummaryText extends Label {
		public SummaryText(JiraIssue issue) {
			setText(issue.getSummary());
			setStylePrimaryName("summary");
		}
	}
	
	class AssigneeLabel extends Label {
		public AssigneeLabel(Assignee assignee) {
			if (assignee != null) { 
				setText(assignee.getFullName());
			}
		}
	}
	
	class StickyHeader extends Composite {
		public StickyHeader(JiraIssue issue) {
			HorizontalPanel panel = new HorizontalPanel();
			panel.setStyleName("stickyHeader");
			panel.setWidth("100%");
			
			if (issue.getIssueType()  != null) {
				Image typeImage = new Image();
				typeImage.setUrl(issue.getIssueType().getIconUrl());
				typeImage.setTitle(issue.getIssueType().getDescription());
				panel.add(typeImage);	
			}
				
			Label title = new Label(issue.getKey() + "(" + issue.getProject().getName() + ")");
			title.setStyleName("stickyKey");
			panel.add(title);
			
			if (issue.getIssuePriority() != null) {
				Image priorityImage = new Image();
				priorityImage.setUrl(issue.getIssuePriority().getIconUrl());
				priorityImage.setTitle(issue.getIssuePriority().getDescription());
				panel.add(priorityImage);
			}
			
			initWidget(panel);
		}
	}
}
