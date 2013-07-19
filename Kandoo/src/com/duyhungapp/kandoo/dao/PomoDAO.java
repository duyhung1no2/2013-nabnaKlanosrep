package com.duyhungapp.kandoo.dao;

import java.util.List;

import android.content.Context;

import com.duyhungapp.kandoo.dao.converter.PomoConverter;
import com.duyhungapp.kandoo.model.Pomodoro;

public class PomoDAO extends DatabaseDAO<Pomodoro> {

	public PomoDAO(Context context) {
		super(context);
		converter = new PomoConverter();
	}

	@Override
	public List<Pomodoro> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pomodoro get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Pomodoro obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Pomodoro obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Pomodoro obj) {
		// TODO Auto-generated method stub
		
	}

}
