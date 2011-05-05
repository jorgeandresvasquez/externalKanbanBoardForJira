package com.stw.kanban.client.widget.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

//MVP Boilerplate Code: 1 AbstractPresenter per application.
public abstract interface AbstractPresenter {

	public abstract void execute(final HasWidgets container);
}
