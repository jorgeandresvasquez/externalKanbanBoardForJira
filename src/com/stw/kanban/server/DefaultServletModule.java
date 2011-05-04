package com.stw.kanban.server;

import com.google.inject.servlet.ServletModule;

public class DefaultServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/kanbanboard/GWT.rpc").with(GuiceRemoteServiceServlet.class);
	}
}