package com.example.wechel.heart;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.wechel.heart.Data.DBOpenHelper;
import com.example.wechel.heart.Data.Execute_Data;
import com.example.wechel.heart.Data.Future_Data;
import com.example.wechel.heart.Data.Get_value;
import com.example.wechel.heart.Family.Send_Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connected extends Thread {
    private static BluetoothSocket socket;
    private Handler handler;
    private Message message;
    private static InputStream inputStream = null;
    private static OutputStream outputStream = null;
    private Context tcontext;
    static int time = 0;  //time用于在数据库中查找当前最新的心率值；
    static int unusual_flag = 0;
    private int calculate = 0, sum = 1;//累计接收到的数据总和和个数，显示历史心率;
    private int Sumten = 1;
    Get_value get_value = new Get_value();  //历史心率；
    Future_Data future_data = new Future_Data();  //训练样本集；

    public Connected(BluetoothSocket socket, Handler handler, Context context) {
        this.socket = socket;
        this.handler = handler;
        tcontext = context;
        InputStream tmin = null;
        OutputStream tmout = null;
        if (inputStream == null && outputStream == null) {
            try {
                tmin = socket.getInputStream();
                tmout = socket.getOutputStream();
            } catch (IOException e) {
                Log.e("", "In_Out Stream error.");
            }
            inputStream = tmin;
            outputStream = tmout;
        }
        if (inputStream != null && outputStream != null)
            Log.d("in&out", "not_null");
        else
            Log.d("in&out", "eq_null");
    }

    @Override
    public void run() {

        int i = 0, cnt = 0;
        while (true) {  //读数据；
            if (inputStream == null && outputStream == null) {
                try {
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null && outputStream != null)
                Log.e("run", "Not null");
            byte[] buffer = new byte[1024];
            Log.d("TAG", "true" + i);
            try {
                sleep(45000);//每1min读一次；
                write("r");//给下位机发送一个r，下位机返回当前心率值；
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Log.d("TAG", "tr" + i);
                if (buffer != null) {
                    Log.d("TAG", "突然" + i);
                    while (cnt == 0) {
                        cnt = inputStream.available();
                    }
                    int readcount = 0;
                    while (readcount < cnt) {
//                    cnt = inputStream.read(buffer);
                        readcount += inputStream.read(buffer, readcount, cnt - readcount);
                    }
                    Log.d("buffer", "cnt" + cnt);
                }
                if (cnt > 0) {
                    Log.d("buffer1", "input1");
                    String str = new String(buffer, 0, cnt, "utf-8");//str就是心率值；
                    char temp1 = str.charAt(0);
                    char temp2 = ' ';
                    int heart_rate = (temp1 - temp2) + 32;
                    // 去统计心率值；
                    caculate_rate(heart_rate);
                    /*异常检测*/
                    detection(heart_rate);
//                    Thread.currentThread().// 获取当前线程的引用；
                    /*保存到数据库中*/
                    save(heart_rate);  //保存心率值到数据库；
//                    message = new Message();
//                    message.obj = heart_rate + "\n";
//                    handler.sendMessage(message);//接收到数据进行显示处理；***显示当前心率***
                }
                cnt = 0;
                Log.d("TAG", "end" + i);
            } catch (IOException e) {
                Log.d("TAG", "" + i);
                Log.d("buffer2", "input2");
                message = new Message();
                message.obj = "无数据输入\n";
                Log.d("Data error.", "already");
                handler.sendMessage(message);
                //break;
            }
//            message = new Message();
//            message.obj = "数据接受结束。\n";
//            mmy_handler.sendMessage(message);
            Log.d("jp", "out");
        }
//        Log.d("while","stop");
    }

    public void write(String s) {   //写过去是字节数组，需要处理；
        try {
            String out = s;
            outputStream.write(out.getBytes()); //每发送一个则接收一次心率值；
            outputStream.flush();
            /*下面是检测数据执行输出*/
//            String Msg = new String(s, 0, 1, "utf-8");
//            message = new Message();
//            message.obj = Msg + "\n";
//            handler.sendMessage(message);r
            Log.d("write", "end");
//            try {
//                wait(5000);
//            }catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        } catch (IOException e) {
//            message = new Message();
//            message.obj = "Write异常！\n";
//            handler.sendMessage(message);
            Log.e("", "Write failed.");
        }
    }

    public void save(int value) {
//        MainActivity mainActivity = new MainActivity();
//        Context context = tactivity.getApplicationContext();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(tcontext, "Heart", null, 1);
        Execute_Data execute_data = new Execute_Data(dbOpenHelper);
        execute_data.add(value);
        time++;//时间参数；
    }

    public void detection(int heart_rate) {
        if (heart_rate > 100 || heart_rate < 40)
            unusual_flag++;
        else unusual_flag = 0;
        if (unusual_flag == 10) {
//            Send_Message send_message = new Send_Message();
            write("g");
            Send_Message.sendMessage();//发送消息；
            unusual_flag = 0;//重置；
        }
    }

    public void caculate_rate(int value) {
        calculate += value;
        if (Sumten % 10 == 0) {    //每10min统计一次;
            future_data.calculate(value);
        }
        if (sum % 60 == 0) {   //1h显示一次心率值;
            calculate /= 60;   //历史心率
            get_value.sum(calculate);
            calculate = 0;
        }
        sum++;
    }
}
