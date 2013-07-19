package com.duyhungapp.kandoo.model;

import java.io.Serializable;

public class Board implements Serializable {

	private static final long serialVersionUID = 1L;
	private int _boardId;
	private String _boardName;

	public Board() {
	}

	public Board(int _boardID, String _boardName) {
		this._boardId = _boardID;
		this._boardName = _boardName;
	}

	public int getBoardId() {
		return _boardId;
	}

	public void setBoardId(int _boardID) {
		this._boardId = _boardID;
	}

	public String getBoardName() {
		return _boardName;
	}

	public void setBoardName(String _boardName) {
		this._boardName = _boardName;
	}
	
	@Override
	public String toString() {
		return this.getBoardName();
	}

}
