package com.codestation.banglabee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.codestation.banglabee.mashroor.databasemanagement.DataFetcer;
import com.codestation.banglabee.mashroor.databasemanagement.WordModel;
import com.codestation.banglabee.stack.SwipeStack;
import com.codestation.banglabee.stack.SwipeStackAdapter;
import com.cunoraz.gifview.library.GifView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Study extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    private SwipeStack mSwipeStack;
    private SwipeStackAdapter mAdapter;
    private ArrayList<String> mData;
    private GifView gifView1;

    public static final String isStudy = "isStudy";
    private LinearLayout searchLL;
    private EditText searchET;
    private ImageView searchBtn;
    private RecyclerView searchRV;
    private boolean isSearchOpen;
    private SearchAdapter searchAdapter;
    private Handler searchHandler;
    private String currentSearch = "";
    private DataFetcer dataFetcer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        mSwipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        int dificulty = sharedPref.getInt("dificulty", 1);
        int quizSize = sharedPref.getInt("quizSize", 5);
        // fillWithTestData();
        dataFetcer = new DataFetcer(this);
        if (getIntent().getBooleanExtra("isStudy", false)) {
            fillWithStudyWrongData();
        } else {
            mAdapter = new SwipeStackAdapter(dataFetcer.fetchData(quizSize, dificulty), this);
            mSwipeStack.setAdapter(mAdapter);
            gifView1 = (GifView) findViewById(R.id.gif);
            mSwipeStack.setListener(this);
        }

        searchLL = (LinearLayout) findViewById(R.id.searchLL);
        searchET = (EditText) findViewById(R.id.searchET);
        searchRV = (RecyclerView) findViewById(R.id.searchRV);
        searchBtn = (ImageView) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchOpen) {
                    closeSearch();
                } else {
                    openSearch();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (isSearchOpen) {
            closeSearch();
            return;
        }
        super.onBackPressed();
    }

    private void openSearch() {
        isSearchOpen = true;
        ViewGroup.LayoutParams parms = searchLL.getLayoutParams();
        parms.width = ViewGroup.LayoutParams.MATCH_PARENT;
        searchLL.setLayoutParams(parms);
        searchET.setVisibility(View.VISIBLE);
        searchRV.setVisibility(View.VISIBLE);
        searchBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchET, InputMethodManager.SHOW_IMPLICIT);
        initSearch();
    }

    private void closeSearch() {
        isSearchOpen = false;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchET.getWindowToken(), 0);
        searchRV.removeAllViewsInLayout();
        searchET.removeTextChangedListener(textWatcher);
        ViewGroup.LayoutParams parms = searchLL.getLayoutParams();
        parms.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        searchLL.setLayoutParams(parms);
        searchET.setText("");
        searchET.setVisibility(View.GONE);
        searchRV.setVisibility(View.GONE);
        searchBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
    }

    protected TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Log.e("TAGGY", "beforeTextChanged - " + s);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           // Log.e("TAGGY", "onTextChanged - " + s);
            newIncomingSearch(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            //Log.e("TAGGY", "afterTextChanged - " + s);
        }
    };

    public void newIncomingSearch(String query){
        if(searchHandler == null){
            searchHandler  =  new Handler();
        }
        if(!currentSearch.equalsIgnoreCase(query)){
            currentSearch  = query;
            searchHandler.removeCallbacksAndMessages(null);
            searchHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    performSearch(currentSearch);
                }
            },1000);
        }

    }

    private void performSearch(String currentSearch) {
        if(isSearchOpen) {
            if (currentSearch.length() < 3 && searchAdapter.getItemCount() > 0) {
                searchAdapter.newData(new ArrayList<WordModel>());
            }else {
                searchAdapter.newData(dataFetcer.fetchDataByParrern(currentSearch));
            }

        }
    }

    private void initSearch() {
        searchRV.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this, new ArrayList<WordModel>());
        searchRV.setAdapter(searchAdapter);
        searchET.addTextChangedListener(textWatcher);
    }

    private void fillWithStudyWrongData() {
        ArrayList<Integer> integers = new bbDBHelper(this).getAllWrong();
        DataFetcer dataFetcer = new DataFetcer(this);
        if (integers.size() < 1) {
            return;
        }
        Set<Integer> set = new HashSet<Integer>();
        int max_size = 10;
        if (integers.size() < max_size) {
            max_size = integers.size();
        }
        Random r = new Random();
        while (set.size() < max_size) {
            set.add(r.nextInt(integers.size()));
        }
        ArrayList<WordModel> wordModels = new ArrayList<>();
        for (Integer integer : set) {
            String number = "" + integers.get(integer);
            int serial = Integer.parseInt(number.substring(0, number.length() - 1));
            int dificulty = Integer.parseInt("" + number.charAt(number.length() - 1));
            wordModels.add(dataFetcer.fetchSingleData(serial, dificulty));
        }
        mAdapter = new SwipeStackAdapter(wordModels, this);
        mSwipeStack.setAdapter(mAdapter);
        gifView1 = (GifView) findViewById(R.id.gif);
        mSwipeStack.setListener(this);
    }

    private void fillWithTestData() {
        mData = new ArrayList<>();
        for (int x = 0; x < 15; x++) {
            mData.add("Bae" + " " + (x + 1));
        }
    }

    @Override
    public void onViewSwipedToLeft(int position) {
        gifView1.play();
    }

    @Override
    public void onViewSwipedToRight(int position) {
        gifView1.play();
    }

    @Override
    public void onStackEmpty() {
        Toast.makeText(this, "Great Work", Toast.LENGTH_LONG).show();
    }
}
