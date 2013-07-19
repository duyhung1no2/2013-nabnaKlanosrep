package com.duyhungapp.kandoo.dao.converter;

import java.text.ParseException;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;

import com.duyhungapp.kandoo.dao.DB;
import com.duyhungapp.kandoo.model.Item;

public class ItemConverter implements IConverter<Item> {

	@Override
	public ContentValues convertModelToContentValues(Item obj) {
		ContentValues cv = new ContentValues();

		cv.put(DB.ITEM_ID, obj.getItemId());
		cv.put(DB.ITEM_NAME, obj.getItemName());
		cv.put(DB.ITEM_STATUS, obj.getItemStatus() + "");
		cv.put(DB.ITEM_CREATEDATE, DB.DB_DATE_FORMAT.format(obj.getItemCreateTime()));
		cv.put(DB.ITEM_DONEDATE, obj.getItemDoneTime() == null ? null : DB.DB_DATE_FORMAT.format(obj.getItemDoneTime()));
		cv.put(DB.TASK_ID, obj.getTask().getTaskId());
		
		return cv;
	}

	@Override
	public Item convertContentValesToModel(ContentValues cv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item convertCursorToModel(Cursor cs) {
		int id = cs.getInt(cs.getColumnIndex(DB.ITEM_ID));
		String name = cs.getString(cs.getColumnIndex(DB.ITEM_NAME));
		String status = cs.getString(cs.getColumnIndex(DB.ITEM_STATUS));
		
		try {
			Date createDate = DB.DB_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(DB.ITEM_CREATEDATE)));
			Date doneDate = cs.getString(cs.getColumnIndex(DB.ITEM_DONEDATE)) != null  ? DB.DB_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(DB.ITEM_DONEDATE))) : null;
		
			return new Item(id, name, Boolean.parseBoolean(status),createDate,doneDate, null);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

}
