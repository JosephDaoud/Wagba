package com.joseph.wagba.model;

public class PastOrder {

    String restaurantName;
    String status;
    String orderid;
    String date;

    public PastOrder(String restaurantName, String status, String orderid, String date) {
        this.restaurantName = restaurantName;
        this.status = status;
        this.orderid = orderid;
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

    public String getImagePos() {
        return orderid;
    }

    public void setImagePos(String orderid) {
        this.orderid = orderid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
