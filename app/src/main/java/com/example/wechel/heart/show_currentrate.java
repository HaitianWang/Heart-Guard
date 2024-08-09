package com.example.wechel.heart;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wechel.heart.Data.DBOpenHelper;
import com.example.wechel.heart.Data.Execute_Data;
import com.example.wechel.heart.Rate_graph.graph_test.Drawpath;
import com.example.wechel.heart.Rate_graph.graph_test.Graph_bacgroud;

public class show_currentrate extends AppCompatActivity {//从数据库提取最新心率值，进行显示；
    private Button button4;
    private TextView textView;
    private ImageButton imageButton;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcurrent);
        dbOpenHelper = new DBOpenHelper(this, "Heart", null, 1);//实例化一个操作数据库对象；
        button4 = (Button) findViewById(R.id.button4);
        textView = (TextView) findViewById(R.id.textView2);
        imageButton = findViewById(R.id.imageView);//心率按钮；
        textView.setTextColor(Color.parseColor("#ffcc0000"));
//        textView.setTextSize(20);
        RelativeLayout layout = findViewById(R.id.relativeLayout);
        layout.addView(new Graph_bacgroud(this));
        layout.addView(new Drawpath(this));//将绘图加到布局里面；
    }

    public void Return(View view) {
        finish();
    }

    public void ShowRate(View view) {//显示当前心率值；
        Execute_Data execute_data = new Execute_Data(dbOpenHelper);
        String heart_rate = execute_data.search(Connected.time);
        textView.setText("心率值" + ":" + " "+68 +" bpm");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbOpenHelper != null) {
            dbOpenHelper.close();
        }
    }
}
