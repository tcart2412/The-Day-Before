package com.example.the_day_before2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {
    private TextView textView;
    int isClick = 0;
    DatabaseHelper databaseHelper;
    private int year, month, day;
    Calendar calendar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_main);

//      資料庫建立基本資料
        String DB_NAME = "testDB"; // 資料庫名稱
        int DB_VERSION = 1; // 資料庫版本
        databaseHelper = new DatabaseHelper(this, DB_NAME, null, DB_VERSION);// 建立 SQLiteOpenHelper 資料庫物件

        Toolbar toolbar = findViewById(R.id.second_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ImageView imageView = findViewById(R.id.back_image);
//      返回鍵 (左箭頭) 點擊事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//      輸入框點擊事件
        EditText editText = findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setShowSoftInputOnFocus(true);
            } // 開啟鍵盤
        });

//      事件圖片點擊事件
        ImageView eventIcon = findViewById(R.id.imageView_eventIcon);
        eventIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setShowSoftInputOnFocus(false);
            } // 收起鍵盤
        });

//      DatePicker/Spinner 設定
        Button btn_calendar = findViewById(R.id.btn_calendar); // 日曆按鈕
        textView = findViewById(R.id.textView_showDate);
        DatePicker datePicker = findViewById(R.id.datePicker);
        calendar = Calendar.getInstance();// 取得目前時間的 年/月/日
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Date date = new Date(year, month, day - 1); // 取得目前時間的日期 date
        @SuppressLint("SimpleDateFormat") // 偵錯忽略
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E"); // 日期輸出格式設為星期
        String dayOfWeek = "(" + simpleDateFormat.format(date) + ")"; // 獲取目前日期是星期幾
        textView.setText(year + "/" + (month + 1) + "/" + day + "/" + dayOfWeek); // yyyy/mm/dd(E)

//      設定 Calendar/Spinner 顯示的日期並且設定日期改變的監聽事件
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Date date = new Date(year, month, dayOfMonth - 1);
                String dayOfWeek = "(" + simpleDateFormat.format(date) + ")"; //獲取所選日期是星期幾
//              設定 textView 文字為 DatePicker/Spinner 選取的日期
                textView.setText(year + "/" + (month + 1) + "/" + dayOfMonth + dayOfWeek);
            }
        });

//      日曆按鈕點擊事件並且將在 DatePicker/Calendar 選取的日期設給 DatePicker/Spinner
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SecondActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            //                          當確定選取日期時的監聽事件
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                Date date = new Date(selectedYear, selectedMonth, selectedDay - 1);
                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E"); // 日期輸出格式設為星期
                                String dayOfWeek = "(" + simpleDateFormat.format(date) + ")"; // 獲取所選日期是星期幾
                                textView.setText(selectedYear + "/" + (selectedMonth + 1) + "/" + selectedDay + dayOfWeek);
                                datePicker.init(selectedYear, selectedMonth, selectedDay, new DatePicker.OnDateChangedListener() {
                                    @Override
                                    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
                                        Date date = new Date(year, month, dayOfMonth - 1);
                                        String dayOfWeek = "(" + simpleDateFormat.format(date) + ")";
                                        textView.setText(year + "/" + (month + 1) + "/" + dayOfMonth + dayOfWeek); // yyyy/mm/dd(E)
                                    }
                                });
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

//      動態設定每個按鈕的點擊事件、間隔大小
        LinearLayout linearLayout = findViewById(R.id.LinearLayout_scroll);
        int childCount = linearLayout.getChildCount();
        int width = 50;
        LinearLayout.LayoutParams Params;
        if (childCount > 1) {
            for (int var = 0; var < childCount; var++) {
                Button btn;
                View view = linearLayout.getChildAt(var);
                btn = (Button) linearLayout.getChildAt(var); // 建立按鈕物件
                Params = (LinearLayout.LayoutParams) view.getLayoutParams();
                Params.setMarginStart(width);
                if (var == (childCount - 1))
                    Params.setMarginEnd(width);
                if (var == 0) {
                    btn.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                    btn.setTextColor(getResources().getColor(R.color.white));
                }
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = btn.getText().toString();
//                      先確保按下按鈕前沒有其他按鈕被按(紅底白字) 如果有的話就取消按下(灰底黑字)
                        for (int i = 0; i < childCount; i++) {
                            Button btn = (Button) linearLayout.getChildAt(i);
                            if (btn.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                                btn.setBackgroundColor(getResources().getColor(R.color.gray));
                                btn.setTextColor(getResources().getColor(R.color.black));
                            }
                        }
                        btn.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
                        btn.setTextColor(getResources().getColor(R.color.white));
                        if (!text.equals("自訂"))
                            editText.setText(text);
                        else {
                            editText.setText("");
                            eventIcon.setImageResource(R.drawable.check);
                        }
                        switch (text) {
                            case "情侶":
                                eventIcon.setImageResource(R.drawable.heart);
                                break;
                            case "生日":
                                eventIcon.setImageResource(R.drawable.birthday_cake);
                                break;
                            case "考試":
                                eventIcon.setImageResource(R.drawable.exam);
                                break;
                            case "減肥":
                                eventIcon.setImageResource(R.drawable.weight_scale);
                                break;
                            case "戒菸":
                                eventIcon.setImageResource(R.drawable.no_smoking);
                                break;
                        }
                    }
                });
            }
        }

//      上下箭頭點擊事件
        ImageView imageViewArrow = findViewById(R.id.imageView_click);

        imageViewArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              獲取輸入法管理器 imm
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//              會取得具有焦點的控件 (editText) 的 WindowToken 傳給 imm 管理器來隱藏鍵盤
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                if (isClick == 0) {
                    imageViewArrow.setImageResource(R.drawable.up_arrow); // 箭頭上
                    datePicker.setVisibility(View.VISIBLE);   // DatePicker/Spinner
                    btn_calendar.setVisibility(View.VISIBLE); // 日曆按鈕
                } else {
                    imageViewArrow.setImageResource(R.drawable.down_arrow); // 箭頭下
                    datePicker.setVisibility(View.GONE);   // DatePicker/Spinner
                    btn_calendar.setVisibility(View.GONE); // 日曆按鈕

                }
                isClick = ~isClick;
            }
        });
    }

    ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//      設定要用哪個 menu 作為選單 (綁定xml)
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
//      當 save 被按下
        if (id == R.id.save) {
            EditText editText = findViewById(R.id.editText); // 標題
            String text1 = editText.getText().toString();     // 日期
            ImageView eventIcon = findViewById(R.id.imageView_eventIcon);
            TextView dateView = findViewById(R.id.textView_showDate);
            String text2 = dateView.getText().toString();

//          取得目前欲儲存的事件類型
            LinearLayout linearLayout = findViewById(R.id.LinearLayout_scroll);
            int childCount = linearLayout.getChildCount();
            String btnType = ""; // 事件類型字串
            if (childCount > 1) {
                for (int var = 0; var < childCount; var++) {
                    Button btn;
                    btn = (Button) linearLayout.getChildAt(var); // 建立按鈕物件
                    if (btn.getCurrentTextColor() == getResources().getColor(R.color.white))
                        btnType = btn.getText().toString();
                }
            }

//          用字串長度判斷是否輸入標題為空
            if (text1.length() > 0) {
                int num = 0;
                Intent intent = new Intent();
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                Cursor c = db.rawQuery("SELECT title, date, type FROM Users", null);
                if (c.moveToFirst()) {
                    do {
                        if (text1.equals(c.getString(0)) && text2.equals(c.getString(1)) && btnType.equals(c.getString(2))) {
                            num++;
                            break;
                        }
                    } while (c.moveToNext());
                    if (num > 0)
                        showDialog();
                    else {
                        addData(text1, textView.getText().toString(), getDiffDay(), btnType);
                        Toast.makeText(this, text1 + "事件新增成功!", Toast.LENGTH_SHORT).show();
                        intent.setClass(SecondActivity.this, Enter.class); // 前往另個頁面
                        startActivity(intent);
                    }
                } else {
                    addData(text1, textView.getText().toString(), getDiffDay(), btnType);
                    Toast.makeText(this, text1 + "事件新增成功!", Toast.LENGTH_SHORT).show();
                    intent.setClass(SecondActivity.this, Enter.class); // 前往另個頁面
                    startActivity(intent);
                }
            } else
                Toast.makeText(this, text1 + "請輸入標題", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //  顯示事件重複訊息框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("輸入事件重複!!!");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //  新增資料
    public void addData(String title, String date, int diff, String type) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase(); // 與 testDB 資料庫連線/開啟資料庫
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("date", date);
        values.put("diffDay", diff);
        values.put("type", type);
        db.insert("Users", null, values);
    }

    //  取得相差天數
    private int getDiffDay() {
        Calendar currentDate = Calendar.getInstance(); // 取得目前日期
        Calendar selectedDate = Calendar.getInstance();

        DatePicker datePicker = findViewById(R.id.datePicker);
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();
        selectedDate.set(year, month, dayOfMonth); // 取得所選日期

        long diffInMillis = selectedDate.getTimeInMillis() - currentDate.getTimeInMillis(); // 相差毫秒
        int diffInDays = (int) (diffInMillis / (24 * 60 * 60 * 1000)); // 算出相差天數

        return diffInDays;
    }

}