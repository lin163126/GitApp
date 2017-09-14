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
        final EditText jin = (EditText) findViewById(R.id.jin);
        final TextView hou = (TextView) findViewById(R.id.hou);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float ikou = 0;
                float iji = 0;
                float ibu = 0;
                float ijin = 0;

                ikou = Float.parseFloat(kou.getText().toString());
                iji = Float.parseFloat(ji.getText().toString());
                ibu = Float.parseFloat(bu.getText().toString());
                ijin = (float) (0.01 * Float.parseFloat(jin.getText().toString()));

                float x = ikou * ijin + 10;

                float y = (iji + ibu) - x;

                float z = (y - 3500);

                float a = 0;

                float b = 0;

                if (z <= 1500) {
                    a = (float) 0.03;
                    b = 0;
                } else if (z > 1500 && z <= 4500) {
                    a = (float) 0.1;
                    b = 105;
                } else if (z > 4500 && z <= 9000) {
                    a = (float) 0.2;
                    b = 555;
                } else if (z > 9000 && z <= 35000) {
                    a = (float) 0.25;
                    b = 1005;
                } else if (z > 35000 && z <= 55000) {
                    a = (float) 0.30;
                    b = 2755;
                } else if (z > 55000 && z <= 80000) {
                    a = (float) 0.35;
                    b = 5505;
                } else if (z > 80000) {
                    a = (float) 0.45;
                    b = 13505;
                }

                float ishui = z * a - b;

                float tt = y - ishui - 5;

                hou.setText(tt + "");
            }
        });
    }
}
