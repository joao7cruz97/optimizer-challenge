package com.interview.shoppingbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutPipelineTest {

    CheckoutPipeline checkoutPipeline;

    @Mock
    Basket basket;

    @Mock
    CheckoutStep checkoutStep1;

    @Mock
    CheckoutStep checkoutStep2;

    @BeforeEach
    void setup() {
        checkoutPipeline = new CheckoutPipeline();
        PricingService pricingService = Mockito.mock(PricingService.class);
        PromotionsService promotionsService = Mockito.mock(PromotionsService.class);

        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        basket.add("productCode", "myProduct", 10);
        basket.add("productCode2", "myProduct2", 10);
        basket.add("productCode3", "myProduct3", 10);

        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        // Adicionar elementos à lista
        list.add("1");
        list.add("2");
        list2.add("3");
        list3.add("4");

        Promotion promo = new Promotion(list,"2FOR1");
        Promotion promo2 = new Promotion(list2,"50%");
        Promotion promo3 = new Promotion(list3,"10%");


        RetailPriceCheckoutStep checkoutStep = new RetailPriceCheckoutStep(pricingService);
        ApplyPromotionsStep promotionStep = new ApplyPromotionsStep(promotionsService);
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {
        // Exercise - implement testing passing through all checkout steps
        checkoutPipeline = new CheckoutPipeline();

        PricingService pricingService = Mockito.mock(PricingService.class);
        PromotionsService promotionsService = Mockito.mock(PromotionsService.class);

        Mockito.when(pricingService.getPrice("ABC")).thenReturn(10.0);
        Mockito.when(pricingService.getPrice("XYZ")).thenReturn(15.0);

        List<String> productCodes = Arrays.asList("ABC", "XYZ");
        List<Promotion> promotions = Arrays.asList(
                new Promotion(productCodes, "2FOR1"),
                new Promotion(Arrays.asList("ABC"), "50%"),
                new Promotion(Arrays.asList("XYZ"), "10%")
        );

        Mockito.when(promotionsService.getPromotions(Mockito.any(Basket.class))).thenReturn(promotions);

        // Create basket items
        BasketItem item1 = new BasketItem("ABC", "Product ABC", 2, 0.0);
        BasketItem item2 = new BasketItem("XYZ", "Product XYZ", 1, 0.0);

        // Create basket
        Basket basket = new Basket();
        basket.add(item1.getProductCode(), item1.getProductName(), item1.getQuantity());
        basket.add(item2.getProductCode(), item2.getProductName(), item2.getQuantity());

        // Create checkout pipeline
        CheckoutPipeline checkoutPipeline = new CheckoutPipeline();
        checkoutPipeline.addStep(new BasketConsolidationCheckoutStep());
        checkoutPipeline.addStep(new ApplyPromotionsStep(promotionsService));
        checkoutPipeline.addStep(new RetailPriceCheckoutStep(pricingService));

        // Execute checkout steps
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        // Verify the result
        assertEquals(25.0, paymentSummary.getRetailTotal()); // Expected total after applying promotions
    }

}
