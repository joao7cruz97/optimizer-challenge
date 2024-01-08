package com.interview.shoppingbasket;

import lombok.Data;

@Data
public class BasketItem {
    private String productCode;
    private String productName;
    private int quantity;
    private double productRetailPrice;

    public BasketItem(String productCode, String productName, int quantity, double productRetailPrice){
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.productRetailPrice = productRetailPrice;
    }

    public BasketItem(){
    }
}
