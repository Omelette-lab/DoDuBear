package com.example.denglu;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TomaClock extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private String timerName = "番茄钟";
    private boolean isCountingDown = false; // 是否正在倒计时中
    private long remainingMillis; // 剩余毫秒数
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomaclock);

        // 找到倒计时长度选择的 RadioGroup，添加监听器
        final RadioGroup radioGroup = findViewById(R.id.radio_group_duration);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 如果计时器当前正在运行，则先暂停
                if (isCountingDown) {
                    pauseCountdown();
                }

                // 重新创建计时器并更新剩余毫秒数
                switch (checkedId) {
                    case R.id.radio_5:
                        remainingMillis = 5* 1000;
                        break;
                    case R.id.radio_25:
                        remainingMillis = 25 * 60 * 1000;
                        break;

                    case R.id.radio_40:
                        remainingMillis = 40 * 60 * 1000;
                        break;

                    case R.id.radio_60:
                        remainingMillis = 60 * 60 * 1000;
                        break;
                }

                // 更新显示和按钮状态
                isCountingDown = false;
                TextView textView = findViewById(R.id.text_countdown);
                textView.setText(timerName + "：" + formatMillis(remainingMillis));
                showStartButton();
            }
        });

        // 找到开始/暂停/继续按钮并添加点击事件
        Button buttonStart = findViewById(R.id.button_start);
        Button buttonPause = findViewById(R.id.button_pause);
        Button buttonResume = findViewById(R.id.button_resume);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountdown(remainingMillis); // 开始倒计时
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseCountdown(); // 暂停倒计时
            }
        });
        buttonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeCountdown(); // 继续倒计时
            }
        });

        // 找到重置按钮并添加点击事件
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCountdown(); // 重置倒计时
            }
        });
    }
    //我的个人信息
    public void my_message(View v){
        Intent intent=new Intent();
        intent.setClass(TomaClock.this,My_message.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }
    //休息区
    public void vi(View v){
        Intent intent=new Intent();
        intent.setClass(TomaClock.this, RestArea.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }
    //待做事项
    public void todolist(View v){
        Intent intent=new Intent();
        intent.setClass(TomaClock.this, ToDoList.class);
        intent.putExtra("name",this.username);
        startActivity(intent);
    }

    private void startCountdown(long millisInFuture) {
        // 如果计时器当前正在运行，则先取消
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // 重新创建计时器并启动
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdown(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                onCountDownFinished();
            }
        }.start();

        // 更新状态和按钮状态
        isCountingDown = true;
        hideStartButton();
        showPauseButton();
    }

    private void pauseCountdown() {
        // 取消计时器并更新状态和按钮状态
        countDownTimer.cancel();
        isCountingDown = false;
        hidePauseButton();
        showResumeButton();
    }

    private void resumeCountdown() {
        // 重新创建计时器并继续
        countDownTimer = new CountDownTimer(remainingMillis - remainingMillis % 1000, 1000) { // 对齐到整秒数
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdown(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                onCountDownFinished();
            }
        }.start();

        // 更新状态和按钮状态
        isCountingDown = true;
        hideResumeButton();
        showPauseButton();
    }

    private void resetCountdown() {
        // 取消计时器
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // 重置剩余毫秒数、状态和显示，并更新按钮状态
        remainingMillis = 25 * 60 * 1000;
        isCountingDown = false;
        TextView textView = findViewById(R.id.text_countdown);
        textView.setText(timerName + "：" + formatMillis(remainingMillis));
        hidePauseButton();
        hideResumeButton();
        showStartButton();
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setVisibility(View.GONE);
    }

    private void updateCountdown(long millisUntilFinished) {
        remainingMillis = millisUntilFinished; // 保存剩余毫秒数
        isCountingDown = true; // 将状态设置为正在倒计时中

        TextView textView = findViewById(R.id.text_countdown);
        textView.setText(timerName + "：" + formatMillis(millisUntilFinished));
    }

    private void onCountDownFinished() {
        isCountingDown = false; // 将状态设置为未在倒计时中
        showResetButton();
        Toast.makeText(this, timerName + " 时间到！", Toast.LENGTH_SHORT).show();
    }

    private String formatMillis(long millis) {
        int minutes = (int) (millis / 1000 / 60);
        int seconds = (int) ((millis / 1000) % 60);
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private void showStartButton() {
        Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setVisibility(View.VISIBLE);
    }

    private void hideStartButton() {
        Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setVisibility(View.GONE);
    }

    private void showPauseButton() {
        Button buttonPause = findViewById(R.id.button_pause);
        buttonPause.setVisibility(View.VISIBLE);
    }

    private void hidePauseButton() {
        Button buttonPause = findViewById(R.id.button_pause);
        buttonPause.setVisibility(View.GONE);
    }

    private void showResumeButton() {
        Button buttonResume = findViewById(R.id.button_resume);
        buttonResume.setVisibility(View.VISIBLE);
    }

    private void hideResumeButton() {
        Button buttonResume = findViewById(R.id.button_resume);
        buttonResume.setVisibility(View.GONE);
    }

    private void showResetButton() {
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setVisibility(View.VISIBLE);
    }
}
