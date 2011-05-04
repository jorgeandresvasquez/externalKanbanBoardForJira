package com.stw.kanban.client;

@SuppressWarnings("serial")
public class BoardRetrievalException extends Exception {

	private String message;
	
	public BoardRetrievalException() {}
	
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
