package com.joseph.wagba.model;

public class PastOrder {

    String restaurantName;
    String status;
    Integer imagePos;
    String date;

    public PastOrder(String restaurantName, String status, Integer imagePos, String date) {
        this.restaurantName = restaurantName;
        this.status = status;
        this.imagePos = imagePos;
        this.date = date;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getImagePos() {
        return imagePos;
    }

    public void setImagePos(Integer imagePos) {
        this.imagePos = imagePos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
