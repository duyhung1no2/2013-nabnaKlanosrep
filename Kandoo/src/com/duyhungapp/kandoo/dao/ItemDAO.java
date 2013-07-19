package com.duyhungapp.kandoo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.duyhungapp.kandoo.dao.converter.ItemConverter;
import com.duyhungapp.kandoo.model.Item;

public class ItemDAO extends DatabaseDAO<Item> {

	Context mContext;

	public ItemDAO(Context context) {
		super(context);
		mContext = context;
		converter = new ItemConverter();
	}

	@Override
	public List<Item> getAll() {
		openDB();
		List<Item> items = new ArrayList<Item>();

		Cursor cursor = db.query(true, DB.TABLE_ITEM, new String[] {}, null, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Item i = converter.convertCursorToModel(cursor);
			i.setTask(new TaskDAO(mContext).get(cursor.getInt(cursor.getColumnIndex(DB.TASK_ID))));
			items.add(i);
		}

		closeDB();
		return items;
	}
	
	public List<Item> getAllByTaskId(int taskId){
		openDB();
		List<Item> items = new ArrayList<Item>();

		Cursor cursor = db.query(true, DB.TABLE_ITEM, new String[] {}, DB.TASK_ID + " = " + taskId, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Item i = converter.convertCursorToModel(cursor);
			i.setTask(new TaskDAO(mContext).get(cursor.getInt(cursor.getColumnIndex(DB.TASK_ID))));
			items.add(i);
		}

		closeDB();
		return items;
	}

	@Override
	public Item get(int id) {
		openDB();
		Item item = null;

		Cursor cursor = db.query(true, DB.TABLE_ITEM, new String[] {}, DB.ITEM_ID + " = " + id, null, null, null, null, null);

		cursor.moveToFirst();
		item = converter.convertCursorToModel(cursor);
		item.setTask(new TaskDAO(mContext).get(cursor.getInt(cursor.getColumnIndex(DB.TASK_ID))));

		closeDB();
		return item;
	}

	@Override
	public void insert(Item obj) {
		openDB();
		String insertStm = "INSERT INTO " + DB.TABLE_ITEM + "(" +
				DB.ITEM_ID + ", " +
				DB.ITEM_NAME + ", " +
				DB.ITEM_STATUS + ", " +
				DB.ITEM_CREATEDATE + ", " +
				DB.ITEM_DONEDATE + ", " +
				DB.TASK_ID +
				") VALUES(?, ?, ?, ?, ?, ?)";
		SQLiteStatement stm = db.compileStatement(insertStm);
		stm.clearBindings();
		stm.bindNull(1);
		stm.bindString(2, obj.getItemName());
		stm.bindString(3, obj.getItemStatus() + "");
		stm.bindString(4, DB.DB_DATE_FORMAT.format(obj.getItemCreateTime()));

		if (obj.getItemDoneTime() != null)
			stm.bindString(5, DB.DB_DATE_FORMAT.format(obj.getItemDoneTime()));
		else
			stm.bindNull(5);

		stm.bindLong(6, obj.getTask().getTaskId());

		stm.execute();
		closeDB();
	}

	@Override
	public void update(Item obj) {
		openDB();
		ContentValues cv = converter.convertModelToContentValues(obj);
		db.update(DB.TABLE_ITEM, cv, DB.ITEM_ID + " = " + obj.getItemId(), null);
		closeDB();
	}

	@Override
	public void delete(Item obj) {
		openDB();
		db.delete(DB.TABLE_ITEM, DB.ITEM_ID + " = " + obj.getItemId(), null);
		closeDB();
	}

}
