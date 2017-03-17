package com.heyl.recycleviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class ChartViewActivity extends Activity {

    private RelativeLayout activityChartView;
    private ChartView chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        chart = new ChartView(this);
        initView();
    }

    private void initView() {
        activityChartView = (RelativeLayout) findViewById(R.id.activity_chart_view);
        activityChartView.removeAllViews();
        chart.setInfo(7, new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" } // X轴刻度
                ,new String[]{"150","60","70","80","130","100","120"}
                );
        activityChartView.addView(chart);
    }
}
