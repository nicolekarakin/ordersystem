package org.nnn4eu.nfishe.repo;

import org.nnn4eu.nfishe.model.Order;
import org.nnn4eu.nfishe.model.OrderLine;
import org.nnn4eu.nfishe.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class OrderRepo {
    private List<Order> orders;
    public Optional<Order> getOrder(UUID id){
        return orders.stream().filter(a->a.getId().equals(id)).findFirst();
    }
    public List<Order> getOrders(){
        return orders;
    }

    public void addOrder(Order order){
        if(order!=null)orders.add(order);
    }
    public void removeOrder(Order order){
        if(order!=null)orders.remove(order);
    }

    public void addOrderLine(UUID id, OrderLine orderLine){
        if(orderLine!=null && getOrder(id).isPresent())getOrder(id).get().getOrderLines().add(orderLine);
    }
    public void removeOrderLine(UUID id,OrderLine orderLine){
        if(orderLine!=null && getOrder(id).isPresent())getOrder(id).get().getOrderLines().remove(orderLine);
    }

    public OrderRepo(List<Order> orders) {
        this.orders = orders;
    }
}
