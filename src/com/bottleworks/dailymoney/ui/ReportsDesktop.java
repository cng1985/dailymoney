package com.bottleworks.dailymoney.ui;

import android.app.Activity;
import android.content.Intent;

import com.bottleworks.dailymoney.ui.report.BalanceActivity;
import com.iym.dailymoney.Res;
/**
 * 
 * @author dennis
 *
 */
public class ReportsDesktop extends AbstractDesktop {

    public ReportsDesktop(Activity activity) {
        super(activity);
    }

    @Override
    protected void init() {
        label = i18n.string(Res.string.dt_reports);
        icon = Res.drawable.tab_reports;
        
        Intent intent = null;
        
        intent = new Intent(activity, BalanceActivity.class);
        intent.putExtra(BalanceActivity.INTENT_TOTAL_MODE, false);
        intent.putExtra(BalanceActivity.INTENT_MODE, BalanceActivity.MODE_MONTH);
        DesktopItem monthBalance = new DesktopItem(new IntentRun(activity, intent),
                i18n.string(Res.string.dtitem_report_monthly_balance), Res.drawable.dtitem_balance_month);
        addItem(monthBalance);
        
        intent = new Intent(activity, BalanceActivity.class);
        intent.putExtra(BalanceActivity.INTENT_TOTAL_MODE, false);
        intent.putExtra(BalanceActivity.INTENT_MODE, BalanceActivity.MODE_YEAR);
        DesktopItem yearBalance = new DesktopItem(new IntentRun(activity, intent),
                i18n.string(Res.string.dtitem_report_yearly_balance), Res.drawable.dtitem_balance_year);
        addItem(yearBalance);
        
        intent = new Intent(activity, BalanceActivity.class);
        intent.putExtra(BalanceActivity.INTENT_TOTAL_MODE, true);
        intent.putExtra(BalanceActivity.INTENT_MODE, BalanceActivity.MODE_MONTH);
        DesktopItem totalBalance = new DesktopItem(new IntentRun(activity, intent),
                i18n.string(Res.string.dtitem_report_cumulative_balance), Res.drawable.dtitem_balance_cumulative_month,99);
        addItem(totalBalance);
    }

}
