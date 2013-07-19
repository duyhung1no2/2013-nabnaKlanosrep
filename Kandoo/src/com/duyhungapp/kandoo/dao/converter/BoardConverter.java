package com.duyhungapp.kandoo.dao.converter;

import com.duyhungapp.kandoo.dao.DB;
import com.duyhungapp.kandoo.model.Board;

import android.content.ContentValues;
import android.database.Cursor;

public class BoardConverter implements IConverter<Board> {

	@Override
	public ContentValues convertModelToContentValues(Board obj) {
		ContentValues cv = new ContentValues();

		//cv.put(DB.BOARD_ID, obj.getBoardId());
		cv.put(DB.BOARD_NAME, obj.getBoardName());

		return cv;
	}

	@Override
	public Board convertContentValesToModel(ContentValues cv) {
		Board board = new Board();

		board.setBoardId(cv.getAsInteger(DB.BOARD_ID));
		board.setBoardName(cv.getAsString(DB.BOARD_NAME));

		return board;
	}

	@Override
	public Board convertCursorToModel(Cursor cs) {
		int id = cs.getInt(cs.getColumnIndex(DB.BOARD_ID));
		String name = cs.getString(cs.getColumnIndex(DB.BOARD_NAME));
		
		return new Board(id, name);
	}
}
