package org.nnn4eu.nfishe.service;

import org.nnn4eu.nfishe.exception.OrderNotFoundExeption;
import org.nnn4eu.nfishe.exception.ProductNotFoundExeption;
import org.nnn4eu.nfishe.model.Order;
import org.nnn4eu.nfishe.model.Product;

import java.util.List;
import java.util.UUID;

public interface ShopService {
    Product getProduct(UUID id) throws ProductNotFoundExeption;
    List<Product> listProducts();
    boolean addOrder(Order order);
    Order gerOrder(UUID id) throws OrderNotFoundExeption;
    List<Order> listOrders();
}
