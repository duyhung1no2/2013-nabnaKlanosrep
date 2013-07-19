package com.duyhungapp.kandoo.model;

import java.util.Date;

public class Item {

    private int _itemId;
    private String _itemName;
    private boolean _itemStatus;
    private Date _itemCreateTime;
    private Date _itemDoneTime;
    private Task _task;

    public Item() {
    }

    public Item(int _itemId, String _itemName, boolean _itemStatus, Date _itemCreateTime, Date _itemDoneTime, Task _task) {
        this._itemId = _itemId;
        this._itemName = _itemName;
        this._itemStatus = _itemStatus;
        this._itemCreateTime = _itemCreateTime;
        this._itemDoneTime = _itemDoneTime;
        this._task = _task;
    }

    public int getItemId() {
        return _itemId;
    }

    public void setItemId(int _itemId) {
        this._itemId = _itemId;
    }

    public String getItemName() {
        return _itemName;
    }

    public void setItemName(String _itemName) {
        this._itemName = _itemName;
    }

    public boolean getItemStatus() {
        return _itemStatus;
    }

    public void setItemStatus(boolean _itemStatus) {
        this._itemStatus = _itemStatus;
    }

    public Date getItemCreateTime() {
        return _itemCreateTime;
    }

    public void setItemCreateTime(Date _itemCreateTime) {
        this._itemCreateTime = _itemCreateTime;
    }

    public Date getItemDoneTime() {
        return _itemDoneTime;
    }

    public void setItemDoneTime(Date _itemDoneTime) {
        this._itemDoneTime = _itemDoneTime;
    }

    public Task getTask() {
        return _task;
    }

    public void setTask(Task _task) {
        this._task = _task;
    }
}
