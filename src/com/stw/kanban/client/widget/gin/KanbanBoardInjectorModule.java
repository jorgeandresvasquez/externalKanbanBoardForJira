package com.stw.kanban.client.widget.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.resources.client.ClientBundle;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.stw.kanban.client.KanbanBoardService;
import com.stw.kanban.client.KanbanBoardServiceAsync;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.client.entities.KanbanBoardColumn;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;
import com.stw.kanban.client.widget.view.KanbanBoardView;
import com.stw.kanban.client.widget.view.KanbanBoardViewImpl;
import com.stw.kanban.client.widget.view.StickyNoteWidget;
import com.stw.kanban.resources.KanbanBoardResources;
import com.stw.kanban.resources.KanbanBoardResources.KanbanboardCssResources;
import com.stw.kanban.server.KanbanBoardServiceImpl;

public class KanbanBoardInjectorModule extends AbstractGinModule {

	@Override
	protected void configure() {
		//Simple bindings
		bind(KanbanBoardViewImpl.class).in(Singleton.class);
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(KanbanBoardServiceAsync.class).in(Singleton.class);
		bind(KanbanBoardResources.class).in(Singleton.class);
		bind(StickyNoteWidgetOptions.class);
		bind(Board.class);
		// Since our StickyNoteWidget view is not a singelton (for obvious reasons) we want to make 
		// the UiBinder for StickyNote a singelton!
		bind(StickyNoteWidget.StickyNoteWidgetUiBinder.class).in(Singleton.class); 
		bind(ClientBundle.class).to(KanbanBoardResources.class);
		
		//Constant bindings
		bindConstant().annotatedWith(BoardColumnStyle.class).to("sticky taped");
		
		//Interface binding
		bind(StickyNoteIssue.class).to(JiraIssue.class);
		bind(KanbanBoardColumn.class).to(BoardColumn.class);
		
		//Interface binding of generics: bind(TypeLiteral).to(TypeLiteral)
		bind(new TypeLiteral<KanbanBoardView<Board>>() {}).to(new TypeLiteral<KanbanBoardViewImpl<Board>>() {});

		
		//View and presenter bindings (simplified syntax via our AbstractPresenterGinModule)
//		bindPresenter(KanbanBoardPresenter.class, KanbanBoardPresenter.Display.class, KanbanBoardViewImpl.class);
	}
	
//	// Additional injection providers
	@Inject
	@Provides KanbanboardCssResources createKanbanBoardResources(final KanbanBoardResources resources) {
		KanbanboardCssResources style = resources.style();
		style.ensureInjected();
		return style;
	}
}
