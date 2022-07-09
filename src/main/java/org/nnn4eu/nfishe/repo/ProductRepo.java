package org.nnn4eu.nfishe.repo;

import org.nnn4eu.nfishe.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductRepo {
    private Set<Product> products;
    public Optional<Product> getProduct(UUID id){
       return products.stream().filter(a->a.getId().equals(id)).findFirst();
    }
    public List<Product> getProducts(){
        return products.stream().sorted().collect(Collectors.toUnmodifiableList());
    }

    public void addProduct(Product product){
        if(product!=null)products.add(product);
    }
    public void removeProduct(Product product){
        if(product!=null)products.remove(product);
    }

    public ProductRepo(Set<Product> products) {
        this.products = products.stream().collect(Collectors.toUnmodifiableSet());
//        this.products = products.stream().sorted().collect(Collectors.toUnmodifiableSet());
    }
}
