package com.duyhungapp.kandoo.activity.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TaskListPagerAdapter extends FragmentPagerAdapter{
	
	List<Fragment> frags;

	public TaskListPagerAdapter(FragmentManager fm, List<Fragment> frags) {
		super(fm);
		this.frags = frags;
	}

	@Override
	public Fragment getItem(int position) {
		return this.frags.get(position);	
	}

	@Override
	public int getCount() {
		return this.frags.size();
	}
	
}
