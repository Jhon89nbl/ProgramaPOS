package com.jhon89nbl.programpos.model;


import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;



public class Product {
    private String name;
    private long code;
    private String description;
    private String category;
    private String provider;
    private int amount;
    private double cost;
    private Double salePrice;
    private boolean iva;
    private float ivaPercent;
    private ImageView photo;
    private int amountSale;

    public int getAmountSale() {
        return amountSale;
    }

    public void setAmountSale(int amountSale) {
        this.amountSale = amountSale;
    }



    public boolean isChargePhoto() {
        return chargePhoto;
    }

    public void setChargePhoto(boolean chargePhoto) {
        this.chargePhoto = chargePhoto;
    }

    private boolean chargePhoto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
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
        image.setFitHeight(80);
        image.setFitWidth(120);
        this.photo = image;
    }
    public void setImage(ImageView image){
        this.photo= image;
    }


    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", provider='" + provider + '\'' +
                ", amount=" + amount +
                ", cost=" + cost +
                ", salePrice=" + salePrice +
                ", iva=" + iva +
                ", ivaPercent=" + ivaPercent +
                ", photo=" + photo +
                ", amountSale=" + amountSale +
                ", chargePhoto=" + chargePhoto +
                '}';
    }
}
