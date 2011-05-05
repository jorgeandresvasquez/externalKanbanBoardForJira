package com.stw.kanban.client.exception;

@SuppressWarnings("serial")
public class BoardRetrievalException extends Exception {

	private String message;

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
