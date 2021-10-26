package com.example.second;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class huilvjisuan extends AppCompatActivity {

    private static final String TAG = "hhh";
    java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");
    float hd = 0,out = 0,hb=0;
    TextView tt;
    EditText dd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilvjisuan);

        Log.i(TAG, "receive: ");
        Intent in = getIntent();
        String d = in.getStringExtra("title");
        tt = findViewById(R.id.huobititle);
        tt.setText(String.valueOf(d));

        String hd1 = in.getStringExtra("detail");
        hd = Float.parseFloat(df.format(100/Float.parseFloat(hd1)));
        Log.i(TAG, "hd = "+hd);
        Log.i(TAG, "receive: ok");
        dd = findViewById(R.id.huobijisuan);
        dd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String in = dd.getText().toString();
                Log.i(TAG, "inp="+in);
                if(s.length() > 0){
                    hb = Float.parseFloat(s.toString());
                    out = hb * hd;
                    tt.setText(String.valueOf(d)+": "+df.format(out));
                }else{
//                    Toast.makeText(huilvjisuan.this, "请勿输入空值", Toast.LENGTH_SHORT).show();
                    tt.setText("");
                }
                Log.i(TAG, "count: ok");
            }
        });
    }

    public void btn_hb(View btn){
        dd = findViewById(R.id.huobijisuan);
        String in = dd.getText().toString();
        Log.i(TAG, "inp="+in);
        if(in.length() > 0){
            hb = Float.parseFloat(in);
        }else{
            Toast.makeText(huilvjisuan.this, "请勿输入空值", Toast.LENGTH_SHORT).show();
        }
        out = hb * hd;
        dd.setText(df.format(out));
        Log.i(TAG, "count: ok");
    }
}