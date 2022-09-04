package com.yonatandeneke.foodforge;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setTitle(extras.getString("name"));

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.contents);

        String[] ingredients = extras.getStringArray("ingredients");
        TextView tv1 = new TextView(RecipeActivity.this);
        tv1.setTextColor(Color.WHITE);
        tv1.setTextSize(20);
        tv1.setText("Ingredients: " + stripBrackets(Arrays.toString(ingredients)));
        linearLayout.addView(tv1);

        String[] steps = extras.getStringArray("steps");
        TextView tv2 = new TextView(RecipeActivity.this);
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(20);
        tv2.setText("Directions: " + stripBrackets(Arrays.toString(steps)));
        linearLayout.addView(tv2);


    }

    private String stripBrackets(String s){
        String temp = s;
        temp = temp.substring(1);
        temp = temp.substring(0, temp.length()-1);
        return temp;
    }
}