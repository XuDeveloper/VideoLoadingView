package com.xu.videoloadingviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xu.library.VideoLoadingView.VideoLoadingViewSpeed;
import com.xu.library.VideoLoadingView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startBtn;
    private Button stopBtn;
    private Button pauseBtn;
    private VideoLoadingView view;
    private VideoLoadingView view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (VideoLoadingView) findViewById(R.id.videoLoadingView);
        view1 = (VideoLoadingView) findViewById(R.id.videoLoadingView2);
//        view1.setArcColor(Color.GREEN);
//        view1.setTriangleColor(Color.GREEN);
        view1.setSpeed(VideoLoadingViewSpeed.SPEED_SLOW);
        view1.start();
        startBtn = (Button) findViewById(R.id.start_btn);
        stopBtn = (Button) findViewById(R.id.stop_btn);
        pauseBtn = (Button) findViewById(R.id.pause_btn);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        view.registerVideoViewListener(new VideoLoadingView.VideoViewListener() {
            @Override
            public void onStart() {
                Toast.makeText(MainActivity.this, "video_loading_view onStart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPause(double progress) {
                Toast.makeText(MainActivity.this, "video_loading_view onPause, progress:" + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStop() {
                Toast.makeText(MainActivity.this, "video_loading_view onStop", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                view.start();
                break;

            case R.id.stop_btn:
                view.stop();
                break;

            case R.id.pause_btn:
                view.pause();
                break;
        }
    }
}
