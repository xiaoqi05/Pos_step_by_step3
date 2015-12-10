package com.thoughtworks.pos;

import com.thoughtworks.pos.domain.CartItem;
import com.thoughtworks.pos.domain.Item;
import com.thoughtworks.pos.domain.TotalInfoItem;
import com.thoughtworks.pos.promotion.PromotionManager;

import java.util.HashMap;
import java.util.List;

public final class PosMachine {
    private final List<Item> allItems;

    private PromotionManager promotionManager;

    private HashMap<String, TotalInfoItem> allInfoItem;


    public PosMachine(final List<Item> allItems, HashMap<String, TotalInfoItem> allInfoItem, PromotionManager promotionManager) {
        this.allItems = allItems;
        this.allInfoItem = allInfoItem;
        this.promotionManager = promotionManager;
    }

    public PosMachine(List<Item> allItems) {
        this.allItems = allItems;
    }

    public double calculate(final List<CartItem> cartItems) {
        double total = 0;
        for (CartItem cartItem : cartItems) {
            total += calculateSubtotal(cartItem);
            System.out.print("total" + total);
        }
        return total;
    }

    private double calculateSubtotal(final CartItem cartItem) {
        String barcode = cartItem.getBarcode();
        double originPrice = queryItemPrice(barcode);
        return promotionManager.promotion(barcode, cartItem, originPrice);

    }

    private double queryItemPrice(final String barcode) {
        for (Item item : allItems) {
            if (item.getBarcode().equals(barcode)) {
                return item.getPrice();
            }
        }

        throw new IllegalArgumentException("unknown item");
    }

}
