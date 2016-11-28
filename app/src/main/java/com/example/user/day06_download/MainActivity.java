package com.example.user.day06_download;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int MAIN_PRO = 1;
    private static final int MAIN_END = 2;
    private Button bt_download;
    private TextView show;
    private ProgressBar pb;
    private Handler hb=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MAIN_PRO:
                    pb.setProgress(msg.arg1);
                    bt_download.setText("下载");
                    break;
                case MAIN_END:
                    show.setText((String)msg.obj);
                    bt_download.setText("重新下载");
                    break;
            }
        }
    };
    private Message ms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化空间
        init();
        //监听事件
        bt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.setText("开始下载");
                //定义一个方法写下载代码
                download();
            }
        });

    }

    private void init() {
        bt_download= (Button) findViewById(R.id.button);
        show= (TextView) findViewById(R.id.tv);
        pb= (ProgressBar) findViewById(R.id.progressBar);

    }
    private void download() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i=0;i<100;i++){
                    ms=Message.obtain();
                    try {
                        Thread.sleep(10);
                        ms.arg1=i;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ms.what = MAIN_PRO;
                    hb.sendMessage(ms);
                }
                Message messb=Message.obtain();
                messb.what= MAIN_END;
                messb.obj="已下载完成";
                hb.sendMessage(messb);
            }
        }.start();

    }
}
