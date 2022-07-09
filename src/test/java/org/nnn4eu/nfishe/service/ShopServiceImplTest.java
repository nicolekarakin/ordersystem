package org.nnn4eu.nfishe.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nnn4eu.nfishe.config.OrderSystemConfig;
import org.nnn4eu.nfishe.exception.ProductNotFoundExeption;
import org.nnn4eu.nfishe.model.MilkProduct;
import org.nnn4eu.nfishe.model.Product;
import org.nnn4eu.nfishe.model.SeafoodProduct;
import org.nnn4eu.nfishe.repo.OrderRepo;
import org.nnn4eu.nfishe.repo.ProductRepo;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceImplTest {

    @Test
    void getProduct() throws ProductNotFoundExeption {
        List<Product> products=productsGen(10);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        OrderRepo orderRepo=new OrderRepo(Collections.EMPTY_LIST);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);
        Product expected = products.get(5);
        Product actual=shopService.getProduct(expected.getId());
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getProduct_throws() throws ProductNotFoundExeption {
        List<Product> products=productsGen(2);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        OrderRepo orderRepo=new OrderRepo(Collections.EMPTY_LIST);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);

        UUID id =UUID.randomUUID();
        Exception ex=Assertions.assertThrows(ProductNotFoundExeption.class, ()->shopService.getProduct(id));
        Assertions.assertTrue(ex.getMessage().contains(id.toString()));
    }

    @Test
    void listProducts() {
        List<Product> products=productsGen(32);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        OrderRepo orderRepo=new OrderRepo(Collections.EMPTY_LIST);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);

        List<Product> actual=shopService.listProducts();
        System.out.println("=============unsorted===============");
        products.forEach(System.out::println);
        System.out.println("=============sorted=================");
        actual.forEach(System.out::println);

        Assertions.assertTrue(products.size()== actual.size());
        Assertions.assertTrue(actual.containsAll(products));

    }

    @Test
    void addOrder() {
    }

    @Test
    void gerOrder() {
    }

    @Test
    void listOrders() {
    }

    private Product productGen(Random rand,OrderSystemConfig.Category category){
        String rch=getRandomUpperCaseLetter(rand)+"-";
        String[] milkNames={"Moon milk","Chocolate milk","HappyCow"};
        Integer[] milkVolumes={750,2000};
        String[] seafoodNames={"CATFISH FILLET LEMON PEPPER","CRAB MEAT IMITATION Flake 680g","CRAB LEGS 12-14 Red King CS"};
        String[] seafoodOrigins={"Canada","Spain"};
        switch(category ){ //OrderSystemConfig.Category.valueOf(category)
            case milk:
                return new MilkProduct(
                        UUID.randomUUID(),rch+milkNames[rand.nextInt(milkNames.length)],
                        "ml",milkVolumes[rand.nextInt(milkVolumes.length)]);
            case seafood:

                return new SeafoodProduct(
                        UUID.randomUUID(),rch+seafoodNames[rand.nextInt(seafoodNames.length)],
                        seafoodOrigins[rand.nextInt(seafoodOrigins.length)], rand.nextBoolean()
                );
            default:
                throw new IllegalArgumentException("Probably new category that is not in our switch case :)");
        }

    }

    private List<Product> productsGen(int num){
        Random rand=new Random();
        OrderSystemConfig.Category[] categories=OrderSystemConfig.Category.values();
        List<Product> products=new ArrayList<>();
        for(int i=0; i<num; i++){
            products.add(productGen(rand,categories[rand.nextInt(categories.length)]));
        }
        return products;
    }

    private String getRandomUpperCaseLetter(Random rand){
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return abc.charAt(rand.nextInt(abc.length()))+"";
    }
}
