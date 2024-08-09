package com.example.wechel.heart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wechel.heart.Data.Future_Data;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class future extends AppCompatActivity {
    ArrayList<Entry> entries;  //坐标集合;
    int[] HeartNum = new int[18];
    int hour, minute;   //当前系统时间;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);
        LineChart chart = findViewById(R.id.Future_Chart);
        entries = new ArrayList<>();
        loadData();
        LineDataSet dataSet = new LineDataSet(entries, "未来心率变化");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.GREEN);
        dataSet.setLineWidth(1f);
        dataSet.setValueTextColor(Color.RED);
        FormatizeXais(chart);  //X轴标签设置;
        description(chart);  //隐藏X轴描述;
        XYAisSet(chart);    //X、Y轴轴线设置;
        finger(chart);      //摩擦系数;
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();  //刷新UI；
    }

    private void XYAisSet(LineChart chart) {
        XAxis xAxis = chart.getXAxis();
        YAxis yAxisLeft = chart.getAxisLeft();
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);  //禁用右侧Y轴;
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxisLeft.setTextColor(Color.BLUE);
        yAxisLeft.setTextSize(10f);
        yAxisLeft.setAxisLineColor(Color.RED);
        yAxisLeft.setAxisLineWidth(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setTextSize(10f);
        xAxis.setAxisLineColor(Color.RED);
        xAxis.setAxisLineWidth(1f);
    }

    private void loadData() {
        Future_Data future_data = new Future_Data();
        HeartNum = future_data.sendData();  //获取到预测数据;
        for (int i = 0; i < 18; i++) {
            entries.add(new Entry(i, HeartNum[i]));
        }
    }

    private void FormatizeXais(LineChart chart) {
        List<String> list = new ArrayList<>();
        Gettime();
        String time;
        minute += 10;  //预测下一时刻；
        for (int i = 0; i < 18; i++) {
            if (minute >= 60) {
                hour += 1;
                minute %= 60;
            }
            if (hour >= 24) {
                hour %= 24;
            }
            if (minute / 10 == 0) {
                time = String.valueOf(hour) + ":0" + minute;
            } else {
                time = String.valueOf(hour) + ":" + minute;
            }
            list.add(time);
            Log.e("Time", time);
            minute += 10;
        }
        for (String date : list) {
            Log.e("", date);
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
        /*chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value == 24) {
                    return String.valueOf(0).concat(":00");
                } else {
                    return String.valueOf((int) value).concat(":00");
                }
            }
        });*/
    }

    /*
     * 设置摩擦系数
     * */
    private void finger(LineChart chart) {
        chart.setDragDecelerationEnabled(true);
        chart.setDragDecelerationFrictionCoef(1);

    }

    private void description(LineChart chart) {
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
    }

    /*
     * 获取系统时间
     * */
    private void Gettime() {
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void Return(View view) {
        finish();
    }
}
