package com.thoughtworks.pos.promotion;

import com.thoughtworks.pos.domain.CartItem;

/**
 * 策略模式
 */
public interface Promotion {
    double promotion(CartItem cartItem, double originPrice);
}
