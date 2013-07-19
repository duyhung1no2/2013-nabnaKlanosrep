package com.duyhungapp.kandoo.model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _taskId;
    private String _taskName;
    private String _taskNote;
    private Date _taskCreateDate;
    private Date _taskDoneDate;
    private Date _taskDueDate;
    private Date _taskAlarm;
    private Board _board;

    public Task() {
    }
    
    public Task(String _taskName, String _taskNote, Date _taskCreateDate, Date _taskDoneDate, Date _taskDueDate, Date _taskAlarm) {
        this._taskName = _taskName;
        this._taskNote = _taskNote;
        this._taskCreateDate = _taskCreateDate;
        this._taskDoneDate = _taskDoneDate;
        this._taskDueDate = _taskDueDate;
        this._taskAlarm = _taskAlarm;
    }

    public Task(int _taskId, String _taskName, String _taskNote, Date _taskCreateDate, Date _taskDoneDate, Date _taskDueDate, Date _taskAlarm) {
        this._taskId = _taskId;
        this._taskName = _taskName;
        this._taskNote = _taskNote;
        this._taskCreateDate = _taskCreateDate;
        this._taskDoneDate = _taskDoneDate;
        this._taskDueDate = _taskDueDate;
        this._taskAlarm = _taskAlarm;
    }
    
    public Task(String _taskName, String _taskNote, Date _taskCreateDate, Date _taskDoneDate, Date _taskDueDate, Date _taskAlarm, Board _board) {
    	this._taskName = _taskName;
    	this._taskNote = _taskNote;
    	this._taskCreateDate = _taskCreateDate;
    	this._taskDoneDate = _taskDoneDate;
    	this._taskDueDate = _taskDueDate;
    	this._taskAlarm = _taskAlarm;
    	this._board = _board;
    }
    
    public Task(int _taskId, String _taskName, String _taskNote, Date _taskCreateDate, Date _taskDoneDate, Date _taskDueDate, Date _taskAlarm, Board _board) {
    	this._taskId = _taskId;
    	this._taskName = _taskName;
    	this._taskNote = _taskNote;
    	this._taskCreateDate = _taskCreateDate;
    	this._taskDoneDate = _taskDoneDate;
    	this._taskDueDate = _taskDueDate;
    	this._taskAlarm = _taskAlarm;
    	this._board = _board;
    }

    public int getTaskId() {
        return _taskId;
    }

    public void setTaskId(int _taskId) {
        this._taskId = _taskId;
    }

    public String getTaskName() {
        return _taskName;
    }

    public void setTaskName(String _taskName) {
        this._taskName = _taskName;
    }

    public String getTaskNote() {
        return _taskNote;
    }

    public void setTaskNote(String _taskNote) {
        this._taskNote = _taskNote;
    }

    public Date getTaskCreateDate() {
        return _taskCreateDate;
    }

    public void setTaskCreateDate(Date _taskCreateDate) {
        this._taskCreateDate = _taskCreateDate;
    }

    public Date getTaskDoneDate() {
        return _taskDoneDate;
    }

    public void setTaskDoneDate(Date _taskDoneDate) {
        this._taskDoneDate = _taskDoneDate;
    }

    public Date getTaskDueDate() {
        return _taskDueDate;
    }

    public void setTaskDueDate(Date _taskDueDate) {
        this._taskDueDate = _taskDueDate;
    }

    public Date getTaskAlarm() {
        return _taskAlarm;
    }

    public void setTaskAlarm(Date _tastAlarm) {
        this._taskAlarm = _tastAlarm;
    }

    public Board getBoard() {
        return _board;
    }

    public void setBoard(Board _board) {
        this._board = _board;
    }
    
    @Override
    public String toString() {
    	return this.getTaskName();
    }
    
}
