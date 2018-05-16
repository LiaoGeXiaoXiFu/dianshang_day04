package com.example.zhoukao_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyView titleView;
    private int num = 0;
    private ShowView showView;
    private boolean isAddState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleView = findViewById(R.id.titleView);
        showView = findViewById(R.id.showView);

        Button titleBarLeftBtn = titleView.getTitleBarLeftBtn();
        titleBarLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num>0){
                    num--;
                    titleView.setTitleText(num);
                    isAddState = true;
                    showView.changeShow(num);
                }else {
                }
            }
        });

        Button titleBarRightBtn = titleView.getTitleBarRightBtn();
        titleBarRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                titleView.setTitleText(num);
                isAddState = true;
                showView.changeShow(num);
            }
        });

    }
}
