package com.stw.kanban.client.widget.view;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.inject.Inject;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.resources.KanbanBoardResources;

/*
 * GWT Best Practices - Large Scale Application Development and MVP: 
 * 
 * A "data" object that is passed to the view is a very simplistic ViewModel — basically a representation 
 * of a more complex data model using primitives. This is fine for a simplistic view, but as soon as you 
 * start to do something more complex, you quickly realize that something has to give. Either the presenter 
 * needs to know more about the view (making it hard to swap out views for other platforms), or the view needs 
 * to know more about the data model (ultimately making your view smarter, thus requiring more GwtTestCases). 
 * The solution is to use generics along with a third party that abstracts any knowledge of a cell's data type, 
 * as well as how that data type is rendered.
 * 
 * To be able to demonstrate this 'Complex UIs - Dumb Views' concept and Gin we therefore implement this view 
 * using this generics principle! 
 * */
public class KanbanBoardViewImpl<T> extends Composite implements KanbanBoardView<T> {
		
	private VerticalPanel mainPanel;
	private HorizontalPanel boardColumnPanel;
	private Presenter<T> presenter;
	private Label lastUpdatedLabel;
	private Image loadingMask;
	
	@Inject
	private KanbanBoardResources resources;
		
	@Inject		
	public KanbanBoardViewImpl(KanbanBoardResources resources) {
		this.resources = resources;
		this.resources.style().ensureInjected();
	
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		
		loadingMask = new Image(resources.loadingImage());
		mainPanel.add(loadingMask);
		loadingMask.setVisible(false);
		
		boardColumnPanel = new HorizontalPanel();
		mainPanel.add(boardColumnPanel);
		boardColumnPanel.setVisible(false);
		
		mainPanel.setHeight("99%");
		mainPanel.setWidth("99%");
		boardColumnPanel.setHeight("100%");
		boardColumnPanel.setWidth("100%");
		
		lastUpdatedLabel = new Label();
		mainPanel.add(lastUpdatedLabel);
	}
	
	public void onLoadedBoard() {
		if (presenter != null) {
			presenter.onLoadedBoard();
		}
	}
		
	@Override
	public void setData(Board board) {
		
		boardColumnPanel.clear();
		int x = 0;
		for (BoardColumn boardColumn : board.getColumns()) {
			String style = (++x % 2 == 0) ? resources.style().borderColumnEven() : resources.style().borderColumnOdd();
			BoardColumnWidget boardColumnWidget = new BoardColumnWidget(style, resources);
			boardColumnWidget.setData(boardColumn);
			boardColumnPanel.add(boardColumnWidget);
		}
		// Display timestamp showing last refresh.
		lastUpdatedLabel.setText("Last update : " + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_FULL).format(new Date()));
	}

	@Override
	public void setPresenter(Presenter<T> presenter) {
		this.presenter = presenter;
	}

	@Override
	public void maskView(boolean mask) {
		if(mask) {
			boardColumnPanel.setVisible(false);
			loadingMask.setVisible(true);
		}
		else {
			loadingMask.setVisible(false);
			boardColumnPanel.setVisible(true);
		}
	}
	
	
/* Note: This can be compared to... 
 * 
 * GWT Best Practices - MVP simplistic ViewModel:
 * If you are working on a smaller application you may not need generic views but would rather
 * work with a simple view you maybe choose the following implementation of the KanbanBoardView
 * instead. 
 * 
 * public class KanbanBoardViewImpl extends Composite implements KanbanBoardPresenter.Display {
 * 	
 * 	private HorizontalPanel panel;
 * 	
 * 	public KanbanBoardViewImpl() { 
 * 		panel = new HorizontalPanel();
 * 		panel.setHeight("99%");
 * 		panel.setWidth("99%");
 * 		initWidget(panel);
 * 	}
 * 
 * 	@Override
 * 	public HasWidgets getKanbanBoard() {
 * 		return panel;
 * 	}
 * 	
 * 	@Override
 * 	public void setData(Board board) {
 * 		int x = 0;
 * 		for (BoardColumn boardColumn : board.getColumns()) {
 * 			String style = (++x % 2 == 0) ? "columnEven" : "columnOdd";
 * 			BoardColumnWidget boardColumnWidget = new BoardColumnWidget(style);
 * 			boardColumnWidget.setData(boardColumn);
 * 			panel.add(boardColumnWidget);
 * 		}
 * 	} 
 * } 	
 * */
}
