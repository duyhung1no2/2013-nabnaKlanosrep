package com.duyhungapp.kandoo.model;

import java.util.Date;

public class Pomodoro {

    private int _pomoId;
    private Date _pomoStartTime;
    private Date _pomoEndTime;
    private Task _task;

    public Pomodoro() {
    }

    public Pomodoro(int _pomoId, Date _pomoStartTime, Date _pomoEndTime, Task _task) {
        this._pomoId = _pomoId;
        this._pomoStartTime = _pomoStartTime;
        this._pomoEndTime = _pomoEndTime;
        this._task = _task;
    }

    public int getPomoId() {
        return _pomoId;
    }

    public void setPomoId(int _pomoId) {
        this._pomoId = _pomoId;
    }

    public Date getPomoStartTime() {
        return _pomoStartTime;
    }

    public void setPomoStartTime(Date _pomoStartTime) {
        this._pomoStartTime = _pomoStartTime;
    }

    public Date getPomoEndTime() {
        return _pomoEndTime;
    }

    public void setPomoEndTime(Date _pomoEndTime) {
        this._pomoEndTime = _pomoEndTime;
    }

    public Task getTask() {
        return _task;
    }

    public void setTask(Task _task) {
        this._task = _task;
    }
}
