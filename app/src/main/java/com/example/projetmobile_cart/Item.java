package com.example.projetmobile_cart;

public class Item {
    private int imageResource;
    private String name;
    private String description;
    private String quality;
    private String price;

    public Item(int imageResource, String name, String description, String price,String qua) {
        this.imageResource = imageResource;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quality=qua;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getQuality() {
        return quality;
    }

    public String getPrice() {
        return price;
    }
}


