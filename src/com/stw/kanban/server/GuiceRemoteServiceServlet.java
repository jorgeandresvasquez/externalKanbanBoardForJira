package com.stw.kanban.server;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class GuiceRemoteServiceServlet extends RemoteServiceServlet {
	@Inject
	private Injector injector;

	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			RPCRequest req = RPC.decodeRequest(payload, null, this);

			RemoteService service = getServiceInstance(req.getMethod().getDeclaringClass());

			return RPC.invokeAndEncodeResponse(service, req.getMethod(), req.getParameters(), req.getSerializationPolicy());
		} catch (IncompatibleRemoteServiceException ex) {
			log("IncompatibleRemoteServiceException in the processCall(String) method.", ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}

	@SuppressWarnings({ "unchecked" })
	private RemoteService getServiceInstance(Class serviceClass) {
		return (RemoteService) injector.getInstance(serviceClass);
	}
}
