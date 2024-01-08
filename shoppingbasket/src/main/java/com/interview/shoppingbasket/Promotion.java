package com.interview.shoppingbasket;

import java.util.List;

public class Promotion {
    private List<String> productsCodes;
    private String promotionType;

    public Promotion(List<String> productsCodes, String promotionType){
        this.productsCodes = productsCodes;
        this.promotionType = promotionType;
    }

    public Promotion() {

    }

    public String getPromotionType(){
        return promotionType;
    }

    public List<String> getProductsCodes() {
        return productsCodes;
    }
}
