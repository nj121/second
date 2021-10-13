package com.example.second;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RateTask implements Runnable{

    private Handler handler;
    private static final String TAG = "lll";

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        Log.i(TAG, "线程启动: ");
        Document doc = null;
        Message msg;
        try {
            List<String> list1 = new ArrayList<String>();
            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "title: "+doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table=tables.get(1);
            Log.i(TAG, "run: table="+table);
            Elements trs = table.getElementsByTag("tr");
            trs.remove(0);
            Log.i(TAG, "size ="+trs.size());
            for(Element tr : trs){
                //Log.i(TAG, "run: tr="+tr);
                Elements tds = tr.getElementsByTag("td");
                Element td1 = tds.get(0);
                Element td2 = tds.get(4);
                list1.add(td1.text()+"--->"+td2.text());
            }
            sendMessage(list1,1);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    public void sendMessage(List<String> td, int mid){
        Message msg;
        msg = handler.obtainMessage(mid);
        msg.obj = td;
        handler.sendMessage(msg);
    }


}
