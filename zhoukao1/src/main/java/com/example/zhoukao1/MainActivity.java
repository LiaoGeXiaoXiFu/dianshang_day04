package com.example.zhoukao1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AddSubtractView asv;
    private LadderView ladder;
    private ViewGroup.MarginLayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asv = findViewById(R.id.asv);
        asv.setTv_num(3);
        setListener();
        initData();
    }

    private void initData() {
        ladder = findViewById(R.id.ladder);
        int tv_num = asv.getTv_num();
        lp = new ViewGroup.MarginLayoutParams(100, 50);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 1; i <= tv_num; i++) {
            TextView textView = new TextView(this);
            textView.setText("第" + i + "条数据");
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.GREEN);
            ladder.addView(textView, lp);
        }
    }

    private void setListener() {
        asv.setOnAddSubtractListener(new AddSubtractView.OnAddSubtractListener() {
            @Override
            public void onClickAdd(View view) {
                int num = asv.getTv_num();
                if (num<10){
                    num++;
                    asv.setTv_num(num);
                    TextView textView = ladder.addItem(num);
                    ladder.addView(textView,lp);
                }
            }

            @Override
            public void onClickSubtract(View view) {
                int num = asv.getTv_num();
                if (num > 0) {
                    num--;
                    asv.setTv_num(num);
                    TextView textView = ladder.subtractItem(asv.getTv_num());
                    ladder.removeView(textView);
                }
            }
        });
    }
}
