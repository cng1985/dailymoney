/*
 * Copyright (C) 2010 Cyril Mottier (http://www.cyrilmottier.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iym.dailymoney.ui;

import greendroid.app.ActionBarActivity;
import greendroid.graphics.drawable.ActionBarDrawable;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.NormalActionBarItem;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.bottleworks.commons.util.GUIs;
import com.bottleworks.dailymoney.context.Contexts;
import com.bottleworks.dailymoney.context.NewContextsActivity;
import com.bottleworks.dailymoney.data.Account;
import com.bottleworks.dailymoney.data.AccountType;
import com.bottleworks.dailymoney.data.BalanceHelper;
import com.bottleworks.dailymoney.data.Book;
import com.bottleworks.dailymoney.data.DataCreator;
import com.bottleworks.dailymoney.data.Detail;
import com.bottleworks.dailymoney.data.IDataProvider;
import com.bottleworks.dailymoney.data.IMasterDataProvider;
import com.bottleworks.dailymoney.ui.AccountMgntActivity;
import com.bottleworks.dailymoney.ui.BookMgntActivity;
import com.bottleworks.dailymoney.ui.Constants;
import com.bottleworks.dailymoney.ui.DataMaintenanceActivity;
import com.bottleworks.dailymoney.ui.DetailEditorActivity;
import com.bottleworks.dailymoney.ui.DetailListActivity;
import com.bottleworks.dailymoney.ui.PasswordProtectionActivity;
import com.bottleworks.dailymoney.ui.PrefsActivity;
import com.bottleworks.dailymoney.ui.report.BalanceActivity;
import com.iym.dailymoney.R;
import com.iym.dailymoney.Res;
import com.iym.dailymoney.adapter.MainButtonAdapter;

public class QuickActionActivity extends NewContextsActivity {

	private QuickActionWidget mBar;
	private QuickActionWidget mGrid;
	private GridView gridView;
	private String appinfo;
	private static boolean protectionPassed = false;
	private static boolean protectionInfront = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setActionBarContentView(Res.layout.quick_action);

		prepareQuickActionBar();
//		prepareQuickActionGrid();
//		  addActionBarItem(getActionBar()
//	                .newActionBarItem(NormalActionBarItem.class)
//	                .setDrawable(new ActionBarDrawable(this, R.drawable.addd)), R.id.dt_info_weekly_expense);
		addActionBarItem(Type.Add);
		gridView = (GridView) findViewById(R.id.gridView1);
		infoBook = (TextView)findViewById(Res.id.dt_info_book);

		infoWeeklyExpense = (TextView) findViewById(Res.id.dt_info_weekly_expense);
		infoMonthlyExpense = (TextView) findViewById(Res.id.dt_info_monthly_expense);
		infoCumulativeCash = (TextView) findViewById(Res.id.dt_info_cumulative_cash);

		MainButtonAdapter adapter = new MainButtonAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					mBar.show(arg1);
					break;
				case 1:
					mGrid.show(arg1);
					break;
				case 2:
					Intent intent = new Intent(QuickActionActivity.this,
							AccountMgntActivity.class);
					QuickActionActivity.this.startActivityForResult(intent,
							Constants.REQUEST_DETAIL_EDITOR_CODE);

					break;
				case 3:
					Intent intent1 = new Intent(QuickActionActivity.this,
							DataMaintenanceActivity.class);
					QuickActionActivity.this.startActivityForResult(intent1,
							Constants.REQUEST_DETAIL_EDITOR_CODE);
					break;
				case 4:
					Intent intent2 = new Intent(QuickActionActivity.this,
							PrefsActivity.class);
					QuickActionActivity.this.startActivityForResult(intent2,
							Constants.REQUEST_DETAIL_EDITOR_CODE);
					break;
				case 5:
					Intent intent3 = new Intent(QuickActionActivity.this,
							BookMgntActivity.class);
					intent3.putExtra(ActionBarActivity.GD_ACTION_BAR_TITLE,
							"账簿管理");
					QuickActionActivity.this.startActivityForResult(intent3,
							Constants.REQUEST_DETAIL_EDITOR_CODE);
					break;
				default:
					break;
				}

			}
		});
		initialApplicationInfo();
		initPasswordProtection();
		loadInfo();
	}
    @Override
    protected void onResume(){
        super.onResume();
        loadInfo();
        
    }
	private void initialApplicationInfo() {
		appinfo = i18n.string(Res.string.app_name);
		String ver = getContexts().getApplicationVersionName();
		appinfo += " ver : " + ver;

		if (getContexts().isFirstTime()) {
			IDataProvider idp = getContexts().getDataProvider();
			if (idp.listAccount(null).size() == 0) {
				// cause of this function is not ready in previous version, so i
				// check the size for old user
				new DataCreator(idp, i18n).createDefaultAccount();
			}
			GUIs.longToast(this, Res.string.msg_firsttime_use_hint);
		}

	}

	private void initPasswordProtection() {
		// dtLayout.setVisibility(View.INVISIBLE);
		final String password = getContexts().getPrefPassword();
		if ("".equals(password) || protectionPassed) {
			// dtLayout.setVisibility(View.VISIBLE);
			return;
		}
		if (protectionInfront) {
			return;
		}
		Intent intent = null;
		intent = new Intent(this, PasswordProtectionActivity.class);
		startActivityForResult(intent,
				Constants.REQUEST_PASSWORD_PROTECTION_CODE);
		protectionInfront = true;
	}

	public void onShowGrid(View v) {
		mGrid.show(v);
	}

	public void onShowBar(View v) {
		mBar.show(v);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ShowMessageDialog();
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void ShowMessageDialog() {
		AlertDialog.Builder builder = new Builder(QuickActionActivity.this);
		builder.setTitle(R.string.true_calce);
		builder.setPositiveButton(R.string.yes,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						QuickActionActivity.this.finish();
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton(R.string.no,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				});
		builder.create().show();
	}

	private TextView infoWeeklyExpense;
	private TextView infoMonthlyExpense;
	private TextView infoCumulativeCash;
	private TextView infoBook;
	private void loadInfo() {

		IMasterDataProvider imdp = Contexts.instance().getMasterDataProvider();
		Book book = imdp.findBook(Contexts.instance().getWorkingBookId());
		String symbol = book.getSymbol();
		if (symbol == null || "".equals(symbol)) {
			infoBook.setText(book.getName());
		} else {
			 infoBook.setText(book.getName() + " ( " + symbol + " )");
		}

		 infoBook.setVisibility(imdp.listAllBook().size() <= 1 ? TextView.GONE
		 : TextView.VISIBLE);

		Date now = new Date();
		Date start = calHelper.weekStartDate(now);
		Date end = calHelper.weekEndDate(now);
		AccountType type = AccountType.EXPENSE;
		double b = BalanceHelper.calculateBalance(type, start, end).getMoney();
		infoWeeklyExpense.setText(i18n.string(Res.string.label_weekly_expense,
				getContexts().toFormattedMoneyString(b)));

		start = calHelper.monthStartDate(now);
		end = calHelper.monthEndDate(now);
		b = BalanceHelper.calculateBalance(type, start, end).getMoney();
		infoMonthlyExpense.setText(i18n.string(
				Res.string.label_monthly_expense, getContexts()
						.toFormattedMoneyString(b)));

		IDataProvider idp = Contexts.instance().getDataProvider();
		List<Account> acl = idp.listAccount(AccountType.ASSET);
		b = 0;
		for (Account ac : acl) {
			if (ac.isCashAccount()) {
				b += BalanceHelper.calculateBalance(ac, null,
						calHelper.toDayEnd(now)).getMoney();
			}
		}
		infoCumulativeCash.setText(i18n.string(
				Res.string.label_cumulative_cash, getContexts()
						.toFormattedMoneyString(b)));
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {

		switch (position) {
		case 0:
			Detail d = new Detail("", "", new Date(), 0D, "");
			Intent intent = null;
			intent = new Intent(QuickActionActivity.this,
					DetailEditorActivity.class);
			intent.putExtra(DetailEditorActivity.INTENT_MODE_CREATE, true);
			intent.putExtra(DetailEditorActivity.INTENT_DETAIL, d);
			QuickActionActivity.this.startActivityForResult(intent,
					Constants.REQUEST_DETAIL_EDITOR_CODE);
			break;

		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;
	}

	/**
	 * 明细列表
	 */
	private void prepareQuickActionBar() {
		mBar = new QuickActionBar(this);
		mBar.addQuickAction(new MyQuickAction(this,
				Res.drawable.dtitem_detail_day, Res.string.dtitem_detlist_day));
		mBar.addQuickAction(new MyQuickAction(this,
				Res.drawable.dtitem_detail_week, Res.string.dtitem_detlist_week));
		mBar.addQuickAction(new MyQuickAction(this,
				Res.drawable.dtitem_detail_month,
				Res.string.dtitem_detlist_month));
		mBar.addQuickAction(new MyQuickAction(this,
				Res.drawable.dtitem_detail_year, Res.string.dtitem_detlist_year));
		mBar.setOnQuickActionClickListener(mActionListener);
	}

	private void prepareQuickActionGrid() {
		mGrid = new QuickActionBar(this);
		mGrid.addQuickAction(new MyQuickAction(this,
				Res.drawable.dtitem_balance_month,
				Res.string.dtitem_report_monthly_balance));
		mGrid.addQuickAction(new MyQuickAction(this,
				Res.drawable.dtitem_balance_year,
				Res.string.dtitem_report_yearly_balance));
		mGrid.setOnQuickActionClickListener(gActionListener);
	}
    /**
     * 明细监听器
     */
	private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {

			Intent intent = new Intent(QuickActionActivity.this,
					DetailListActivity.class);
			switch (position) {
			case 0:
				intent.putExtra(DetailListActivity.INTENT_MODE,
						DetailListActivity.MODE_DAY);
				break;
			case 1:
				intent.putExtra(DetailListActivity.INTENT_MODE,
						DetailListActivity.MODE_WEEK);
				break;
			case 2:
				intent.putExtra(DetailListActivity.INTENT_MODE,
						DetailListActivity.MODE_MONTH);
				break;
			case 3:
				intent.putExtra(DetailListActivity.INTENT_MODE,
						DetailListActivity.MODE_YEAR);
				break;
			default:
				break;
			}
			QuickActionActivity.this.startActivityForResult(intent,
					Constants.REQUEST_DETAIL_EDITOR_CODE);
		}
	};
	private OnQuickActionClickListener gActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {

			Intent intent = new Intent(QuickActionActivity.this,
					BalanceActivity.class);
			switch (position) {
			case 0:
				/**
				 * 月报表
				 */
				intent.putExtra(BalanceActivity.INTENT_TOTAL_MODE, false);
				intent.putExtra(BalanceActivity.INTENT_MODE,
						BalanceActivity.MODE_MONTH);
				break;
			case 1:
				/**
				 * 年报表
				 */
				intent.putExtra(BalanceActivity.INTENT_TOTAL_MODE, false);
				intent.putExtra(BalanceActivity.INTENT_MODE,
						BalanceActivity.MODE_YEAR);
				break;
			case 2:
				/**
				 * 所有报表
				 */
				intent.putExtra(BalanceActivity.INTENT_TOTAL_MODE, true);
				intent.putExtra(BalanceActivity.INTENT_MODE,
						BalanceActivity.MODE_MONTH);
				break;
			default:
				break;
			}
			QuickActionActivity.this.startActivityForResult(intent,
					Constants.REQUEST_DETAIL_EDITOR_CODE);
		}
	};

	private static class MyQuickAction extends QuickAction {

		private static final ColorFilter BLACK_CF = new LightingColorFilter(
				Color.BLACK, Color.BLACK);

		public MyQuickAction(Context ctx, int drawableId, int titleId) {
			super(ctx, buildDrawable(ctx, drawableId), titleId);
		}

		private static Drawable buildDrawable(Context ctx, int drawableId) {
			Drawable d = ctx.getResources().getDrawable(drawableId);
			// d.setColorFilter(BLACK_CF);
			return d;
		}

	}
}
