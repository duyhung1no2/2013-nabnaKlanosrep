package com.duyhungapp.kandoo.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.duyhungapp.kandoo.R;
import com.duyhungapp.kandoo.dao.BoardDAO;
import com.duyhungapp.kandoo.dao.DB;
import com.duyhungapp.kandoo.dao.ItemDAO;
import com.duyhungapp.kandoo.dao.TaskDAO;
import com.duyhungapp.kandoo.model.Board;
import com.duyhungapp.kandoo.model.Item;
import com.duyhungapp.kandoo.model.Task;

public class TaskAddActivity extends Activity {

	private int dueYear, dueMonth, dueDay, dueHour, dueMinute;
	private String currentDueDate, currentDueTime;
	private List<Item> items;
	private Task task;

	private BoardDAO boardDAO;
	private TaskDAO taskDAO;
	private ItemDAO itemDAO;

	private Spinner spinBoard;
	private EditText editName, editNote,
			editItem;
	private Button btDueDate, btDueTime;
	private ImageButton btDueDateCancel, btDueTimeCancel;
	private LinearLayout listItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_add);

		boardDAO = new BoardDAO(this);
		taskDAO = new TaskDAO(this);
		itemDAO = new ItemDAO(this);
		task = new Task();

		spinBoard = (Spinner) findViewById(R.id.spinBoard);
		editName = (EditText) findViewById(R.id.editTaskName);
		editNote = (EditText) findViewById(R.id.editTaskNote);
		//editItem = (EditText) findViewById(R.id.editTaskItem);
		btDueDate = (Button) findViewById(R.id.btDueDate);
		btDueTime = (Button) findViewById(R.id.btDueTime);
		btDueDateCancel = (ImageButton) findViewById(R.id.btDueDateCancel);
		btDueTimeCancel = (ImageButton) findViewById(R.id.btDueTimeCancel);
		//listItem = (LinearLayout) findViewById(R.id.listItem);

		items = new ArrayList<Item>();
		setSpinerBoard();
		btDueDate.setOnClickListener(getDueDateListener());
		btDueTime.setOnClickListener(getDueTimeListener());
		btDueDateCancel.setOnClickListener(cancelDueDate());
		btDueTimeCancel.setOnClickListener(cancelDueTime());

		//editItem.setOnKeyListener(onKeyListener);
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
			insertTask();
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
		spinBoard.setAdapter(new ArrayAdapter<String>(TaskAddActivity.this, android.R.layout.simple_spinner_dropdown_item, boards));
	}

	OnKeyListener onKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == 66) {
				Item item = new Item();
				item.setItemId(0);
				item.setItemName(editItem.getText() + "");
				item.setItemStatus(false);
				item.setItemCreateTime(new Date());
				item.setItemDoneTime(null);
				item.setTask(null);

				if (!item.getItemName().equals(""))
					items.add(item);

				updateCheckList();
				editItem.setText("");
			}
			return true;
		}
	};

	private void updateCheckList() {
		listItem.removeAllViews();
		for (Item item : items) {
			CheckBox cb = new CheckBox(getApplicationContext());
			cb.setText(item.getItemName());
			cb.setTextColor(getResources().getColor(android.R.color.black));
			cb.setChecked(item.getItemStatus());

			listItem.addView(cb);
		}
	}

	void enableButtonSetDueTime() {
		if (dueYear != 0)
			btDueTime.setEnabled(true);
		else
			btDueTime.setEnabled(false);
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
				DatePickerDialog dialog = new DatePickerDialog(TaskAddActivity.this, dueDateSetListener, dueYear, dueMonth, dueDay);
				dialog.setTitle(R.string.set_date);
				dialog.show();
				updateDueDateDisplay();
				// if(dueHour==0){
				// dueHour = 6;
				// dueMinute = 0;
				// btDueTime.setText("Default 06:00");
				// }
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
				new TimePickerDialog(TaskAddActivity.this, dueTimeSetListener, dueHour, dueMinute, DateFormat.is24HourFormat(TaskAddActivity.this)).show();

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
	 * INSERT
	 */
	private void insertTask() {
		task.setTaskId(taskDAO.getNextId());
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

		task.setTaskCreateDate(new Date());
		task.setTaskDoneDate(new Date());

		taskDAO.insert(task);
		insertItem();
	}

	private void insertItem() {
		for (Item i : items) {
			i.setTask(task);
			itemDAO.insert(i);
		}
	}

}
