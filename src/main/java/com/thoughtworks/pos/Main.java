package com.thoughtworks.pos;

import com.thoughtworks.pos.domain.*;
import com.thoughtworks.pos.parser.DiscountItemParser;
import com.thoughtworks.pos.parser.ItemParser;
import com.thoughtworks.pos.parser.SecondHalfPriceParser;
import com.thoughtworks.pos.parser.ShoppingCartParser;
import com.thoughtworks.pos.promotion.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<String, TotalInfoItem> allInfoItem = new HashMap<>();
        ItemParser itemParser = new ItemParser();
        //存储讲所有商品信息
        List<Item> allItems = itemParser.parse(ShopData.ITEMS_DATA);
        for (Item item : allItems) {
            TotalInfoItem totalInfoItem = new TotalInfoItem(item.getBarcode(), item.getPrice());
            allInfoItem.put(item.getBarcode(), totalInfoItem);
        }
        //添加购物车信息
        ShoppingCartParser shoppingCartParser = new ShoppingCartParser();
        List<CartItem> cartItems = shoppingCartParser.parse(ShopData.SHOPPING_CART_DATA);
        for (CartItem item : cartItems) {
            if (allInfoItem.get(item.getBarcode()) != null) {
                TotalInfoItem totalInfoItem = allInfoItem.get(item.getBarcode());
                totalInfoItem.setItemNum(item.getQuantity());
                allInfoItem.put(item.getBarcode(), totalInfoItem);
            }
        }
        //添加所有第二件折扣信息
        List<SecondHalfPriceItem> secondHalfPriceItems = new SecondHalfPriceParser().parse(ShopData.SECOND_HALF_PRICE_PROMOTION);
        for (SecondHalfPriceItem item : secondHalfPriceItems) {
            if (allInfoItem.get(item.getBarcode()) != null) {
                TotalInfoItem totalInfoItem = allInfoItem.get(item.getBarcode());
                totalInfoItem.setHasSecondHalfPrice(true);
                allInfoItem.put(item.getBarcode(), totalInfoItem);
            }
        }
        //添加所有折扣
        DiscountItemParser discountItemParser = new DiscountItemParser();
        List<DiscountItem> discountItems = discountItemParser.parse(ShopData.DISCOUNT_ITEMS);
        for (DiscountItem item : discountItems) {
            if (allInfoItem.get(item.getBarcode()) != null) {
                TotalInfoItem totalInfoItem = allInfoItem.get(item.getBarcode());
                totalInfoItem.setDiscount(item.getDiscountPrice());
                allInfoItem.put(item.getBarcode(), totalInfoItem);
            }
        }

        Map<String, Promotion> promotionMap = new HashMap<>();
        for (CartItem item : cartItems) {
            if (allInfoItem.get(item.getBarcode()) != null) {
                TotalInfoItem totalInfoItem = allInfoItem.get(item.getBarcode());
                if (totalInfoItem.isHasSecondHalfPrice() && totalInfoItem.getDiscount() < 100d) {
                    promotionMap.put(item.getBarcode(), new DiscountWithHalfPromotion(totalInfoItem.getDiscount()));
                } else if (totalInfoItem.isHasSecondHalfPrice() && totalInfoItem.getDiscount() == 100d) {
                    promotionMap.put(item.getBarcode(), new HalfPromotion());
                } else if (!totalInfoItem.isHasSecondHalfPrice() && totalInfoItem.getDiscount() < 100d) {
                    promotionMap.put(item.getBarcode(), new DiscountPromotion(totalInfoItem.getDiscount()));
                } else {
                    promotionMap.put(item.getBarcode(), new NoPromotion());
                }
            }
        }


        PosMachine posMachine = new PosMachine(allItems, allInfoItem, new PromotionManager(promotionMap));
        double total = posMachine.calculate(cartItems);
        System.out.println("总价:" + total);
    }
}
