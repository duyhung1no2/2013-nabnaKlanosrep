package com.duyhungapp.kandoo.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.duyhungapp.kandoo.R;
import com.duyhungapp.kandoo.dao.BoardDAO;
import com.duyhungapp.kandoo.dao.DB;
import com.duyhungapp.kandoo.dao.TaskDAO;
import com.duyhungapp.kandoo.model.Board;
import com.duyhungapp.kandoo.model.Task;

public class TaskEditActivity extends Activity {

	private Task task;

	private int dueYear, dueMonth, dueDay, dueHour, dueMinute;

	private String currentDueDate, currentDueTime;

	private BoardDAO boardDAO;
	private TaskDAO taskDAO;

	private Spinner spinBoard;
	private EditText editName, editNote;
	private Button btDueDate, btDueTime;
	private ImageButton btDueDateCancel, btDueTimeCancel;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_add);

		task = (Task) getIntent().getSerializableExtra(DB.TABLE_TASK);

		boardDAO = new BoardDAO(this);
		taskDAO = new TaskDAO(this);

		spinBoard = (Spinner) findViewById(R.id.spinBoard);
		editName = (EditText) findViewById(R.id.editTaskName);
		editNote = (EditText) findViewById(R.id.editTaskNote);
		btDueDate = (Button) findViewById(R.id.btDueDate);
		btDueTime = (Button) findViewById(R.id.btDueTime);
		btDueDateCancel = (ImageButton) findViewById(R.id.btDueDateCancel);
		btDueTimeCancel = (ImageButton) findViewById(R.id.btDueTimeCancel);

		setSpinerBoard();
		btDueDate.setOnClickListener(getDueDateListener());
		btDueTime.setOnClickListener(getDueTimeListener());
		btDueDateCancel.setOnClickListener(cancelDueDate());
		btDueTimeCancel.setOnClickListener(cancelDueTime());

		// SET CONTENT
		spinBoard.setSelection(task.getBoard().getBoardId() - 1);
		editName.setText(task.getTaskName());
		editNote.setText(task.getTaskNote());

		Date dueDate = task.getTaskDueDate();
		if (dueDate != null) {
			dueYear = dueDate.getYear() + 1900;
			dueMonth = dueDate.getMonth();
			dueDay = dueDate.getDate();
			dueHour = dueDate.getHours();
			dueMinute = dueDate.getMinutes();
			updateDueDateDisplay();
			updateDueTimeDisplay();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.task_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.action_task_add_ok:
			updateTask();
			Intent intent = getIntent();
			intent.removeExtra(DB.TABLE_TASK);
			intent.putExtra(DB.TABLE_TASK, task);
			finish();
			break;

		case R.id.action_task_add_cancel:
			finish();
			break;

		default:
			break;
		}

		return true;
	}

	public void setSpinerBoard() {
		ArrayList<String> boards = new ArrayList<String>();
		for (Board b : boardDAO.getAll()) {
			boards.add(b.getBoardName());
		}
		spinBoard.setAdapter(new ArrayAdapter<String>(TaskEditActivity.this, android.R.layout.simple_spinner_dropdown_item, boards));
	}

	/**
	 * GET DATE
	 */
	private OnDateSetListener dueDateSetListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int i, int j, int k) {
			dueYear = i;
			dueMonth = j;
			dueDay = k;
			updateDueDateDisplay();
		}
	};

	private OnClickListener getDueDateListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				if (dueYear != 0)
					c.set(dueYear, dueMonth, dueDay);
				dueYear = c.get(Calendar.YEAR);
				dueMonth = c.get(Calendar.MONTH);
				dueDay = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dialog = new DatePickerDialog(TaskEditActivity.this, dueDateSetListener, dueYear, dueMonth, dueDay);
				dialog.setTitle(R.string.set_date);
				dialog.show();
				updateDueDateDisplay();
			}
		};
	}

	private void updateDueDateDisplay() {
		if (dueYear != 0) {
			String day = String.valueOf(dueDay).length() < 2 ? "0" + dueDay : dueDay + "";
			dueMonth++;
			String month = String.valueOf(dueMonth).length() < 2 ? "0" + dueMonth : dueMonth + "";
			dueMonth--;
			currentDueDate = new StringBuilder().append(day).append("-")
					.append(month).append("-").append(dueYear).toString();
			btDueDate.setText(currentDueDate);
		} else {
			btDueDate.setText(R.string.set_date);
		}

	}

	/**
	 * GET TIME
	 */
	private OnTimeSetListener dueTimeSetListener = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hour, int minute) {
			dueHour = hour;
			dueMinute = minute;
			updateDueTimeDisplay();
		}
	};

	private OnClickListener getDueTimeListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				if (dueHour != 0)
					c.set(dueYear, dueMonth, dueDay, dueHour, dueMinute);
				else
					c.set(dueYear, dueMonth, dueDay, 6, 0);
				dueHour = c.get(Calendar.HOUR);
				dueMinute = c.get(Calendar.MINUTE);
				new TimePickerDialog(TaskEditActivity.this, dueTimeSetListener, dueHour, dueMinute, DateFormat.is24HourFormat(TaskEditActivity.this)).show();

				updateDueTimeDisplay();
			}
		};
	}

	private void updateDueTimeDisplay() {
		if (dueHour != 0) {
			String hour = String.valueOf(dueHour).length() < 2 ? "0" + dueHour : dueHour + "";
			String minute = String.valueOf(dueMinute).length() < 2 ? "0" + dueMinute : dueMinute + "";
			currentDueTime = new StringBuilder().append(hour).append(":").append(minute).toString();
			btDueTime.setText(currentDueTime);
		} else {
			btDueTime.setText(R.string.set_time);
		}
	}

	/**
	 * CANCEL DATE TIME
	 */
	private OnClickListener cancelDueDate() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dueYear = dueMonth = dueDay = dueHour = dueMinute = 0;
				updateDueDateDisplay();
				updateDueTimeDisplay();
			}
		};
	}

	private OnClickListener cancelDueTime() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dueHour = dueMinute = 0;
				updateDueTimeDisplay();
			}
		};
	}

	/**
	 * UPDATE
	 */
	public void updateTask() {
		task.setTaskName(editName.getText().toString());
		task.setTaskNote(editNote.getText().toString());
		task.setBoard(boardDAO.get(spinBoard.getSelectedItemPosition() + 1));

		if (dueYear != 0) {
			String dueDateString = currentDueDate + " " + currentDueTime;

			try {
				Date dueDate = DB.DISPLAY_DATE_FORMAT.parse(dueDateString);

				task.setTaskDueDate(dueDate);
				task.setTaskAlarm(dueDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else{
			task.setTaskDueDate(null);
		}

		taskDAO.update(task);
	}
}
