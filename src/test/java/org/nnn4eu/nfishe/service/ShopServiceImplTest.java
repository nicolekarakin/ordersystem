package org.nnn4eu.nfishe.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nnn4eu.nfishe.config.OrderSystemConfig;
import org.nnn4eu.nfishe.exception.OrderNotFoundExeption;
import org.nnn4eu.nfishe.exception.ProductNotFoundExeption;
import org.nnn4eu.nfishe.model.*;
import org.nnn4eu.nfishe.repo.OrderRepo;
import org.nnn4eu.nfishe.repo.ProductRepo;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceImplTest {

    @Test
    void getProduct() throws ProductNotFoundExeption {
        List<Product> products=productsGen(10);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        OrderRepo orderRepo=new OrderRepo(Collections.EMPTY_MAP);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);
        Product expected = products.get(5);
        Product actual=shopService.getProduct(expected.getId());
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getProduct_throws() throws ProductNotFoundExeption {
        List<Product> products=productsGen(2);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        OrderRepo orderRepo=new OrderRepo(Collections.EMPTY_MAP);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);

        UUID id =UUID.randomUUID();
        Exception ex=Assertions.assertThrows(ProductNotFoundExeption.class, ()->shopService.getProduct(id));
        Assertions.assertTrue(ex.getMessage().contains(id.toString()));
    }

    @Test
    void listProducts() {
        List<Product> products=productsGen(32);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        OrderRepo orderRepo=new OrderRepo(Collections.EMPTY_MAP);
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
        List<Product> products=productsGen(32);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        Map<UUID, Order> ordersExpected=ordersGen(productRepo, 3);
        OrderRepo orderRepo=new OrderRepo(ordersExpected);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);

        int sizeBefore=ordersExpected.size();
        Product product=productRepo.getProducts().get(0);
        OrderLine line = new OrderLine(product.getId(),2);
        Order newOrder=new Order(UUID.randomUUID(),List.of(line));

        shopService.addOrder(newOrder);

        System.out.println(ordersExpected.values());
        System.out.println("=================================");
        System.out.println(shopService.listOrders());

        Assertions.assertEquals(sizeBefore,shopService.listOrders().size()-1);
        Assertions.assertTrue(shopService.listOrders().contains(newOrder));
    }

    @Test
    void gerOrder() throws OrderNotFoundExeption {
        List<Product> products=productsGen(32);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        Map<UUID, Order> ordersExpected=ordersGen(productRepo, 3);
        OrderRepo orderRepo=new OrderRepo(ordersExpected);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);

        System.out.println(ordersExpected.values());
        System.out.println("=================================");
        System.out.println(shopService.listOrders());

        Order expected = ordersExpected.values().stream().findFirst().get();
        System.out.println("=================================");
        System.out.println(expected);

        Order actual = shopService.gerOrder(expected.getId());
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void listOrders() {
        List<Product> products=productsGen(32);
        ProductRepo productRepo=new ProductRepo( new HashSet<Product>(products));
        Map<UUID, Order> ordersExpected=ordersGen(productRepo, 3);
        OrderRepo orderRepo=new OrderRepo(ordersExpected);
        ShopService shopService=new ShopServiceImpl(orderRepo,productRepo);

        List<Order> actual=shopService.listOrders();
        System.out.println(actual.size()+"\n"+actual);

        Assertions.assertTrue(ordersExpected.size()== actual.size());
        Assertions.assertTrue(actual.containsAll(ordersExpected.values()));
    }

    private Map<UUID, Order> ordersGen(ProductRepo productRepo, int orderNum) {
        int maxAmount=5; //perProduct
        Random rand=new Random();
        List<Integer> indexesOriginal = IntStream.range(0, productRepo.getProducts().size()).boxed().toList();
        Map<UUID, Order> orders=new HashMap<>();

        for(int ii=0;ii<orderNum;ii++) {
            List<Integer> indexes=new ArrayList<>(indexesOriginal);
            List<OrderLine> orderLines = new ArrayList<>();
            for (int i = 0; i < rand.nextInt(1, productRepo.getProducts().size()); i++) {
                int productInd = rand.nextInt(0, indexes.size());
                indexes.remove(productInd);
                Product product = productRepo.getProducts().get(productInd);
                OrderLine line = new OrderLine(product.getId(), rand.nextInt(1, maxAmount));
                orderLines.add(line);
            }
            Order order = new Order(UUID.randomUUID(), orderLines);
            orders.put(order.getId(), order);
        }
        return orders;
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
