package com.example.second;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class RateListActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Handler handler;
    private static final  String TAG = "ttt";
    ListView mylist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate2_list);
        mylist2 = findViewById(R.id.mylist2);
        mylist2.setOnItemClickListener(RateListActivity2.this);
        ProgressBar bar = findViewById(R.id.progressBar);
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: ok");
                if(msg.what == 1){
                    ArrayList<HashMap<String,String>> list2 = (ArrayList<HashMap<String,String>>)msg.obj;
                    /*SimpleAdapter listItemAdapter = new SimpleAdapter(
                            RateListActivity2.this, list2,R.layout.list_item,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});*/
                    MyAdapter myAdapter = new MyAdapter(RateListActivity2.this,R.layout.list_item,list2);
                    mylist2.setAdapter(myAdapter);

                    //切换显示状态
                    bar.setVisibility(bar.GONE);
                    mylist2.setVisibility(mylist2.VISIBLE);

                }
                super.handleMessage(msg);
            }
        };
        RateList rlist = new RateList();
        rlist.setHandler(handler);
        Thread thread = new Thread(rlist);
        thread.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtPosition = mylist2.getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String,String>) itemAtPosition;
        String titlestr = map.get("ItemTitle");
        String detailstr = map.get("ItemDetail");
        Log.i(TAG, "tstr = "+titlestr);
        Log.i(TAG, "dstr = "+detailstr);

        Log.i(TAG, "open: ");
        Intent first = new Intent(this,huilvjisuan.class);
        first.putExtra("title",titlestr);
        first.putExtra("detail",detailstr);
        startActivityForResult(first,1);

        /*TextView vtitle = (TextView) view.findViewById(R.id.itemTitle);
        TextView vdetail = (TextView) view.findViewById(R.id.itemDetail);
        String titlestr1 = String.valueOf(vtitle.getText());
        String detailstr1 = String.valueOf(vdetail.getText());
        Log.i(TAG, "tstr1 = "+titlestr1);
        Log.i(TAG, "dstr1 = "+detailstr1);*/
    }
}