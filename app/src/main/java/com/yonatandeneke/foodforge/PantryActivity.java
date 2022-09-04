package com.yonatandeneke.foodforge;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private ArrayList<String> pantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Your Pantry");
        setContentView(R.layout.activity_pantry);

        loadPantry();

        for (String i : pantry){
            TextView textView = new TextView(PantryActivity.this);
            textView.setTextColor(Color.WHITE);
            textView.setText(i);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pantryLayout);

            linearLayout.addView(textView);
        }

        Button cb = (Button) findViewById(R.id.Add);
        cb.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextInputEditText id = (TextInputEditText) findViewById(R.id.enter);
                String text = id.getText().toString();
                pantry.add(text);
                TextView textView = new TextView(PantryActivity.this);
                textView.setTextColor(Color.WHITE);
                textView.setText(text);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.pantryLayout);

                linearLayout.addView(textView);
            }
        });

    }

    private void loadPantry(){
        pantry = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("pantry.txt")));

            String line;
            while ((line = reader.readLine()) != null) {
                pantry.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}