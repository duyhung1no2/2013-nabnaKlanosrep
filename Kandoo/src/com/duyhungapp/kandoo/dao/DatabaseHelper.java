package com.duyhungapp.kandoo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String createBoardTableQuery = "CREATE TABLE " + DB.TABLE_BOARD + "(" +
																					DB.BOARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
																					DB.BOARD_NAME + " TEXT" +
																					");";
	static final String createTaskTableQuery = "CREATE TABLE " + DB.TABLE_TASK + "(" +
																				  DB.TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
																				  DB.TASK_NAME + " TEXT NOT NULL," +
																				  DB.TASK_NOTE + " TEXT," +
																				  DB.TASK_CREATEDATE + " DATETIME NOT NULL," +
																				  DB.TASK_DONEDATE + " DATETIME," +
																				  DB.TASK_DUEDATE + " DATETIME," +
																				  DB.TASK_ALARM + " DATETIME," +
																				  DB.BOARD_ID + " INTEGER NOT NULL," +
																				  "FOREIGN KEY (" + DB.BOARD_ID +") REFERENCES " + DB.TABLE_BOARD +"(" + DB.BOARD_ID + ")" +
																			      ");";
	static final String createPomodoroTableQuery = "CREATE TABLE " + DB.TABLE_POMODORO + "(" +
																						  DB.POMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
																						  DB.POMO_STARTDATE + " DATETIME NOT NULL," +
																						  DB.POMO_ENDDATE + " DATETIME NOT NULL," +
																						  DB.TASK_ID + " INTEGER NOT NULL," +
																						  "FOREIGN KEY ("+DB.TASK_ID+") REFERENCES "+DB.TABLE_TASK+"("+DB.TASK_ID+")" +
																						  ");";
	static final String createItemTableQuery = "CREATE TABLE " + DB.TABLE_ITEM + "(" +
																				   DB.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
																				   DB.ITEM_NAME + " TEXT NOT NULL," +
																				   DB.ITEM_STATUS + " BOOLEAN NOT NULL," +
																				   DB.ITEM_CREATEDATE + " DATETIME NOT NULL," +
																				   DB.ITEM_DONEDATE + " DATETIME," +
																				   DB.TASK_ID + " INTEGER NOT NULL," +
																				   "FOREIGN KEY ("+DB.TASK_ID+") REFERENCES "+DB.TABLE_TASK+"("+DB.TASK_ID+")" +
																				   ");";
	
	public DatabaseHelper(Context context) {
		super(context, DB.DB_NAME, null, DB.DB_VERSION);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if(!db.isReadOnly()){
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createBoardTableQuery);
		db.execSQL(createTaskTableQuery);
		db.execSQL(createPomodoroTableQuery);
		db.execSQL(createItemTableQuery);
		
		db.execSQL("INSERT INTO " + DB.TABLE_BOARD + " VALUES (1, 'Todo')");
		db.execSQL("INSERT INTO " + DB.TABLE_BOARD + " VALUES (2, 'Doing')");
		db.execSQL("INSERT INTO " + DB.TABLE_BOARD + " VALUES (3, 'Done')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXIST " + DB.TABLE_ITEM);
		db.execSQL("DROP TABLE IF EXIST " + DB.TABLE_POMODORO);
		db.execSQL("DROP TABLE IF EXIST " + DB.TABLE_TASK);
		db.execSQL("DROP TABLE IF EXIST " + DB.TABLE_BOARD);
		onCreate(db);
	}
	
	@Override
	public synchronized void close() {
		super.close();
	}
	
}
