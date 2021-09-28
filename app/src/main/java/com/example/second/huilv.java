package com.example.second;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class huilv extends AppCompatActivity implements Runnable {

    private static final String TAG = "kkkkk";
    float d = 0.15f,e = (float)0.13,w =(float) 182.43;
    Handler handler;
    TextView infrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilv);

        infrom = findViewById(R.id.infrom);

        Log.i(TAG, "shared:");
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        d = sp.getFloat("dr",0.0f);
        e = sp.getFloat("er",0.0f);
        w = sp.getFloat("wr",0.0f);
        Log.i(TAG, "shared: ok");

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: 接收");
                if(msg.what == 1){
                    String str = (String)msg.obj;
                    Log.i(TAG, "handleMessage: 消息是 " + str);
                    infrom.setText(str);
                }
                super.handleMessage(msg);
            }
        };

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.he){
            Log.i(TAG, "menu: ");
            Intent first = new Intent(this,opentest.class);
            first.putExtra("dollar_key",d);
            first.putExtra("euro_key",e);
            first.putExtra("won_key",w);

            Log.i(TAG, "menu:ok");

            startActivityForResult(first,1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void hclick(View btn){
        Log.i(TAG, "hclick: ");
        EditText in = findViewById(R.id.input);
        String inp = in.getText().toString();
        Log.i(TAG, "inp="+inp);
        float out = 0,rmb=0;
        if(inp.length() > 0){
            rmb = Float.parseFloat(inp);

        }else{
            Toast.makeText(huilv.this, "请勿输入空值", Toast.LENGTH_SHORT).show();
        }

        if(btn.getId() == R.id.dollar){
            out = rmb*d;
            in.setText(""+out);
        }else if(btn.getId() == R.id.euro){
            out = rmb*e;
            in.setText(""+out);
        }else if(btn.getId() == R.id.won){
            out = rmb*w;
            in.setText(""+out);
        }
        Log.i(TAG, "hclick: ok");
    }

    public void open(View v){
        Log.i(TAG, "open: ");
        Intent first = new Intent(this,opentest.class);
        first.putExtra("dollar_key",d);
        first.putExtra("euro_key",e);
        first.putExtra("won_key",w);

        startActivityForResult(first,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==3){
            d = data.getFloatExtra("dre",0.1f);
            e = data.getFloatExtra("ere",0.1f);
            w = data.getFloatExtra("wre",0.1f);

            Log.i(TAG, "onActivityResult: dre=" + d);
            Log.i(TAG, "onActivityResult: ere=" + e);
            Log.i(TAG, "onActivityResult: wre=" + w);
        }else if(requestCode==1 && resultCode==5){
            Bundle bb = data.getExtras();
            d = bb.getFloat("dre",0.1f);
            e = bb.getFloat("ere",0.1f);
            w = bb.getFloat("wre",0.1f);
            //w = bb.getDouble("wre",1);

            Log.i(TAG, "onActivityResult: dre1=" + d);
            Log.i(TAG, "onActivityResult: ere1=" + e);
            Log.i(TAG, "onActivityResult: wre1=" + w);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG, "线程1启动: ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        URL url = null;
        String html = null;
        try {
            url = new URL("http://hl.anseo.cn/");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            InputStream ins = http.getInputStream();

            html = input2StreamString(ins);
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Message msg = handler.obtainMessage(1);
        msg.obj = html;
        handler.sendMessage(msg);
        Log.i(TAG, "run: ok");
    }

    private String input2StreamString(InputStream ins) throws IOException{
        final int buffersize = 1024;
        final char[] buffer = new char[buffersize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(ins,"UTF-8");
        for(;;){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0) break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}

