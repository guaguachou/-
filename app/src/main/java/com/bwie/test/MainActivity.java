package com.bwie.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bwie.test.view.MyProgress;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bwie.test.R.id.circleProgress;

public class MainActivity extends AppCompatActivity {

    @Bind(circleProgress)
    MyProgress mCircleProgress;
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.button2)
    Button mButton2;

    private boolean isGo = false;
    private int progress = 0 ;
    public static final int PROGRESS_CIRCLE_STARTING = 0x110;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PROGRESS_CIRCLE_STARTING:
                    progress = mCircleProgress.getProgress();
                    mCircleProgress.setPercent(++progress);
                    if(progress >= 100){

                        progress = 0;
                        mCircleProgress.setPercent(0);

                        Message message = Message.obtain();
                        message.what = PROGRESS_CIRCLE_STARTING;
                        handler.sendMessage(message);

                    }else{
                        //延迟100ms后继续发消息，实现循环
                        handler.sendEmptyMessageDelayed(PROGRESS_CIRCLE_STARTING, 100);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({circleProgress, R.id.button, R.id.button2})
    public void onViewClicked(View view)   {
        Message message = Message.obtain();
        switch (view.getId()) {
            case circleProgress:
                break;
            case R.id.button:

                handler.removeCallbacksAndMessages(null);
                mCircleProgress.setPercent(0);
                message.what = PROGRESS_CIRCLE_STARTING;
                handler.sendMessage(message);

                break;
            case R.id.button2:
                if (isGo){

                    message.what = PROGRESS_CIRCLE_STARTING;
                    handler.sendMessage(message);
                    isGo=false;

                }else {

                    handler.removeCallbacksAndMessages(null);

                    isGo=true;
                }

                break;
        }
    }


}
