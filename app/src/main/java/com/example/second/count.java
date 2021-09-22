package com.example.second;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class count extends AppCompatActivity {

    int s = 0;
    int s1 = 0;
    public static final String TAG = "count";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
    }

    public void click(View btn){
        Log.i("TAG","click");
        if(btn.getId() == R.id.btn1){
            s++;
        }else if(btn.getId() == R.id.btn2){
            s += 2;
        }else if(btn.getId() == R.id.btn3){
            s +=3;
        }else{
            s = 0;
        }

        TextView count = findViewById(R.id.count);
        count.setText(""+s);
    }

    public void click1(View btn){
        Log.i("TAG","click1");
        if(btn.getId() == R.id.btn11){
            s1++;
        }else if(btn.getId() == R.id.btn21){
            s1 += 2;
        }else if(btn.getId() == R.id.btn31){
            s1 +=3;
        }else{
            s1 = 0;
        }

        TextView count1 = findViewById(R.id.count1);
        count1.setText(""+s1);
    }
}