package com.joseph.wagba.model;

public class BestPicks {

    String name;
    String time;
    String price;
    String restaurant;
    String imagePos;


    public BestPicks(String name, String time, String price, String restaurant, String imagePos) {
        this.name = name;
        this.time = time;
        this.price = price;
        this.restaurant = restaurant;
        this.imagePos = imagePos;
    }

    public String getImageUrl() {
        return imagePos;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imagePos) {
        this.imagePos = imagePos;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(String price) {
        this.price = price;
    }



}
