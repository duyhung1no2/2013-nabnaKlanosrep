package com.duyhungapp.kandoo.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.duyhungapp.kandoo.R;
import com.duyhungapp.kandoo.dao.BoardDAO;
import com.duyhungapp.kandoo.dao.DB;
import com.duyhungapp.kandoo.dao.ItemDAO;
import com.duyhungapp.kandoo.dao.TaskDAO;
import com.duyhungapp.kandoo.model.Board;
import com.duyhungapp.kandoo.model.Item;
import com.duyhungapp.kandoo.model.Task;

public class TaskDetailActivity extends Activity {
	
	private TextView tvTaskName, tvTaskNote, tvTaskCreateDate, tvTaskDueDate;
	private Spinner spinBoard;
//	private LinearLayout listItem;

	private Task task;
	private TaskDAO taskDAO;
	private BoardDAO boardDAO;
	private ItemDAO itemDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);

		taskDAO = new TaskDAO(getApplicationContext());
		boardDAO = new BoardDAO(getApplicationContext());
		itemDAO = new ItemDAO(getApplicationContext());

		spinBoard = (Spinner) findViewById(R.id.spinBoard);
		tvTaskName = (TextView) findViewById(R.id.tvTaskName);
		tvTaskNote = (TextView) findViewById(R.id.tvTaskNote);
		tvTaskCreateDate = (TextView) findViewById(R.id.tvTaskCreateDate);
		tvTaskDueDate = (TextView) findViewById(R.id.tvTaskDueDate);
		//listItem = (LinearLayout) findViewById(R.id.listItem);

		task = (Task) getIntent().getSerializableExtra(DB.TABLE_TASK);
		tvTaskName.setText(task.getTaskName());
		tvTaskNote.setText(task.getTaskNote());
		tvTaskCreateDate.setText("Create: " + DB.DISPLAY_DATE_FORMAT.format(task.getTaskCreateDate()));
		if (task.getTaskDueDate() != null)
			tvTaskDueDate.setText("Due: " + DB.DISPLAY_DATE_FORMAT.format(task.getTaskDueDate()));
		else
			tvTaskDueDate.setText("No due date");

		setSpinerBoard();
		spinBoard.setSelection(task.getBoard().getBoardId() - 1);
		spinBoard.setOnItemSelectedListener(onSpinnerBoardItemChanged);

		//updateCheckList();

	}

	OnCheckedChangeListener onItemCheckedChanged = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			Item item = itemDAO.get(buttonView.getId());
			item.setItemStatus(buttonView.isChecked());
			itemDAO.update(item);
			Log.i("CHECKBOX ID", buttonView.getId()+"");
		}
	};

	OnItemSelectedListener onSpinnerBoardItemChanged = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Board board = boardDAO.get((spinBoard.getSelectedItemPosition() + 1));
			task.setBoard(board);
			taskDAO.update(task);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.task_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.task_edit:
			Intent intent = new Intent(getApplicationContext(), TaskEditActivity.class);
			intent.putExtra(DB.TABLE_TASK, task);
			startActivityForResult(intent, 0);
			break;

		case R.id.task_delete:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Confirm delete");
			dialog.setMessage("Are you sure you want delete this?");
			dialog.setIcon(android.R.drawable.ic_delete);

			dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					taskDAO.delete(task);
					finish();
				}
			});

			dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

			dialog.show();

			break;

		default:
			finish();
			break;
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		task = taskDAO.get(task.getTaskId());
		tvTaskName.setText(task.getTaskName());
		tvTaskNote.setText(task.getTaskNote());
		tvTaskCreateDate.setText("Create: " + DB.DISPLAY_DATE_FORMAT.format(task.getTaskCreateDate()));
		if (task.getTaskDueDate() != null)
			tvTaskDueDate.setText("Due: " + DB.DISPLAY_DATE_FORMAT.format(task.getTaskDueDate()));
		else
			tvTaskDueDate.setText("No due date");

		setSpinerBoard();
		spinBoard.setSelection(task.getBoard().getBoardId() - 1);
		spinBoard.setOnItemSelectedListener(onSpinnerBoardItemChanged);
	}

	public void setSpinerBoard() {
		ArrayList<String> boards = new ArrayList<String>();
		for (Board b : boardDAO.getAll()) {
			boards.add(b.getBoardName());
		}
		spinBoard.setAdapter(new ArrayAdapter<String>(TaskDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, boards));
	}

//	private void updateCheckList() {
//		listItem.removeAllViews();
//		for (Item item : itemDAO.getAllByTaskId(task.getTaskId())) {
//			CheckBox cb = new CheckBox(getApplicationContext());
//			cb.setId(item.getItemId());
//			cb.setText(item.getItemId() + item.getItemName());
//			cb.setTextColor(getResources().getColor(android.R.color.black));
//			cb.setChecked(item.getItemStatus());
//			cb.setOnCheckedChangeListener(onItemCheckedChanged);
//			listItem.addView(cb);
//		}
//	}
	
}
