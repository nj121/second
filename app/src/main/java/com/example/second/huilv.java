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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class huilv extends AppCompatActivity implements Runnable {

    private static final String TAG = "kkkkk";
    java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
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
                    /*String str = (String)msg.obj;
                    Log.i(TAG, "handleMessage: 消息是 " + str);
                    infrom.setText(str);*/
                    d = Float.parseFloat(df.format(100/Float.parseFloat((String)msg.obj)));
                }else if(msg.what == 2){
                    e = Float.parseFloat(df.format(100/Float.parseFloat((String)msg.obj)));
                }else if(msg.what == 3){
                    w = Float.parseFloat(df.format(100/Float.parseFloat((String)msg.obj)));
                }
                super.handleMessage(msg);
            }
        };

        String date = gettime();
        String date2 = "";
        SharedPreferences sptime = getSharedPreferences("last", Activity.MODE_PRIVATE);
        date2 =sptime.getString("date","");
        Log.i(TAG, "onCreate: date2="+date2);
        if(!date2.equals(date)){
            SharedPreferences.Editor editor = sptime.edit();
            editor.putString("date",date);
            editor.apply();

            Thread thread = new Thread(this);
            thread.start();
        }
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

        /*URL url = null;
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
        }*/

        Document doc = null;
        Message msg;
        try {
            doc = Jsoup.connect("https://usd-cny.com/").get();
            Log.i(TAG, "title: "+doc.title());
            Element firsttable = doc.getElementsByTag("table").first();
            //Log.i(TAG, "run: table="+firsttable);
            Elements trs = firsttable.getElementsByTag("tr");
            trs.remove(0);
            for(Element tr : trs){
                //Log.i(TAG, "run: tr="+tr);
                Elements tds = tr.getElementsByTag("td");
                Element td1 = tds.get(0);
                Element td2 = tds.get(4);
                //Log.i(TAG, "run: tds.size="+tds.size());
                if("美元".equals(td1.text())){
                    sendMessage(td2.text(),1);
                }else if("欧元".equals(td1.text())){
                    sendMessage(td2.text(),2);
                }else if("韩币".equals(td1.text())){
                    sendMessage(td2.text(),3);
                }
            }


            /*for(Element item : firsttable.getElementsByClass("bz")){
                Log.i(TAG, "run: item="+item.text());
            }*/

            /*Elements tds = firsttable.getElementsByTag("td");
                Element dr = tds.get(1);
                Element er = tds.get(6);
                Element wr = tds.get(26);
                Log.i(TAG, "run: dr="+dr.text()+"\t er="+er.text()+"\t wr="+wr.text());*/

            /*Elements ths = firsttable.getElementsByTag("th");
            for(Element th : ths){
                Log.i(TAG, "run: th="+th);
                Log.i(TAG, "run: th.text="+th.text());
                Log.i(TAG, "run: th.html="+th.html());
            }
            Element th2 = ths.get(1);
            Log.i(TAG, "run: th2 = "+th2.text());*/
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        /*Message msg = handler.obtainMessage(1);
        msg.obj = "doc";
        handler.sendMessage(msg);
        Log.i(TAG, "run: ok");*/
    }

    public void sendMessage(String td,int mid){
        Message msg;
        msg = handler.obtainMessage(mid);
        msg.obj = td;
        handler.sendMessage(msg);
        Log.i(TAG, "run: ok"+mid);
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

    public String gettime(){
        SimpleDateFormat s_format = new SimpleDateFormat("yyyyMMdd");
        return s_format.format(new Date());
    }

}

