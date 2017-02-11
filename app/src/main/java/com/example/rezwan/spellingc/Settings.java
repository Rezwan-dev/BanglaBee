package com.example.rezwan.spellingc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hariofspades.incdeclibrary.IncDecCircular;
import com.hariofspades.incdeclibrary.IncDecImageButton;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ArrayList<String> values=new ArrayList<>();
        values.add("Easy");
        values.add("Medium");
        values.add("Hard");

        IncDecImageButton incdec=(IncDecImageButton)findViewById(R.id.incdec);
        incdec.setConfiguration(LinearLayout.HORIZONTAL,IncDecCircular.TYPE_ARRAY,
                IncDecCircular.DECREMENT,IncDecCircular.INCREMENT);
        incdec.setArrayList(values);
        incdec.setArrayIndexes(0,2,1);
        incdec.enableLongPress(true,true,500);

        ArrayList<String> values2=new ArrayList<>();
        values2.add("5");
        values2.add("10");
        values2.add("15");

        IncDecImageButton incdec2=(IncDecImageButton)findViewById(R.id.incdec2);
        incdec2.setConfiguration(LinearLayout.HORIZONTAL,IncDecCircular.TYPE_ARRAY,
                IncDecCircular.DECREMENT,IncDecCircular.INCREMENT);
        incdec2.setArrayList(values2);
        incdec2.setArrayIndexes(0,2,1);
        incdec2.enableLongPress(true,true,500);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
