package com.example.the_day_before2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Slave extends AppCompatActivity {

    int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Enter.emperor = false;
        Enter.slave = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);
        ImageButton btn1 = findViewById(R.id.imageButton1);
        ImageButton btn2 = findViewById(R.id.imageButton2);
        ImageButton btn3 = findViewById(R.id.imageButton3);
        ImageButton btn4 = findViewById(R.id.imageButton4);
        ImageButton btn5 = findViewById(R.id.imageButton5);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);

        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);

        int n = (int)(Math.random() * 5) + 1;
        int enemy = (int)(Math.random() * 5) + 1;
        String t = String.valueOf(n);
        switch (n){
            case 1 :
                btn1.setImageResource(R.drawable.slave);
                break;
            case 2 :
                btn2.setImageResource(R.drawable.slave);
                break;
            case 3 :
                btn3.setImageResource(R.drawable.slave);
                break;
            case 4 :
                btn4.setImageResource(R.drawable.slave);
                break;
            case 5 :
                btn5.setImageResource(R.drawable.slave);
                break;
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enter.flag1 = true;
                Enter.flag2 = true;
                btn1.setVisibility(View.INVISIBLE);
                imageView1.setVisibility(View.VISIBLE);
                if (btn1.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.citizen).getConstantState())) {
                    imageView1.setImageResource(R.drawable.citizen);
                }
                else{
                    imageView1.setImageResource(R.drawable.slave);
                    Enter.flag2 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                if (enemy != num){
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.citizen);
                }
                else {
                    imageView2.setImageResource(R.drawable.emperor);
                    Enter.flag1 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                num++;
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enter.flag1 = true;
                Enter.flag2 = true;
                btn2.setVisibility(View.INVISIBLE);
                imageView1.setVisibility(View.VISIBLE);
                if (btn2.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.citizen).getConstantState())) {
                    imageView1.setImageResource(R.drawable.citizen);
                }
                else{
                    imageView1.setImageResource(R.drawable.slave);
                    Enter.flag2 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                if (enemy != num){
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.citizen);
                }
                else {
                    imageView2.setImageResource(R.drawable.emperor);
                    Enter.flag1 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                num++;
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enter.flag1 = true;
                Enter.flag2 = true;
                btn3.setVisibility(View.INVISIBLE);
                imageView1.setVisibility(View.VISIBLE);
                if (btn3.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.citizen).getConstantState())) {
                    imageView1.setImageResource(R.drawable.citizen);
                }
                else{
                    imageView1.setImageResource(R.drawable.slave);
                    Enter.flag2 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                if (enemy != num){
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.citizen);
                }
                else {
                    imageView2.setImageResource(R.drawable.emperor);
                    Enter.flag1 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                num++;
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enter.flag1 = true;
                Enter.flag2 = true;
                btn4.setVisibility(View.INVISIBLE);
                imageView1.setVisibility(View.VISIBLE);
                if (btn4.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.citizen).getConstantState())) {
                    imageView1.setImageResource(R.drawable.citizen);
                }
                else{
                    imageView1.setImageResource(R.drawable.slave);
                    Enter.flag2 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                if (enemy != num){
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.citizen);
                }
                else {
                    imageView2.setImageResource(R.drawable.emperor);
                    Enter.flag1 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                num++;
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enter.flag1 = true;
                Enter.flag2 = true;
                btn5.setVisibility(View.INVISIBLE);
                imageView1.setVisibility(View.VISIBLE);
                if (btn5.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.citizen).getConstantState())) {
                    imageView1.setImageResource(R.drawable.citizen);
                }
                else{
                    imageView1.setImageResource(R.drawable.slave);
                    Enter.flag2 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                if (enemy != num){
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.citizen);
                }
                else {
                    imageView2.setImageResource(R.drawable.emperor);
                    Enter.flag1 = true;
                    Intent intent = new Intent(Slave.this, End.class);
                    startActivity(intent);
                }
                num++;
            }
        });
    }
}