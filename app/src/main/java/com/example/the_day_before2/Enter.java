package com.example.the_day_before2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Enter extends AppCompatActivity {
    public static Boolean flag1 = true;
    public static Boolean flag2 = true;
    public static Boolean emperor = false;
    public static Boolean slave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        TextView textView_counter = findViewById(R.id.textView_counter);
        TextView textView_skip = findViewById(R.id.textView_skip);
//        start tcart2412
        CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                int sec = (int) (millisUntilFinished / 1000);
                if (sec == 0){
                    textView_counter.setVisibility(View.GONE);
                    textView_skip.setVisibility(View.VISIBLE);
                }
                else
                    textView_counter.setText("" + sec);
            }

            @Override
            public void onFinish() {
            }
        };
        countDownTimer.start();
        textView_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Enter.this, SecondActivity.class); // 前往另個頁面
                startActivity(intent);
            }
        });
//        end tcart2412

        Button emporer = findViewById(R.id.emporer);
        Button slave = findViewById(R.id.slave);
        emporer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Enter.this, Emporer.class);
                startActivity(intent);
            }
        });
        slave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Enter.this, Slave.class);
                startActivity(intent);
            }
        });
    }
}