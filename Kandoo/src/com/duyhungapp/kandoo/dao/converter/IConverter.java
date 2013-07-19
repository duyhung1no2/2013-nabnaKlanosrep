package com.duyhungapp.kandoo.dao.converter;

import android.content.ContentValues;
import android.database.Cursor;

public interface IConverter<T> {
	
	public ContentValues convertModelToContentValues(T obj);
	
	public T convertContentValesToModel(ContentValues cv);
	
	public T convertCursorToModel(Cursor cs);
	
}
