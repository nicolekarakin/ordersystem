package org.nnn4eu.nfishe.repo;

import org.nnn4eu.nfishe.model.Order;
import org.nnn4eu.nfishe.model.OrderLine;
import org.nnn4eu.nfishe.model.Product;

import java.util.*;

public class OrderRepo {
    private Map<UUID,Order> orders;

    public List<Order> listOrders(){
        return List.copyOf(orders.values());
    }

    public Order addOrder(Order newOrder){
        if(newOrder!=null&&newOrder.getId()!=null)
        orders.put(newOrder.getId(), newOrder);
        return newOrder;
    }
    public Optional<Order> getOrder(UUID id){
        return (orders.get(id)!=null)?Optional.of(orders.get(id)):Optional.empty();
    }

    public void removeOrder(Order order){
        if(order!=null&&order.getId()!=null)orders.remove(order.getId());
    }

    public void addOrderLine(UUID id, OrderLine orderLine){
        if(orderLine!=null && getOrder(id).isPresent())getOrder(id).get().getOrderLines().add(orderLine);
    }
    public void removeOrderLine(UUID id,OrderLine orderLine){
        if(orderLine!=null && getOrder(id).isPresent())getOrder(id).get().getOrderLines().remove(orderLine);
    }

    public OrderRepo(Map<UUID,Order> orders) {
        this.orders = orders;
    }
}
