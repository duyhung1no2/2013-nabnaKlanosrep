package com.duyhungapp.kandoo.dao;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duyhungapp.kandoo.dao.converter.IConverter;

public abstract class DatabaseDAO<T> {
	
	public DatabaseHelper helper;
	public SQLiteDatabase db;
	public IConverter<T> converter;
	
	public DatabaseDAO(Context context) {
		helper = new DatabaseHelper(context);
	}
	
	public void openDB(){
		db = helper.getWritableDatabase();
	}
	
	public void closeDB(){
		db.close();
	}
		
	public Cursor rawQuery (String sql, String[] selectionArgs) {
		openDB();
		Cursor c = db.rawQuery(sql, selectionArgs);
		return c;
	}

	public abstract List<T> getAll();
	
	public abstract T get(int id);

	public abstract void insert(T obj);

	public abstract void update(T obj);

	public abstract void delete(T obj);

}
