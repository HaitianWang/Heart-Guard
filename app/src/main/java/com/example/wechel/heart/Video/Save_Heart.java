package com.example.wechel.heart.Video;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.wechel.heart.R;

public class Save_Heart extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏;
        getWindow().setFormat(PixelFormat.TRANSLUCENT); //窗口设置为透明；
        getSupportActionBar().hide();//取消标题;
        setContentView(R.layout.activity_save__heart);
        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(Save_Heart.this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.health;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.requestFocus();
        try{
            videoView.start();
        }catch (Exception e){
            Log.e("","video failed.");
        }
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(Save_Heart.this,"视频播放完毕！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
