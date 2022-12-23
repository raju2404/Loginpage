package com.example.ScanPe;

public class InvoiceItem {

    //private String ORDERID;
    private String PRODUCTID;
    private String PRODUCTNAME;
    private String QUANTITY;
    private String PRICE;

    public InvoiceItem(String ORDERID, String PRODUCTID,String PRODUCTNAME, String QUANTITY ,String PRICE) {
        //this.ORDERID=ORDERID;
        this.PRODUCTID=PRODUCTID;
        this.PRODUCTNAME=PRODUCTNAME;
        this.QUANTITY=QUANTITY;
        this.PRICE=PRICE;

    }

//    public String getORDERID() {
//        return ORDERID;
//    }

//    public void setORDERID(String ORDERID) {
//        this.ORDERID = ORDERID;
//    }

    public String getPRODUCTID() {
        return PRODUCTID;
    }

    public void setPRODUCTID(String PRODUCTID) {
        this.PRODUCTID = PRODUCTID;
    }

    public String getPRODUCTNAME() {
        return PRODUCTNAME;
    }

    public void setPRODUCTNAME(String PRODUCTNAME) {
        this.PRODUCTNAME = PRODUCTNAME;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }
}
