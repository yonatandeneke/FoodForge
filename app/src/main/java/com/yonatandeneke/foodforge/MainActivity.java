package com.yonatandeneke.foodforge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RecipeModel> loadedRecipes;
    private ArrayList<String> pantry;
    private ArrayList<RecipeModel> suitable;
    private int onDisplay;
    private AssetManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        loadedRecipes = new ArrayList<>();
        pantry = new ArrayList<>();
        onDisplay = 0;
        am = getAssets();

        loadRecipes();
        loadPantry();

        if (!getSuitableRecipes().isEmpty()) {
            getSuitableRecipes();
        }
        suitable = getSuitableRecipes();
        RecipeModel current = suitable.get(onDisplay);

        displayRecipe(current);


        ImageButton clickButton = (ImageButton) findViewById(R.id.display);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showRecipe(suitable.get(onDisplay));
            }
        });

        Button cb = (Button) findViewById(R.id.NextRecipe);
        cb.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextRecipe();
            }
        });

        Button pantry = (Button) findViewById(R.id.PANTRY);
        pantry.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPantry();
            }
        });


    }



    private void loadRecipes(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("recipes.txt")));

            RecipeModel recipe = new RecipeModel();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("end")){
                    loadedRecipes.add(recipe);
                    recipe = new RecipeModel();
                }
                else if (line.contains("name: ")){
                    recipe.setName(line.substring(6));
                }
                else if (line.contains("ingredients: ")){
                    ArrayList<String> stuff = new ArrayList<>();
                    String filtered = line.substring(13);
                    String[] split = filtered.split(", ");
                    recipe.setIngredients(Arrays.asList(split));
                }
                else if (line.contains("steps: ")){
                    ArrayList<String> steps = new ArrayList<>();
                    String filtered = line.substring(7);
                    String[] split = filtered.split("\\*");
                    recipe.setSteps(Arrays.asList(split));
                }

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

    private void loadPantry(){
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

    private ArrayList<RecipeModel> getSuitableRecipes(){
        ArrayList<RecipeModel> suitable = new ArrayList<>();
        for (RecipeModel r : loadedRecipes){
            if (pantry.containsAll(r.getIngredients()) || pantry.isEmpty()){
                suitable.add(r);
            }
        }
        return suitable;
    }

    private void showRecipe(RecipeModel r){
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("name", r.getName());
        intent.putExtra("ingredients", r.getIngredients().toArray());
        intent.putExtra("steps", r.getSteps().toArray());
        startActivity(intent);

    }

    private void showPantry(){
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent);
    }

    private void displayRecipe(RecipeModel r){
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(r.getName());

        //TODO: ADD IMAGES
        ImageButton imageButton = (ImageButton) findViewById(R.id.display);
        try {
            String[] images = am.list("img");
            for (String name : images){
                if (name.substring(0, name.length()-4).equalsIgnoreCase(r.getName())){
                    InputStream ims = getAssets().open("img/" + name);
                    Drawable d = Drawable.createFromStream(ims, null);
                    imageButton.setImageDrawable(d);
                }
            }

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void nextRecipe(){
        onDisplay++;
        if (onDisplay >= suitable.size()){
            onDisplay = 0;
        }
        displayRecipe(suitable.get(onDisplay));
    }
}