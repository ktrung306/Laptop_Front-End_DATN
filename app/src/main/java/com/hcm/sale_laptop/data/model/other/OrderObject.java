package com.hcm.sale_laptop.data.model.other;

import java.util.List;

public class OrderObject {
    private List<OrderStateModel> list;

    public OrderObject(List<OrderStateModel> list) {
        this.list = list;
    }

    public List<OrderStateModel> getList() {
        return list;
    }

    public void setList(List<OrderStateModel> list) {
        this.list = list;
    }
}
