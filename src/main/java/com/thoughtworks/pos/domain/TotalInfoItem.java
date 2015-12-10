package com.thoughtworks.pos.domain;


public class TotalInfoItem {
    private String barcode;
    private double price;
    private double discount=100d;
    private boolean hasSecondHalfPrice=false;
    private int itemNum;

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public TotalInfoItem(String barcode, double price) {
        this.barcode = barcode;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isHasSecondHalfPrice() {
        return hasSecondHalfPrice;
    }

    public void setHasSecondHalfPrice(boolean hasSecondHalfPrice) {
        this.hasSecondHalfPrice = hasSecondHalfPrice;
    }
}
