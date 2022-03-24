package com.jhon89nbl.programpos.model;


import javafx.scene.image.ImageView;

public class Product {
    private String name;
    private String code;
    private String description;
    private String category;
    private String provider;
    private int amount;
    private double cost;
    private int salePrice;
    private boolean iva;
    private float ivaPercent;
    private ImageView photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isIva() {
        return iva;
    }

    public void setIva(boolean iva) {
        this.iva = iva;
    }

    public float getIvaPercent() {
        return ivaPercent;
    }

    public void setIvaPercent(float ivaPercent) {
        this.ivaPercent = ivaPercent;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        ImageView image = new ImageView(photo);
        image.setFitHeight(100);
        image.setFitWidth(70);
        this.photo = image;
    }

    /*public Product(String name, String code, String description, String category, String provider, int amount, double cost, int salePrice, boolean iva, float ivaPercent, String photo) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.category = category;
        this.provider = provider;
        this.amount = amount;
        this.cost = cost;
        this.salePrice = salePrice;
        this.iva = iva;
        this.ivaPercent = ivaPercent;
        setPhoto(photo);
    }*/

    public Product() {
    }
}
