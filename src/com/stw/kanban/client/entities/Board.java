package com.stw.kanban.client.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Board implements Serializable {
	private List<BoardColumn> columns = new ArrayList<BoardColumn>();
	private String description;
	
	public List<BoardColumn> getColumns() {
		return columns;
	}
	public void addColumn(BoardColumn boardColumn) {
		columns.add(boardColumn);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
