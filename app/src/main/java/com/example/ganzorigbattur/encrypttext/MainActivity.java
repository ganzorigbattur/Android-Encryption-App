package com.example.ganzorigbattur.encrypttext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    // when button clicek go to secondActivity
    Button gotoSecond;
    public void init(){
        gotoSecond = (Button) findViewById(R.id.gotoSecond);
        gotoSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oneToTwo = new Intent(MainActivity.this,secondActivity.class);
                startActivity(oneToTwo);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
