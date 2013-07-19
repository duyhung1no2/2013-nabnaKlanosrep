package com.duyhungapp.kandoo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.duyhungapp.kandoo.dao.converter.TaskConverter;
import com.duyhungapp.kandoo.model.Item;
import com.duyhungapp.kandoo.model.Task;

public class TaskDAO extends DatabaseDAO<Task> {
	private Context mContext;

	public TaskDAO(Context context) {
		super(context);
		this.mContext = context;
		converter = new TaskConverter();
	}

	@Override
	public List<Task> getAll() {
		openDB();
		List<Task> tasks = new ArrayList<Task>();

		Cursor cursor = db.query(true, DB.TABLE_TASK, new String[] {}, null, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Task t = converter.convertCursorToModel(cursor);
			t.setBoard(new BoardDAO(mContext).get(cursor.getInt(cursor.getColumnIndex(DB.BOARD_ID))));
			tasks.add(t);
		}

		closeDB();
		return tasks;
	}

	public List<Task> getAllByBoardId(int boardId) {
		openDB();
		List<Task> tasks = new ArrayList<Task>();

		Cursor cursor = db.query(true, DB.TABLE_TASK, new String[] {}, DB.BOARD_ID + " = " + boardId, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Task t = converter.convertCursorToModel(cursor);
			t.setBoard(new BoardDAO(mContext).get(cursor.getInt(cursor.getColumnIndex(DB.BOARD_ID))));
			tasks.add(t);
		}

		closeDB();
		return tasks;
	}

	@Override
	public Task get(int id) {
		openDB();
		Task task = null;

		Cursor cursor = db.query(true, DB.TABLE_TASK, new String[] {}, DB.TASK_ID + " = " + id, null, null, null, null, null);

		cursor.moveToFirst();
		task = converter.convertCursorToModel(cursor);
		task.setBoard(new BoardDAO(mContext).get(cursor.getInt(cursor.getColumnIndex(DB.BOARD_ID))));

		closeDB();
		return task;
	}

	@Override
	public void insert(Task obj) {
		openDB();

		String insertStm = "INSERT INTO " + DB.TABLE_TASK + "(" +
				DB.TASK_ID + ", " +
				DB.TASK_NAME + ", " +
				DB.TASK_NOTE + ", " +
				DB.TASK_CREATEDATE + ", " +
				DB.TASK_DONEDATE + ", " +
				DB.TASK_DUEDATE + ", " +
				DB.TASK_ALARM + ", " +
				DB.BOARD_ID +
				") VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		SQLiteStatement stm = db.compileStatement(insertStm);
		stm.clearBindings();
		stm.bindNull(1);
		stm.bindString(2, obj.getTaskName());
		stm.bindString(3, obj.getTaskNote());
		stm.bindString(4, DB.DB_DATE_FORMAT.format(obj.getTaskCreateDate()));

		if (obj.getTaskDoneDate() != null)
			stm.bindString(5, DB.DB_DATE_FORMAT.format(obj.getTaskDoneDate()));
		else
			stm.bindNull(5);

		if (obj.getTaskDueDate() != null)
			stm.bindString(6, DB.DB_DATE_FORMAT.format(obj.getTaskDueDate()));
		else
			stm.bindNull(6);

		if (obj.getTaskAlarm() != null)
			stm.bindString(7, DB.DB_DATE_FORMAT.format(obj.getTaskAlarm()));
		else
			stm.bindNull(7);

		stm.bindLong(8, obj.getBoard().getBoardId());

		stm.execute();
		closeDB();
	}

	@Override
	public void update(Task obj) {
		openDB();
		ContentValues cv = converter.convertModelToContentValues(obj);
		db.update(DB.TABLE_TASK, cv, DB.TASK_ID + "=" + obj.getTaskId(), null);
		closeDB();
	}

	@Override
	public void delete(Task obj) {
		openDB();
		ItemDAO itemDAO = new ItemDAO(mContext);
		for (Item i : itemDAO.getAllByTaskId(obj.getTaskId())) {
			itemDAO.delete(i);
		}
		db.delete(DB.TABLE_TASK, DB.TASK_ID + "=" + obj.getTaskId(), null);
		closeDB();
	}

	public int getNextId() {
		int nextId = 0;

		for (Task t : this.getAll()) {
			nextId = t.getTaskId();
		}

		return ++nextId;
	}

}
