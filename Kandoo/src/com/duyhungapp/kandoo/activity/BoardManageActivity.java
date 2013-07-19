package com.duyhungapp.kandoo.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.duyhungapp.kandoo.R;
import com.duyhungapp.kandoo.dao.BoardDAO;
import com.duyhungapp.kandoo.model.Board;

public class BoardManageActivity extends Activity {

	private BoardDAO boardDAO;
	
	//private Menu mMenu;

	private ListView boardList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board_manage);

		boardDAO = new BoardDAO(this);

		boardList = (ListView) findViewById(R.id.boardList);
		loadBoardList();
		boardList.setOnItemClickListener(onBoardItemClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.board_manage, menu);
		//mMenu = menu;
		return true;
	}

	private void loadBoardList() {
		List<Board> boards = boardDAO.getAll();
		boardList.setAdapter(new ArrayAdapter<Board>(getApplicationContext(), android.R.layout.simple_list_item_1, boards));
	}

	OnItemClickListener onBoardItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
			Log.i("BOARD ID", id+"");
		}
	};
	

}
