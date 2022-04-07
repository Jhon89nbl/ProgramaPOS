package com.jhon89nbl.programpos.model;

public class Category {
    private int idCategory;
    private String category;
    private float maxProfit;
    private float minProfit;

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(float maxProfit) {
        this.maxProfit = maxProfit;
    }

    public float getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(float minProfit) {
        this.minProfit = minProfit;
    }

    public Category(int idCategory, String category, float maxProfit, float minProfit) {
        this.idCategory = idCategory;
        this.category = category;
        this.maxProfit = maxProfit;
        this.minProfit = minProfit;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", category='" + category + '\'' +
                ", maxProfit=" + maxProfit +
                ", minProfit=" + minProfit +
                '}';
    }
}
