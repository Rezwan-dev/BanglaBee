package com.example.rezwan.spellingc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hariofspades.incdeclibrary.IncDecCircular;
import com.hariofspades.incdeclibrary.IncDecImageButton;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    int dificulty;
    int quizSize;
    private IncDecImageButton incdec;
    private IncDecImageButton incdec2;
    private TextView name_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedPref = getSharedPreferences("spellingC",Context.MODE_PRIVATE);
        dificulty = sharedPref.getInt("dificulty", 1);
        quizSize  = sharedPref.getInt("quizSize",5);
        ArrayList<String> values=new ArrayList<>();
        values.add("Easy");
        values.add("Medium");
        values.add("Hard");

        incdec=(IncDecImageButton)findViewById(R.id.incdec);
        incdec.setConfiguration(LinearLayout.HORIZONTAL,IncDecCircular.TYPE_ARRAY,
                IncDecCircular.DECREMENT,IncDecCircular.INCREMENT);
        incdec.setArrayList(values);
        incdec.setArrayIndexes(0,2,1);
        incdec.enableLongPress(true,true,500);
        incdec.setArrayValue(dificulty-1);
        incdec.setOnValueChangeListener(new IncDecImageButton.OnValueChangeListener() {
            @Override
            public void onValueChange(IncDecImageButton view, float oldValue, float newValue) {
                dificulty  = incdec.getCurrentIndex()+1;
            }
        });

        ArrayList<String> values2=new ArrayList<>();
        values2.add("5");
        values2.add("10");
        values2.add("15");

        incdec2=(IncDecImageButton)findViewById(R.id.incdec2);
        incdec2.setConfiguration(LinearLayout.HORIZONTAL,IncDecCircular.TYPE_ARRAY,
                IncDecCircular.DECREMENT,IncDecCircular.INCREMENT);
        incdec2.setArrayList(values2);
        incdec2.setArrayIndexes(0,2,1);
        incdec2.enableLongPress(true,true,500);
        incdec2.setArrayValue((quizSize/5) - 1);
        incdec2.setOnValueChangeListener(new IncDecImageButton.OnValueChangeListener() {
            @Override
            public void onValueChange(IncDecImageButton view, float oldValue, float newValue) {
                quizSize = (incdec2.getCurrentIndex()+1)*5;
                Log.e("quizSize", ""+quizSize);
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name_tv  = (TextView)findViewById(R.id.name_tv);
        name_tv.setText(sharedPref.getString("name","Player One"));
        name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("Enter player name");

// Set up the input
                final EditText input = new EditText(Settings.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String name = input.getText().toString();
                        if (name.trim().length()<1){
                            return;
                        }
                        SharedPreferences sharedPref = getSharedPreferences("spellingC",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("name", name);
                        editor.commit();
                        name_tv.setText(name);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        findViewById(R.id.aboutUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Settings.this, About.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPref = getSharedPreferences("spellingC",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("dificulty", dificulty);
        editor.putInt("quizSize", quizSize);
        editor.commit();
        super.onDestroy();

    }
}
