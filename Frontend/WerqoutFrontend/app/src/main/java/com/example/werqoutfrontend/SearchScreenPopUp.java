package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class SearchScreenPopUp extends AppCompatActivity {
    private TextView titleText;
    private TextView description1;
    private TextView description2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen_pop_up);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width * .85),(int)(height * .55));

        titleText = findViewById(R.id.title_text_ss_popup);
        description1 = findViewById(R.id.description1_ss_popup);
        description2 = findViewById(R.id.descritption2_ss_popup);

        if(getIntent().getSerializableExtra("searchType").equals("event"))
        {

        }
    }
}