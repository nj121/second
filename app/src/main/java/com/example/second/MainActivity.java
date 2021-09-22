package com.example.second;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "kkkkk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.title);
        title.setText("IBM计算器");

        EditText h = findViewById(R.id.height);//身高

        EditText w = findViewById(R.id.weight);//体重

        TextView sug = findViewById(R.id.suggest);//建议

        Button btn = findViewById(R.id.btn);
        btn.setText("开始计算");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String he = h.getText().toString();
                double hnum = Double.parseDouble(he);//身高
                String we = w.getText().toString();
                double wnum = Double.parseDouble(we);//体重
                double num = wnum/(hnum*hnum);//IBM指数
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
                String n = df.format(num);
                if(num < 20){
                    sug.setText("您的IBM指数为" + n + "，偏瘦，建议多吃肉。");
                }
                if(20 <= num && num < 25){
                    sug.setText("您的IBM指数为" + n + "，正常，建议保持。");
                }
                if(num >= 25){
                    sug.setText("您的IBM指数为" + n + "，偏胖，建议注意饮食。");
                }

            }
        });
    }
}