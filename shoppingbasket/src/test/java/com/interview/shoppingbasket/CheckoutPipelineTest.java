package com.interview.shoppingbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

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
        Mockito.when(pricingService.getPrice("AAA")).thenReturn(20.0);
        Mockito.when(pricingService.getPrice("BBB")).thenReturn(30.0);

        List<String> productCodes = Arrays.asList("ABC", "XYZ");
        List<String> productCodes2 = Arrays.asList("AAA");
        List<String> productCodes3 = Arrays.asList("BBB");
        List<Promotion> promotions = Arrays.asList(
                new Promotion(productCodes, "2FOR1"),
                new Promotion(productCodes2, "50"),
                new Promotion(productCodes3, "10")
        );

        Mockito.when(promotionsService.getPromotions(Mockito.any(Basket.class))).thenReturn(promotions);

        BasketItem item1 = new BasketItem("ABC", "Product ABC", 2, 0.0);
        BasketItem item2 = new BasketItem("XYZ", "Product XYZ", 1, 0.0);
        BasketItem item3 = new BasketItem("AAA", "Product AAA", 2, 0.0);
        BasketItem item4 = new BasketItem("BBB", "Product BBB", 1, 0.0);

        Basket basket = new Basket();
        basket.add(item1.getProductCode(), item1.getProductName(), item1.getQuantity());
        basket.add(item2.getProductCode(), item2.getProductName(), item2.getQuantity());
        basket.add(item3.getProductCode(), item3.getProductName(), item3.getQuantity());
        basket.add(item4.getProductCode(), item4.getProductName(), item4.getQuantity());

        // Create checkout pipeline
        CheckoutPipeline checkoutPipeline = new CheckoutPipeline();
        checkoutPipeline.addStep(new BasketConsolidationCheckoutStep());
        checkoutPipeline.addStep(new ApplyPromotionsStep(promotionsService));
        checkoutPipeline.addStep(new RetailPriceCheckoutStep(pricingService));

        // Execute checkout steps
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        // Verify the result
        assertEquals(62.0, paymentSummary.getRetailTotal()); // Expected total after applying promotions
    }

}
