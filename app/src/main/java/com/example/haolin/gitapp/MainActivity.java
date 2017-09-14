package com.example.haolin.gitapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText kou = (EditText) findViewById(R.id.kou);
        final EditText ji = (EditText) findViewById(R.id.ji);
        final EditText bu = (EditText) findViewById(R.id.bu);
        final EditText gong = (EditText) findViewById(R.id.gong);
        final EditText yang = (EditText) findViewById(R.id.yang);
        final EditText shi = (EditText) findViewById(R.id.shi);
        final EditText yi = (EditText) findViewById(R.id.yi);
        final EditText shui = (EditText) findViewById(R.id.shui);
        final TextView hou = (TextView) findViewById(R.id.hou);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float ikou = Integer.parseInt(kou.getText().toString());
                float iji = Integer.parseInt(ji.getText().toString());
                float ibu = Integer.parseInt(bu.getText().toString());
                float igong = (float) (0.01 * Integer.parseInt(gong.getText().toString()));
                float iyang = (float) (0.01 * Integer.parseInt(yang.getText().toString()));
                float ishi = (float) (0.01 * Float.parseFloat(shi.getText().toString()));
                float iyi = (float) (0.01 * Integer.parseInt(yi.getText().toString()));
                float ishui = 0;

                float x = ikou * igong + ikou * iyang * ikou * ishi + ikou * iyi + 10;

                float y = (iji + ibu) - x;

                float z = (y - 3500);

                float a = (float) 0.01;

                float b = 105;

                if (z > 4500) {
                    a = (float) 0.02;
                    b = 555;
                }

                ishui = z * a - b;

                float tt = iji + ibu - x - ishui;

                hou.setText(tt+"");
            }
        });

    }
}
