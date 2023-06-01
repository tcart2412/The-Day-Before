package com.example.the_day_before2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static public Toolbar toolbar;
    static public TextView textView_delete;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private ArrayList<ArrayList<String>> mData = new ArrayList<>();
    private final ArrayList<Integer> removeArray = new ArrayList<>(); // 存放欲勾選移除的 index 值
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        資料庫建立基本資料
        String DB_NAME = "testDB"; // 資料庫名稱
        int DB_VERSION = 1; // 資料庫版本
        // 建立 SQLiteOpenHelper 資料庫物件
        databaseHelper = new DatabaseHelper(this, DB_NAME, null, DB_VERSION);

//        資料庫的結構變化
//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//        db.execSQL("ALTER TABLE Users ADD type CHAR(50)");
//        db.execSQL("DELETE FROM sqlite_sequence WHERE name = 'Users'");
//        db.execSQL("DELETE FROM Users");

//      刪除點擊監聽事件
        textView_delete = findViewById(R.id.textView_delete);
        textView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItem menuItem = toolbar.getMenu().findItem(R.id.settings);
                menuItem.setIcon(R.drawable.menu_icon);
                menuItem.setTitle("選單");

//                System.out.println(removeArray); 印出欲勾選移除位置陣列(存放index)

//              取得目前所有勾選的位置並將位置(index)存到陣列
                for (int i = 0; i < mData.size(); i++) {
                    MyAdapter.CheckItem checkItem = MyAdapter.checkData.get(i);
                    if (checkItem.isChecked())
                        removeArray.add(i);
                }

                SQLiteDatabase db = databaseHelper.getWritableDatabase(); // 開啟資料庫

                for (int i = 0; i < removeArray.size(); i++)
                    db.execSQL("DELETE FROM Users WHERE title = \"" + mData.get(removeArray.get(i)).get(0) + "\"");

//              取的最新的資料陣列(mData) 若該資料有被勾選就要被remove, 如果本身的位置在勾選陣列中就移除
                for (int i = mData.size() - 1; i >= 0; i--) {
                    boolean isExist = false;
//                  判斷當前位置(index)是否存在在勾選陣列中 (判斷當前有沒有被勾選)
                    for (int j = 0; j < removeArray.size(); j++) {
                        if (mData.indexOf(mData.get(i)) == removeArray.get(j)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (isExist) {
                        mData.remove(i);
                        MyAdapter.checkData.remove(i);
                    }
                }
//                System.out.println(mData);
                MyAdapter myAdapter = new MyAdapter(mData, recyclerView); // 將資料流設定給調配器adapter
                recyclerView.setAdapter(myAdapter); // recyclerView 的內容設定(刷新)
                v.setVisibility(View.GONE);
                removeArray.clear(); // 清空移除陣列 否則按下刪除按鈕第二次時就會報錯

            }
        });


        recyclerView = findViewById(R.id.recycle_view);
//      recyclerView 為垂直往下
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//      設置格線
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                                           DividerItemDecoration.VERTICAL));

//      將 mData 中的資料交給適配器 adapter 並設定給 recyclerView
        getEvents(); // 獲取目前資料庫中的所有事件
        MyAdapter adapter = new MyAdapter(mData, recyclerView);
        recyclerView.setAdapter(adapter);

//      側邊導覽列 nav
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

//      將 toolbar 設為此 App 的 ActionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      加號按鈕 (浮動按鈕) fab 的點擊事件
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SecondActivity.class); // 前往另個頁面
            startActivity(intent);
        });

//      處理 nav 中選項的點擊事件
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_home)
                drawerLayout.closeDrawer(GravityCompat.END);// 點選時收起選單
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//      取得 layout/menu.xml 檔, 綁定 menu 長甚麼樣子
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    // toolbar 上 menu 的點擊事件
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // 按下有 icon 的 menu
        if (id == R.id.settings && item.getTitle() != "取消") {
            drawerLayout.openDrawer(GravityCompat.END); // 側邊 nav 滑出
        } else // 此時 menu title 為取消 menu (無 icon)
        {
            MyAdapter myAdapter = new MyAdapter(mData, recyclerView);
            item.setIcon(R.drawable.menu_icon);
            item.setTitle("選單");
            myAdapter.moveButtonTo("ORIGIN"); // 所有事件回到原位
            textView_delete.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void getEvents() { // Activity 一開始 load 時引用
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Cursor c 類似 C# 的 SqlDataReader dataReader
        Cursor c = db.rawQuery("SELECT title, date, diffDay , type FROM Users", null);
        if (c.moveToFirst()) { // 資料庫有資料才做 do-while, 從(頭) First 開始 如果開頭有資料返回值為 true
            do {
                ArrayList<String> arrayList = new ArrayList<>();
                for (int j = 0; j < 4; j++)
                    arrayList.add(c.getString(j));
                mData.add(arrayList);
            }
            while (c.moveToNext()); // 當有下一筆資料 return 為 true
        }
    }
}