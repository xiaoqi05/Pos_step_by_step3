package com.thoughtworks.pos.promotion;

import com.thoughtworks.pos.domain.CartItem;

public class DiscountPromotion implements Promotion {
    private double discountRatio;

    public DiscountPromotion(double discountRatio) {
        this.discountRatio = discountRatio / 100d;
    }


    @Override
    public double promotion(CartItem cartItem, double originPrice) {
        return cartItem.getQuantity() * originPrice * discountRatio;
    }


}
