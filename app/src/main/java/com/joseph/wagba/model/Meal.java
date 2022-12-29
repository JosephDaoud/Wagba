package com.joseph.wagba.model;

public class Meal {

    String name;
    String content;
    String price;
    String imagePos;

    public Meal(String name, String content, String price, String imagePos) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.imagePos = imagePos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImagePos() {
        return imagePos;
    }

    public void setImagePos(String imagePos) {
        this.imagePos = imagePos;
    }
}
