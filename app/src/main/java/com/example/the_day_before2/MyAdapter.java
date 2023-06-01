package com.example.the_day_before2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Region;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final ArrayList<ArrayList<String>> data;
    static public final ArrayList<CheckItem> checkData = new ArrayList<>(); // 勾選物件 List
    private final RecyclerView recyclerView;

    public MyAdapter(ArrayList<ArrayList<String>> data, RecyclerView recyclerView) {
        this.data = data;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
//  創建 ViewHolder 物件和 ViewHolder 物件的佈局
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//      連結 layout/item_layout.xml 佈局檔
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout,
                parent, false);
        return new ViewHolder(view);
    }

    int i;

    @Override
//  ViewHolder 中控件 (Button, TextView...) 的資料 or 監聽事件綁定 (對ViewHolder 的控件進行處理)
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        String event = data.get(position).get(0); // position 為 index 值
        String date = data.get(position).get(1);
        String diffDay = data.get(position).get(2);
        String type = data.get(position).get(3);
        int intDiffDay = Integer.parseInt(diffDay);
        holder.textView_event.setText(event);
        holder.textView_date.setText(date);

        if (intDiffDay > 0)
            holder.textView_dateNum.setText("D+" + diffDay);
        else if (intDiffDay == 0)
            holder.textView_dateNum.setText("今日");
        else
            holder.textView_dateNum.setText("D" + diffDay);

//      用傳進來的type值來用 switch-case 判斷並決定 imageView 的圖片
        switch (type) {
            case "自訂":
                holder.imageView.setImageResource(R.drawable.check);
                break;
            case "情侶":
                holder.imageView.setImageResource(R.drawable.heart);
                break;
            case "生日":
                holder.imageView.setImageResource(R.drawable.birthday_cake);
                break;
            case "考試":
                holder.imageView.setImageResource(R.drawable.exam);
                break;
            case "減肥":
                holder.imageView.setImageResource(R.drawable.weight_scale);
                break;
            case "戒菸":
                holder.imageView.setImageResource(R.drawable.no_smoking);
                break;
        }

        CheckItem checkItem = checkData.get(position);
        if (checkItem.isChecked())
            holder.imageView_checkBox.setImageResource(R.drawable.checked);
        else
            holder.imageView_checkBox.setImageResource(R.drawable.no_checked);
        holder.btnItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity.textView_delete.setVisibility(View.VISIBLE);
                MenuItem menuItem = MainActivity.toolbar.getMenu().findItem(R.id.settings); // 獲取 menu
                menuItem.setIcon(null);
                menuItem.setTitle("取消");
                holder.imageView_checkBox.setVisibility(View.VISIBLE);
                moveButtonTo("RIGHT");
                return true;
            }
        });
    }

    @Override
//  返回傳入的資料數目 可用來得知 RecyclerView 中有幾個 View
    public int getItemCount() {
        return data.size();
    }

    //  勾選類別用來記錄每個 View 的勾選狀態
    public static class CheckItem {
        private boolean isChecked = false; // default

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    //  可取得並存取 RecyclerView 中的控件
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // 事件 icon
        ImageView imageView_checkBox;  // 勾選 icon
        TextView textView_event; // 事件 text
        TextView textView_date; // 事件日期
        TextView textView_dateNum; // 相差天數
        Button btnItem; // 事件按鈕

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_imageView);
            imageView_checkBox = itemView.findViewById(R.id.imageView_checkBox);
            textView_event = itemView.findViewById(R.id.textView_event);
            textView_date = itemView.findViewById(R.id.textView_date);
            textView_dateNum = itemView.findViewById(R.id.textView_dateNum);
            btnItem = itemView.findViewById(R.id.btnItem);
            CheckItem checkItem = new CheckItem(); // 建立勾選物件來記錄勾選的狀態
            checkData.add(checkItem); // 將勾選物件加入至清單存放 以利之後綁定資料的時候(onBindViewHolder())可以存取
//          ImageView 勾選監聽事件
            imageView_checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // 獲取目前 View 控件的位置(index)
                    CheckItem check = checkData.get(position); // 取得目前當前 view 的勾選物件
                    check.setChecked(!check.isChecked()); // 設定勾選物件的勾選狀態
                    notifyItemChanged(getAdapterPosition(), imageView_checkBox);// 局部更新控件
                }
            });
        }
    }

    //  事件的動畫移動
    public void moveButtonTo(String direction) {
        int movementDistance;
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder viewHolder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                if (Objects.equals(direction, "RIGHT")) { // 向右移動
                    movementDistance = 200;
                    viewHolder.imageView_checkBox.setVisibility(View.VISIBLE);
                } else { // 移到原位
                    movementDistance = 0;
                    viewHolder.imageView_checkBox.setVisibility(View.GONE);
                }
                Animation animation = new TranslateAnimation(0, movementDistance, 0, 0);
                animation.setDuration(300);
                animation.setFillAfter(true);
                viewHolder.imageView.startAnimation(animation);
                viewHolder.btnItem.startAnimation(animation);
                viewHolder.textView_dateNum.startAnimation(animation);
                viewHolder.textView_date.startAnimation(animation);
                viewHolder.textView_event.startAnimation(animation);
            }
        }
    }
}
