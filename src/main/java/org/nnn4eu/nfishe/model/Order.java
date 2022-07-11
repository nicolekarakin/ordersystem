package org.nnn4eu.nfishe.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<OrderLine> orderLines;

    public UUID getId() {
        return id;
    }

    public Order(UUID id, List<OrderLine> orderLines) {
        this.id = id;
        this.orderLines = orderLines;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        //TODO build string for orderlines arr
        return id + "\t\t" + orderLines;
    }
}
