package com.iym.dailymoney.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.iym.dailymoney.Res;
import com.iym.dailymoney.widget.MainButtonWidget;

public class MainButtonAdapter extends BaseAdapter {
	Context context;
	private List<MainButtonWidget> bts;

	public MainButtonAdapter(Context c) {
		context = c;
		bts = new ArrayList<MainButtonWidget>();
		MainButtonWidget bt = null;
		bt = new MainButtonWidget(context);
		bt.setImageResourceId(Res.drawable.v01);
		bt.setText("明细列表");
		bts.add(bt);
		bt = new MainButtonWidget(context);
		bt.setImageResourceId(Res.drawable.v02);
		bt.setText("财务报表");
		bts.add(bt);
		bt = new MainButtonWidget(context);
		bt.setImageResourceId(Res.drawable.v03);
		bt.setText("账户管理");
		bts.add(bt);
		bt = new MainButtonWidget(context);
		bt.setImageResourceId(Res.drawable.v04);
		bt.setText("资料维护");
		bts.add(bt);
		bt = new MainButtonWidget(context);
		bt.setImageResourceId(Res.drawable.v05);
		bt.setText("喜好设定");
		bts.add(bt);
		bt = new MainButtonWidget(context);
		bt.setImageResourceId(Res.drawable.v06);
		bt.setText("账本管理");
		bts.add(bt);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bts.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return bts.get(arg0);
	}

}
