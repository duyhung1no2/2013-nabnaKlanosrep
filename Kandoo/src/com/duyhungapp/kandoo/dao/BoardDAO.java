package com.duyhungapp.kandoo.dao;

import java.util.ArrayList;
import java.util.List;

import com.duyhungapp.kandoo.dao.converter.BoardConverter;
import com.duyhungapp.kandoo.model.Board;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class BoardDAO extends DatabaseDAO<Board> {

	public BoardDAO(Context context) {
		super(context);
		converter = new BoardConverter();
	}

	/**
	 * CRUD BOARD
	 */
	@Override
	public List<Board> getAll() {
		openDB();

		List<Board> boards = new ArrayList<Board>();

		Cursor cursor = db.query(true, DB.TABLE_BOARD, new String[] {}, null, null, null, null, null, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			boards.add(converter.convertCursorToModel(cursor));
		}

		closeDB();
		return boards;
	}

	@Override
	public void insert(Board board) {
		openDB();

		ContentValues cv = new ContentValues();
		cv.put(DB.BOARD_NAME, board.getBoardName());
		db.insert(DB.TABLE_BOARD, null, cv);
		closeDB();
	}

	@Override
	public Board get(int id) {
		openDB();

		Cursor cursor = db.query(true, DB.TABLE_BOARD, new String[] {},
				DB.BOARD_ID + " = " + id, null, null, null, null, null);
		cursor.moveToFirst();
		Board board = converter.convertCursorToModel(cursor);

		closeDB();
		return board;

	}

	@Override
	public void update(Board obj) {
		openDB();

		ContentValues cv = converter.convertModelToContentValues(obj);
		db.update(DB.TABLE_BOARD, cv, DB.BOARD_ID + "=" + obj.getBoardId(),
				null);
		closeDB();
	}

	@Override
	public void delete(Board obj) {
		openDB();

		db.delete(DB.TABLE_BOARD, DB.BOARD_ID + "=" + obj.getBoardId(), null);
		closeDB();
	}

}
