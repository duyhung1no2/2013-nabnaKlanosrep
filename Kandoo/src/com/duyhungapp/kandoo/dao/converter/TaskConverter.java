package com.duyhungapp.kandoo.dao.converter;

import java.text.ParseException;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.duyhungapp.kandoo.dao.DB;
import com.duyhungapp.kandoo.model.Task;

public class TaskConverter implements IConverter<Task>{

	@SuppressLint("SimpleDateFormat")
	@Override
	public ContentValues convertModelToContentValues(Task obj) {
		ContentValues cv = new ContentValues();
		
		cv.put(DB.TASK_ID, obj.getTaskId());
		cv.put(DB.TASK_NAME, obj.getTaskName());
		cv.put(DB.TASK_NOTE, obj.getTaskNote());
		cv.put(DB.TASK_CREATEDATE, obj.getTaskCreateDate() == null ? null : DB.DB_DATE_FORMAT.format(obj.getTaskCreateDate()));
		cv.put(DB.TASK_DONEDATE, obj.getTaskDoneDate() == null ? null : DB.DB_DATE_FORMAT.format(obj.getTaskDoneDate()));
		cv.put(DB.TASK_DUEDATE, obj.getTaskDueDate() == null ? null : DB.DB_DATE_FORMAT.format(obj.getTaskDueDate()));
		cv.put(DB.TASK_ALARM, obj.getTaskAlarm() == null ? null : DB.DB_DATE_FORMAT.format(obj.getTaskAlarm()));
		cv.put(DB.BOARD_ID, obj.getBoard().getBoardId());
		return cv;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public Task convertContentValesToModel(ContentValues cv) {
		Task task = new Task();
		
		task.setTaskId(cv.getAsInteger(DB.TASK_ID));
		task.setTaskName(cv.getAsString(DB.TASK_NAME));
		task.setTaskNote(cv.getAsString(DB.TASK_NOTE));
		try {
			task.setTaskCreateDate(cv.getAsString(DB.TASK_CREATEDATE) != "" ? DB.DB_DATE_FORMAT .parse(cv.getAsString(DB.TASK_CREATEDATE)) : null);
			task.setTaskDoneDate(cv.getAsString(DB.TASK_DONEDATE) !=  ""  ? DB.DB_DATE_FORMAT.parse(cv.getAsString(DB.TASK_DONEDATE)) : null);
			task.setTaskDueDate(cv.getAsString(DB.TASK_DUEDATE) !=  ""  ? DB.DB_DATE_FORMAT.parse(cv.getAsString(DB.TASK_DUEDATE)) : null);
			task.setTaskAlarm(cv.getAsString(DB.TASK_ALARM) !=  ""  ? DB.DB_DATE_FORMAT.parse(cv.getAsString(DB.TASK_ALARM)) : null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return task;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public Task convertCursorToModel(Cursor cs) {
		
		int id = cs.getInt(cs.getColumnIndex(DB.TASK_ID));
		String name = cs.getString(cs.getColumnIndex(DB.TASK_NAME));
		String note = cs.getString(cs.getColumnIndex(DB.TASK_NOTE));
		
		try {
			Date createDate = DB.DB_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(DB.TASK_CREATEDATE)));
			Date doneDate = cs.getString(cs.getColumnIndex(DB.TASK_DONEDATE)) != null  ? DB.DB_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(DB.TASK_DONEDATE))) : null;
			Date dueDate = cs.getString(cs.getColumnIndex(DB.TASK_DUEDATE)) != null ? DB.DB_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(DB.TASK_DUEDATE))) : null;
			Date alarm = cs.getString(cs.getColumnIndex(DB.TASK_ALARM)) != null ? DB.DB_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(DB.TASK_ALARM))) : null;
		
			Task task = new Task(id, name, note, createDate, doneDate, dueDate, alarm);
			return task;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;				
	}

}
