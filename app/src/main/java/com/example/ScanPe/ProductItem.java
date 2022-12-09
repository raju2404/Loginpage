package com.example.ScanPe;

public class ProductItem {

    private String IMAGE;
    private String PRODUCTNAME;
    private String PRICE;

    public ProductItem(String IMAGE, String PRODUCTNAME ,String PRICE) {
        this.IMAGE=IMAGE;
        this.PRODUCTNAME=PRODUCTNAME;
        this.PRICE=PRICE;

    }

    public String getIMAGE() {
        return IMAGE;
    }

    public String getPRODUCTNAME() {
        return PRODUCTNAME;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public void setPRODUCTNAME(String PRODUCTNAME) {
        this.PRODUCTNAME = PRODUCTNAME;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }
}
