package com.joseph.wagba.model;

import android.content.Intent;

import java.io.Serializable;

public class CartItem implements Serializable {

    String name;
    String content;
    String totalPrice;
    Integer quantity;

    public CartItem(String name, String content, String totalPrice, Integer quantity) {
        this.name = name;
        this.content = content;
        this.totalPrice = totalPrice;
        this.quantity = quantity;

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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
