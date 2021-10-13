package com.example.second;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class MyListActivity extends ListActivity {

    java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");
    float d = 0.1f,e = (float)0.1,w =(float) 1.0;
    Handler handler;
    private static final  String TAG = "TTT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不需要布局文件
        //setContentView(R.layout.activity_my_list);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: ok");
                if(msg.what == 1){
                    List<String> list2 = (List<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(
                            MyListActivity.this, android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        RateTask task = new RateTask();
        task.setHandler(handler);
        Thread thread = new Thread(task);
        thread.start();

    }
}