package com.duyhungapp.kandoo;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.duyhungapp.kandoo.activity.TaskAddActivity;
import com.duyhungapp.kandoo.activity.TaskListActivity;
import com.duyhungapp.kandoo.activity.adapter.TaskListPagerAdapter;
import com.duyhungapp.kandoo.dao.BoardDAO;
import com.duyhungapp.kandoo.model.Board;

public class MainActivity extends FragmentActivity {

	BoardDAO boardDAO;
	TaskListPagerAdapter pagerAdapter;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		boardDAO = new BoardDAO(this);
		pagerAdapter = new TaskListPagerAdapter(getSupportFragmentManager(), getFragments());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(pagerAdapter);
	}

	private List<Fragment> getFragments() {
		List<Fragment> fragments = new ArrayList<Fragment>();

		for (Board b : boardDAO.getAll()) {
			fragments.add(TaskListActivity.newInstance(b));
		}
		
		return fragments;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {

		case R.id.action_add_task:
			Intent intent = new Intent(this, TaskAddActivity.class);
			startActivityForResult(intent, 0);
			break;
			
//		case R.id.action_edit_boards:
//			startActivityForResult(new Intent(this, BoardManageActivity.class), 0);
//			break;

		default:
			break;
		}

		return true;
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		pagerAdapter = new TaskListPagerAdapter(getSupportFragmentManager(), getFragments());
		pager.setAdapter(pagerAdapter);
	}

}
