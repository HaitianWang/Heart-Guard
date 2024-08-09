package com.example.wechel.heart.Family;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wechel.heart.R;

public class Send_Message extends AppCompatActivity {
    EditText editText;
    Button button;
    static String number ;
    static SmsManager smsManager;//创建短信管理器;
    static String message = "您的亲友当前心率处于异常状态，请注意！";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__message);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button11);
        smsManager = SmsManager.getDefault();
    }
    public void add(View view){
        number = editText.getText().toString();
        Toast.makeText(Send_Message.this,"亲友号添加成功！",Toast.LENGTH_SHORT).show();
    }
    public void Return(View view){
        Toast.makeText(Send_Message.this,number,Toast.LENGTH_LONG).show();
        finish();
    }
    /*心率异常检测，发送短信*/
    public static void sendMessage(){
        smsManager.sendTextMessage(number,null,message,null,null);
    }
}
