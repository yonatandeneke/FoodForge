package com.yonatandeneke.foodforge;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {

    private String name;
    private List<String> ingredients;
    private List<String> steps;
    private int imgId;

    public RecipeModel(String name, List<String> ingredients, List<String> steps, int imgId) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imgId = imgId;
    }

    public RecipeModel() {
        this.name = "";
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.imgId = Integer.MIN_VALUE;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }
}
