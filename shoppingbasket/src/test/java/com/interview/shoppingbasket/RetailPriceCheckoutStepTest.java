package com.interview.shoppingbasket;

import java.util.List;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private PricingService pricingService;
    private double retailTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        List<Promotion> promotions = checkoutContext.getPromotions();
        retailTotal = 0.0;

        for (BasketItem basketItem: basket.getItems()) {
            int quantity = basketItem.getQuantity();
            double price = pricingService.getPrice(basketItem.getProductCode());
            boolean promotionApplied = false;
            for(Promotion promotion : promotions){
                if(promotion.getProductsCodes().contains(basketItem.getProductCode())){
                    price = applyPromotion(promotion,basketItem,price);
                    retailTotal += price;
                    promotionApplied = true;
                    break;
                }
            }
            if(!promotionApplied) {
                basketItem.setProductRetailPrice(price);
                retailTotal += quantity * price;
            }
        }

        checkoutContext.setRetailPriceTotal(retailTotal);
    }

    private double applyPromotion(Promotion promotion, BasketItem basketItem, double price) {
        switch (promotion.getPromotionType()){
            case "2FOR1":
                int free = basketItem.getQuantity() / 2;
                return ((basketItem.getQuantity() - free) * price);
            case "50":
                double discount = (basketItem.getQuantity()) * price * 0.5;
                return discount;
            case "10":
                double discount2 = (basketItem.getQuantity()) *  price * 0.1;
                return (price * basketItem.getQuantity()) - discount2;
            default:
                return price;
        }
    }

}
