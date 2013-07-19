package com.duyhungapp.kandoo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.duyhungapp.kandoo.R;
import com.duyhungapp.kandoo.config.AppConfig;
import com.duyhungapp.kandoo.dao.DB;
import com.duyhungapp.kandoo.dao.TaskDAO;
import com.duyhungapp.kandoo.model.Board;
import com.duyhungapp.kandoo.model.Task;

public class TaskListActivity extends Fragment {

	private TaskDAO taskDAO;
	private ListView listView;
	private int boardId;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_task_list, container, false);

		taskDAO = new TaskDAO(getActivity());

		boardId = getArguments().getInt(DB.BOARD_ID);
		String boardName = getArguments().getString(DB.BOARD_NAME);

		TextView title = (TextView) v.findViewById(R.id.tvTitle);
		title.setText(boardName);

		Cursor cursor = taskDAO.rawQuery("SELECT " + DB.TASK_ID + " as " + DB.ID +
				", " + DB.TASK_NAME +
				", " + DB.TASK_NOTE +
				", " + DB.TASK_CREATEDATE +
				", " + DB.TASK_DONEDATE +
				", " + DB.TASK_DUEDATE +
				", " + DB.TASK_ALARM +
				" FROM " + DB.TABLE_TASK +
				" WHERE " + DB.BOARD_ID + " = " + boardId,
				new String[] {});
		String[] from = new String[] { DB.TASK_NAME, DB.TASK_NOTE };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.two_line_list_item, cursor, from, to);

		listView = (ListView) v.findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClickListener());

		taskDAO.closeDB();
		
		return v;
	}

	private OnItemClickListener onItemClickListener() {
		return new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				AppConfig.CURRENT_BOARD_POSITION = boardId - 1;
				Task task = taskDAO.get(Integer.parseInt(id + ""));
				Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
				intent.putExtra(DB.TABLE_TASK, task);
				startActivityForResult(intent, 1);
			}
		};
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static final TaskListActivity newInstance(Board board) {
		TaskListActivity f = new TaskListActivity();
		Bundle bdl = new Bundle(2);
		bdl.putInt(DB.BOARD_ID, board.getBoardId());
		bdl.putString(DB.BOARD_NAME, board.getBoardName());
		f.setArguments(bdl);
		return f;
	}

	protected String taskDetail(Task task) {
		String dueDate = task.getTaskDueDate() != null ? DB.DB_DATE_FORMAT.format(task.getTaskDueDate()) : "No due date";
		return "Task: " + task.getTaskName() + "\n" +
				"Note: " + task.getTaskNote() + "\n" +
				"Create Date: " + DB.DB_DATE_FORMAT.format(task.getTaskCreateDate()) + "\n" +
				"Due Date: " + dueDate + "\n";
	}
}
