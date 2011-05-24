package com.stw.kanban.client.widget.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.stw.kanban.resources.KanbanBoardResources;


public class NoteHeader extends Composite {
	
	private static final NoteHeaderUiBinder defaultBinder = GWT.create(NoteHeaderUiBinder.class);

	interface NoteHeaderUiBinder extends UiBinder<Widget, NoteHeader> {}

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
//	public NoteHeader() {
//		initWidget(uiBinder.createAndBindUi(this));
//	}

	@UiField Label title; 
	@UiField Image typeImage = new Image();
	@UiField Image priorityImage = new Image();
	@UiField HorizontalPanel panel;
	
	private final KanbanBoardResources resources;
	private final NoteHeaderUiBinder uiBinder;

	public @UiConstructor NoteHeader(final KanbanBoardResources resources, NoteHeaderUiBinder uiBinder) {
		if (uiBinder == null) {
			this.uiBinder = defaultBinder;
		} else {
			this.uiBinder = uiBinder;
		}
		this.resources = resources;
		initWidget(this.uiBinder.createAndBindUi(this));

		// You can access @UiField after calling createAndBindUi like for example:
		// title.setText(titleName) or via setTitleName(titleName);
	}
	
	/* The Composite Interface: This is the external composite interface 
	 * we have chosen to expose for this widget. */
	/**
	 * Gets the summary text for the Sticky Note.
	 * */
	public HasText getTitel() {
		return title;
	}
	
	public void setTitel(String titleName) {
		title.setText(titleName);
	}
	
	public String getTypeImageUrl() {
		return typeImage.getUrl();
	}
	
	public String getPriorityImageUrl() {
		return priorityImage.getUrl();
	}

	/**
	 * Sets the issue type image in the sticky note. 
	 * The default image is displayed if the method is never called. 
	 * If no image should be displayed the typeImage argument can be set to null. 
	 * 
	 * @param priorityImage the image representing issue priority. 
	 * 
	 * @see com.google.gwt.user.client.ui.Image
	 * */
	public void setTypeImage(Image typeImage) {
		if (typeImage == null) {
			this.typeImage.setUrl(this.resources.emptyImageWhite().getURL());
			this.typeImage.setVisible(false);
		}
		else {
			this.typeImage.setUrl(typeImage.getUrl());
			this.typeImage.setVisible(true);
		}
	}
	
	/**
	 * Sets the priority image in the sticky note. 
	 * The default image is displayed if the method is never called. 
	 * If no image should be displayed the priorityImage argument can be set to null. 
	 * 
	 * @param priorityImage the image representing issue priority. 
	 * 
	 * @see com.google.gwt.user.client.ui.Image
	 * */
	public void setPriorityImage(Image priorityImage) {
		if (priorityImage == null) {
			this.priorityImage.setUrl(this.resources.emptyImageWhite().getURL());
			this.priorityImage.setVisible(false);
		}
		else {
			this.priorityImage.setUrl(priorityImage.getUrl());
			this.priorityImage.setVisible(true);
		}
	}

}
