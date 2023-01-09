package com.example.ScanPe;

public class ProductItem {

    private String PRODUCTID;
    private String IMAGE;
    private String PRODUCTNAME;
    private String PRICE;
    private String LOCATION;


    public ProductItem(String PRODUCTID,String IMAGE, String PRODUCTNAME ,String PRICE, String LOCATION) {
        this.PRODUCTID=PRODUCTID;
        this.IMAGE=IMAGE;
        this.PRODUCTNAME=PRODUCTNAME;
        this.PRICE=PRICE;
        this.LOCATION=LOCATION;

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

    public String getLOCATION() {return LOCATION ; }

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

    public void setLOCATION(String LOCATION) { this.LOCATION = LOCATION ; }
}
