package com.ek.posbridge.Models;

import java.util.Date;

public class TempArrayModel {
    private String orderid;
    private Date timeoforder;
    private String orderstatus;
    private String ordersource;

    public TempArrayModel(String orderid, Date timeoforder, String orderstatus, String ordersource) {
        this.orderid = orderid;
        this.timeoforder = timeoforder;
        this.orderstatus = orderstatus;
        this.ordersource = ordersource;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Date getTimeoforder() {
        return timeoforder;
    }

    public void setTimeoforder(Date timeoforder) {
        this.timeoforder = timeoforder;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getOrdersource() {
        return ordersource;
    }

    public void setOrdersource(String ordersource) {
        this.ordersource = ordersource;
    }
}
