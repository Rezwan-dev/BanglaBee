package com.codestation.banglabee;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hariofspades.incdeclibrary.IncDecCircular;
import com.hariofspades.incdeclibrary.IncDecImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    int dificulty;
    int quizSize;
    private IncDecImageButton incdec;
    private IncDecImageButton incdec2;
    private TextView name_tv;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private RoundedImage profile_pic;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        dificulty = sharedPref.getInt("dificulty", 1);
        quizSize = sharedPref.getInt("quizSize", 5);
        String filePath = sharedPref.getString("saved", "");
        profile_pic = (RoundedImage) findViewById(R.id.profile_pic_s);
        if (filePath.length() > 0) {
            File imgFile = new File(filePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                profile_pic.setImageBitmap(myBitmap);

            }
        }
        ArrayList<String> values = new ArrayList<>();
        values.add("Easy");
        values.add("Medium");
        values.add("Hard");

        incdec = (IncDecImageButton) findViewById(R.id.incdec);
        incdec.setConfiguration(LinearLayout.HORIZONTAL, IncDecCircular.TYPE_ARRAY,
                IncDecCircular.DECREMENT, IncDecCircular.INCREMENT);
        incdec.setArrayList(values);
        incdec.setArrayIndexes(0, 2, 1);
        incdec.enableLongPress(true, true, 500);
        incdec.setArrayValue(dificulty - 1);
        incdec.setOnValueChangeListener(new IncDecImageButton.OnValueChangeListener() {
            @Override
            public void onValueChange(IncDecImageButton view, float oldValue, float newValue) {
                dificulty = incdec.getCurrentIndex() + 1;
            }
        });

        ArrayList<String> values2 = new ArrayList<>();
        values2.add("5");
        values2.add("10");
        values2.add("15");

        incdec2 = (IncDecImageButton) findViewById(R.id.incdec2);
        incdec2.setConfiguration(LinearLayout.HORIZONTAL, IncDecCircular.TYPE_ARRAY,
                IncDecCircular.DECREMENT, IncDecCircular.INCREMENT);
        incdec2.setArrayList(values2);
        incdec2.setArrayIndexes(0, 2, 1);
        incdec2.enableLongPress(true, true, 500);
        incdec2.setArrayValue((quizSize / 5) - 1);
        incdec2.setOnValueChangeListener(new IncDecImageButton.OnValueChangeListener() {
            @Override
            public void onValueChange(IncDecImageButton view, float oldValue, float newValue) {
                quizSize = (incdec2.getCurrentIndex() + 1) * 5;
                Log.e("quizSize", "" + quizSize);
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name_tv = (TextView) findViewById(R.id.name_tv);
        name_tv.setText(sharedPref.getString("name", "Player One"));
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
                        if (name.trim().length() < 1) {
                            return;
                        }
                        setName(name);

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
                Intent intent = new Intent(Settings.this, About.class);
                startActivity(intent);
            }
        });
        callbackManager = CallbackManager.Factory.create();
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                if (currentProfile != null) {
                    String name = currentProfile.getName();
                    Uri uri = currentProfile.getProfilePictureUri(100, 100);
                    Log.e("TAGGY", name);
                    Log.e("TAGGY", uri.toString());
                    setName(name);
                    getImage(uri);

                }
            }
        };
        profileTracker.startTracking();

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("TAGGY", "Facebook login success");

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("dificulty", dificulty);
        editor.putInt("quizSize", quizSize);
        editor.apply();
        profileTracker.stopTracking();
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setName(final String name) {
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", name);
        editor.apply();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name_tv.setText(name);
            }
        });

    }

    private void saveToInternalSorage(final Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to
            // the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("saved", mypath.getAbsolutePath());
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                profile_pic.setImageBitmap(bitmapImage);
            }
        });
    }

    public void getImage(Uri uri) {
        try {
            Glide.with(this)
                    .load(uri)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            saveToInternalSorage(resource);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
