package org.nnn4eu.nfishe.service;

import org.nnn4eu.nfishe.exception.ProductNotFoundExeption;
import org.nnn4eu.nfishe.model.Order;
import org.nnn4eu.nfishe.model.Product;
import org.nnn4eu.nfishe.repo.OrderRepo;
import org.nnn4eu.nfishe.repo.ProductRepo;

import java.util.List;
import java.util.UUID;

public class ShopServiceImpl implements ShopService{
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    public ShopServiceImpl(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Override
    public Product getProduct(UUID id) throws ProductNotFoundExeption {
        if(productRepo.getProduct(id).isEmpty())throw new ProductNotFoundExeption("Product with id: "+id+" wasn't found");
        return productRepo.getProduct(id).get();
    }

    @Override
    public List<Product> listProducts() {
        return productRepo.getProducts();
//        return List.copyOf(productRepo.getProducts());
    }

    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    @Override
    public Order gerOrder() {
        return null;
    }

    @Override
    public List<Order> listOrders() {
        return null;
    }
}
