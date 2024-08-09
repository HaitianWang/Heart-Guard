package com.example.wechel.heart;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.UUID;

public class Connect extends Thread {
    private BluetoothAdapter adapter;
    private BluetoothDevice current_device;
    private String address;
    public Handler handler;
    private ImageButton my_Open;//获取主界面蓝牙开关按钮对象；
    private Message message;
    private static BluetoothSocket socket = null;
    private static final UUID Buletooth_UUID = MainActivity.BULETOOTH_UUID;
    private static int flag = 0; //判断是否已有socket连接建立。
    private Context context;

    public Connect(Handler handler, ImageButton button, BluetoothAdapter blue, BluetoothDevice device, Context context) {
        this.handler = handler;
        my_Open = button;
        adapter = blue;
        current_device = device;
        this.context = context;
        if (socket == null) {
            try {
                socket = device.createRfcommSocketToServiceRecord(Buletooth_UUID);//建立一个通信信道；
                flag = 1;

            } catch (IOException e) {
                String str = "腕带连接异常\n";
                Excute_yichang();//按钮归位 & flag=0;就是 蓝牙连接异常 处理的；对的
//                my_Open.setImageResource(R.drawable.open1);//连接蓝牙异常，按钮归位。
                message = new Message();
                message.obj = str;
                handler.sendMessage(message);
                try {
                    sleep(10);
                } catch (InterruptedException x) {
                    x.printStackTrace();
                }
            }
//            my_Socket = tem_socket;
        }
    }

    @Override
    public void run() {
        if (flag != 0) {
            try {
                socket.connect();
                flag = 0;//已有socket连接，就不用再次建立连接；
            } catch (IOException e) {
                Log.e("", e.getMessage());
                try {
                    Log.e("", "trying fallback");
                    socket = (BluetoothSocket) current_device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(current_device, 1);
                    socket.connect();
                } catch (Exception e1) {
                    Excute_yichang(); //连接异常处理；
                    Log.e("", "reconnect failed.");
                }
            }
            proceedConnected(socket);
        }
    }

    private void proceedConnected(BluetoothSocket socket) {
        Message msg = new Message();
        msg.obj = "已成功连接腕带！\n";
        handler.sendMessage(msg);
//        try {
//            sleep(2000);
//        } catch (InterruptedException x) {
//            message = new Message();
//            message.obj = "5秒后启动输出线程\n";
//            my_handler.sendMessage(message);
//        }
//
        Connected connectedThread = new Connected(socket, handler, context);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            Log.e("", "Connect sleep 3秒");
        }
        connectedThread.start();
        if (connectedThread != null)
            Log.d("connected_Thread", "我开始了");
    }

    public void Excute_yichang() {
        MainActivity mainActivity = new MainActivity();
        my_Open.setImageResource(R.drawable.open1);//连接蓝牙异常，按钮归位。
        mainActivity.flag = 0;
    }
}
