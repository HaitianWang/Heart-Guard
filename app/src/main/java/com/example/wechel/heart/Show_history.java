package com.example.wechel.heart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wechel.heart.Data.Get_value;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Show_history extends AppCompatActivity {  //绘制心率变化图；
    private LineChart lineChart;
    private int[] history_rate;
    Get_value get_value = new Get_value();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        lineChart = findViewById(R.id.lineChart);
        /*数据来源*/
        history_rate = get_value.value;
        List<Entry> entries = loadData();//获取数据
        LineDataSet dataSet = new LineDataSet(entries, "历史心率");
        setDataset(dataSet);//设置数据集;
        LineData lineData = new LineData(dataSet);
        setXYAxis(lineChart);
        lineChart.setData(lineData);//装载数据；
//        setChartDragMode(lineChart);
        description(lineChart);
        lineChart.invalidate();//刷新UI绘制；
        lineChart.animateX(3000);
    }

    private void setXYAxis(LineChart lineChart) {
        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setDrawLabels(false);
        axisRight.setDrawGridLines(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(true); //可以拖拽
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis axisleft = lineChart.getAxisLeft();
        axisleft.setDrawGridLines(true);
        axisleft.setAxisMinimum(0f);
        axisleft.setAxisMaximum(120f);
    }

    private void setDataset(LineDataSet dataset) {
        dataset.setMode(LineDataSet.Mode.LINEAR);
        dataset.setDrawValues(true);//绘制数据；
        dataset.setDrawCircles(true);
        dataset.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        dataset.setHighLightColor(Color.BLACK);
        dataset.setHighlightEnabled(true);
        dataset.setHighlightLineWidth(1f);
        dataset.setDrawVerticalHighlightIndicator(true);//绘制垂直高亮;
        dataset.setDrawHorizontalHighlightIndicator(false);//不绘制水平高亮;
        dataset.setLineWidth(2.0f);
    }

    private List<Entry> loadData() {
        List<Entry> entries = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            entries.add(new Entry(i, history_rate[i-1]));
    }
        return entries;
    }
    private void description(LineChart chart){
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);
    }

    public void Return(View view) {
        finish();
    }
}
