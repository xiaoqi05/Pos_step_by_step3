package com.thoughtworks.pos.promotion;

import com.thoughtworks.pos.domain.CartItem;

public class NoPromotion implements Promotion {

    @Override
    public double promotion(CartItem cartItem, double originPrice) {
        return cartItem.getQuantity() * originPrice - originPrice ;
    }


}
