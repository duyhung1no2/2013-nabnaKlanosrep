package com.duyhungapp.kandoo.dao;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

@SuppressLint("SimpleDateFormat")
public class DB {
	
	public static final String DB_NAME = "KandooDB";
	public static final int    DB_VERSION = 33;
	
	public static final String ID = "_id";

	public static final String TABLE_BOARD = "Board";
	public static final String BOARD_ID = "BoardId";
	public static final String BOARD_NAME = "BoardName";

	public static final String TABLE_TASK = "Task";
	public static final String TASK_ID = "TaskId";
	public static final String TASK_NAME = "TaskName";
	public static final String TASK_NOTE = "TaskNote";
	public static final String TASK_CREATEDATE = "TaskCreateDate";
	public static final String TASK_DONEDATE = "TaskDoneDate";
	public static final String TASK_DUEDATE = "TaskDueDate";
	public static final String TASK_ALARM = "TaskAlarm";

	public static final String TABLE_POMODORO = "Pomodoro";
	public static final String POMO_ID = "PomoId";
	public static final String POMO_STARTDATE = "PomoStartDate";
	public static final String POMO_ENDDATE = "PomoEndDate";

	public static final String TABLE_ITEM = "Item";
	public static final String ITEM_ID = "ItemId";
	public static final String ITEM_NAME = "ItemName";
	public static final String ITEM_STATUS = "ItemStatus";
	public static final String ITEM_CREATEDATE = "ItemCreateDate";
	public static final String ITEM_DONEDATE = "ItemDoneDate";
	
	public static final SimpleDateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
}
