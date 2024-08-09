package com.example.wechel.heart.Data;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.example.wechel.heart.Family.Send_Message;

import java.util.ArrayList;
import java.util.Map;

public class Execute_Data {  //调用这个函数需要实例化dbOpenHelper对象；这个对象实例化需要context;
    private DBOpenHelper dbOpenHelper;

    public Execute_Data(DBOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public String search(int time) {  //这是查找
        String value = "null";
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery("select * from Heart order by time desc limit 1", null);
//        query("Heart",null,"time=?",new String[]{String.valueOf(time)},null,null,null);
        ArrayList<Map<String, String>> resultlist = new ArrayList<Map<String, String>>();//用于保存；
        while (cursor.moveToNext()) {
//            Map<String,String> map = new HashMap<String, String>();
//            map.put("word",cursor.getString(1));
//            String name = map.get(cursor.getColumnName(2));
//            map.put("interpret",cursor.getString(2));
//            resultlist.add(map);
            String time1 = cursor.getString(cursor.getColumnIndex("time"));
            value = cursor.getString(cursor.getColumnIndex("heart_value"));
            break;
        }
        return value;
    }

    /*向数据库插入数据*/
    public void add(int heart_num) {  //这是插入；
        insert(dbOpenHelper.getReadableDatabase(), heart_num);
    }

    private void insert(SQLiteDatabase db, int heart_num) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("heart_value", heart_num);
//        contentValues.put("detail",detail);//word和detail是key;
        db.insert("Heart", null, contentValues);
//        db.execSQL("insert into credit_message values(null,?,?)",new String [] {word,detail});
    }
//    /*心率异常检测，发送短信*/
//    private void sendMessage(){
//        Send_Message send_message = new Send_Message();
//        String number = send_message.number;//号码；
//        String context = send_message.message;//短信内容；
//        SmsManager smsManager = send_message.smsManager;//短信管理器；
//        smsManager.sendTextMessage(number,null,context,null,null);
//    }

}
