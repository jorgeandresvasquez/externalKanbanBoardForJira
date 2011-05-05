package com.stw.kanban.client.widget.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.*;
import com.stw.kanban.client.entities.Board;
import com.stw.kanban.client.entities.BoardColumn;
import com.stw.kanban.client.entities.JiraIssue;
import com.stw.kanban.client.entities.KanbanBoardColumn;
import com.stw.kanban.client.entities.StickyNoteIssue;
import com.stw.kanban.client.widget.StickyNoteWidgetOptions;
//import com.stw.kanban.client.widget.presenter.KanbanBoardPresenter;
import com.stw.kanban.client.widget.view.KanbanBoardView;
import com.stw.kanban.client.widget.view.KanbanBoardViewImpl;

public class KanbanBoardInjectorModule extends AbstractGinModule {

	@Override
	protected void configure() {
		//Simple bindings
		bind(KanbanBoardViewImpl.class).in(Singleton.class);
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(StickyNoteWidgetOptions.class);
		bind(Board.class);
		
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
}
