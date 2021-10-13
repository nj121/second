package com.example.second;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RateListActivity2 extends AppCompatActivity {

    Handler handler;
    private static final  String TAG = "TTT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);
        ListView mylist = findViewById(R.id.mylist1);
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: ok");
                if(msg.what == 1){
                    List<String> list2 = (List<String>)msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(
                            RateListActivity2.this, android.R.layout.simple_list_item_1,list2);
                    mylist.setAdapter(adapter);
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