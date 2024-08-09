package com.example.wechel.heart;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechel.heart.Data.DBOpenHelper;
import com.example.wechel.heart.Family.Send_Message;
import com.example.wechel.heart.Video.Save_Heart;

import java.util.UUID;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private ImageButton Open;
    private Button button, button2, button3;
    private BluetoothAdapter Blue;
    private TextView textView;
    private String REMOTE_ADDRESS = "00:0C:BF:09:5E:7C", REMOTE_NAME = "HC-05";
    private BluetoothDevice device;
    public int flag = 0, sum_message = 0;//flag标记按钮点击，sum_messge标记text显示的条目；
    public static final UUID BULETOOTH_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private Connect connect;   //连接线程；
    private Message message;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//显示的布局文件——activity_main；
        Open = (ImageButton) findViewById(R.id.imageButton1);
        button = (Button) findViewById(R.id.button);//显示当前心率；
        button2 = (Button) findViewById(R.id.button2);//心率历史变化；
        button3 = (Button) findViewById(R.id.button3);//心率分析；
        textView = (TextView) findViewById(R.id.textView1);
        Blue = BluetoothAdapter.getDefaultAdapter();
        device = Blue.getRemoteDevice(REMOTE_ADDRESS);
        dbOpenHelper = new DBOpenHelper(MainActivity.this, "Heart", null, 1);
//        device = Blue.getRemoteDevice(REMOTE_ADDRESS);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (sum_message % 3 == 0)
                textView.setText("");
            textView.append((String) msg.obj);
            sum_message++;
        }
    };

    /*
     * 建立蓝牙连接
     * */
    public void setOpen(View view) {
        if (!Blue.isEnabled()) {
            Intent open_bluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(open_bluetooth, 0);
            try {
                sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (flag == 0) {
            //建立连接；
            Toast.makeText(MainActivity.this, "程序正在开启", Toast.LENGTH_SHORT).show();
            String ms = "正在连接腕表..." + "\n";
            message = new Message();
            message.obj = ms;
            handler.sendMessage(message);
//            device = Blue.getRemoteDevice(REMOTE_ADDRESS);
            Open.setImageResource(R.drawable.open2);
//            Open.setImageDrawable(getDrawable())
//            System.out.print(this);
            connect = new Connect(handler, Open, Blue, device, this.getApplicationContext());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connect.start();
            flag = 1;
        } else {
            Blue.disable();
            Message message = new Message();
            message.obj = "程序关闭中...";
            handler.sendMessage(message);
            flag = 0;
            message = new Message();
            message.obj = "程序已关闭\n";
            handler.sendMessage(message);
            Toast.makeText(MainActivity.this, "程序已关闭", Toast.LENGTH_SHORT).show();
            Open.setImageResource(R.drawable.open1);
        }
    }

    public void showNow(View view) {
        Intent intent = new Intent(MainActivity.this, show_currentrate.class);
        startActivity(intent);
    }

    public void showhistory(View view) {
        Intent intent = new Intent(MainActivity.this, Show_history.class);
        startActivity(intent);
    }

    public void showfuture(View view) {
        Intent intent = new Intent(MainActivity.this, future.class);
        startActivity(intent);
    }

    public Context return_Context() { //给connected线程传递Context对象；这是我写的 返回一个context
        Context context = getApplicationContext();
        return context;
    }

    public void family(View view) {
        Intent intent = new Intent(MainActivity.this, Send_Message.class);
        startActivity(intent);
    }

    public void save(View view) {
        Intent intent = new Intent(MainActivity.this, Save_Heart.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbOpenHelper != null) {
            dbOpenHelper.close();
        }
    }

}
