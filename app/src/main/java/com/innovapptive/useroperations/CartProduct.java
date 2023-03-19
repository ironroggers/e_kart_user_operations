package com.innovapptive.useroperations;


public class CartProduct {
    String productTitle,category,mrp,sp,id,url,size,quantity;
    public CartProduct(){

    }
    public CartProduct(String productTitle, String category, String mrp, String sp, String id, String url, String size, String quantity) {
        this.productTitle = productTitle;
        this.category = category;
        this.mrp = mrp;
        this.sp = sp;
        this.id=id;
        this.url=url;
        this.size=size;
        this.quantity=quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }
}