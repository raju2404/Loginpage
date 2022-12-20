package com.example.ScanPe;

public class ProductItem {

    private String PRODUCTID;
    private String IMAGE;
    private String PRODUCTNAME;
    private String PRICE;


    public ProductItem(String PRODUCTID,String IMAGE, String PRODUCTNAME ,String PRICE) {
        this.PRODUCTID=PRODUCTID;
        this.IMAGE=IMAGE;
        this.PRODUCTNAME=PRODUCTNAME;
        this.PRICE=PRICE;

    }

    public String getPRODUCTID() {return PRODUCTID;}

    public String getIMAGE() {
        return IMAGE;
    }

    public String getPRODUCTNAME() {
        return PRODUCTNAME;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRODUCTID(String PRODUCTID) {this.PRODUCTID = PRODUCTID;}

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
