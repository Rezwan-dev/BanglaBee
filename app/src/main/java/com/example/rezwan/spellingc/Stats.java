package com.example.rezwan.spellingc;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences sharedPref = getSharedPreferences("spellingC",Context.MODE_PRIVATE);
        ((TextView)findViewById(R.id.name_tv_stats)).setText(sharedPref.getString("name", "Player One"));
    }
}
