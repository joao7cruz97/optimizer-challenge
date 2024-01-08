package com.interview.shoppingbasket;

import java.util.List;

public class ApplyPromotionsStep implements CheckoutStep{
    private PromotionsService promotionsService;

    public ApplyPromotionsStep(PromotionsService promotionsService){
        this.promotionsService = promotionsService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        List<Promotion> promotions = promotionsService.getPromotions(basket);
        checkoutContext.setPromotions(promotions);
    }
}
