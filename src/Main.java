import entities.Customer;
import entities.Order;
import entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {


//        SUPPLIERS
        Supplier<Product> productSupplier = () -> {
            Random rndm = new Random();
            long randomId = rndm.nextLong(10000, 100000);
            double randomPrice = rndm.nextDouble(1.00, 500.00);
            int randomSelectorName = rndm.nextInt(0, 4);
            int randomSelectorCategory = rndm.nextInt(0, 4);
            List<String> nameList = new ArrayList<>();
            nameList.add("Product1");
            nameList.add("Product2");
            nameList.add("Product3");
            nameList.add("Product4");
            List<String> categoryList = new ArrayList<>();
            categoryList.add("Baby");
            categoryList.add("Books");
            categoryList.add("Boys");
            categoryList.add("Gaming");
            return new Product(randomId, nameList.get(randomSelectorName), categoryList.get(randomSelectorCategory), randomPrice);
        };

        Supplier<Customer> customerSupplier = () -> {
            Random rndm = new Random();
            long randomId = rndm.nextLong(10000, 100000);
            int randomTier = rndm.nextInt(1, 4);
            int randomName = rndm.nextInt(1, 4);
            List<String> nameList = new ArrayList<>();
            nameList.add("Fabio");
            nameList.add("Bobby");
            nameList.add("Jhonny");
            nameList.add("Jimmy");
            return new Customer(randomId, nameList.get(randomName), randomTier);
        };

        Supplier<Order> orderSupplier = () -> {
            Random rndm = new Random();
            long randomId = rndm.nextLong(10000, 100000);
            int randomProducts = rndm.nextInt(1, 4);
            int randomStatus = rndm.nextInt(0, 3);
            int randomDate = rndm.nextInt(0, 2);
            List<LocalDate> dateList = new ArrayList<>();
            dateList.add(LocalDate.now());
            dateList.add(LocalDate.parse("2024-02-04"));
            List<String> status = new ArrayList<>();
            status.add("Shipped");
            status.add("Processed");
            status.add("Not taken in charge yet");
            Customer randomCustomer = customerSupplier.get();
            List<Product> randomProductsList = new ArrayList<>();
            for (int i = 0; i < randomProducts; i++) {
                randomProductsList.add(productSupplier.get());
            }
            return new Order(randomId, status.get(randomStatus), dateList.get(randomDate), randomProductsList, randomCustomer);
        };


//        ESERCIZIO 1
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            productList.add(productSupplier.get());
        }

        List<Product> filteredProducts = productList.stream().filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100).toList();

        System.out.println("Products in Books category with a price over 100 are: " + filteredProducts.size());
        filteredProducts.forEach(product -> System.out.println(product));


//        ESERCIZIO 2
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            orderList.add(orderSupplier.get());
        }
        Set<Order> babyOrders = new HashSet<>();
        orderList.forEach(order -> order.getProducts().forEach(product -> {
            if (product.getCategory().equals("Baby")) {
                babyOrders.add(order);
            }
        }));
        System.out.println("Order with products in baby category are: " + babyOrders.size());
        babyOrders.forEach(order -> System.out.println(order));


//        ESERCIZIO 3
        List<Product> boysProduct = productList.stream().filter(product -> product.getCategory().equals("Boys")).toList();
        System.out.println("Discounted products are: " + boysProduct.size());
        boysProduct.forEach(product -> {
            product.setPrice(product.getPrice() * 0.9);
            System.out.println(product);
        });

//        ESERCIZIO 4
        List<Order> ordersFilteredByCustomerAndDate = orderList.stream().filter(order -> order.getOrderDate().isBefore(LocalDate.parse("2024-02-29")) && order.getOrderDate().isAfter(LocalDate.parse("2024-02-01"))).filter(order -> order.getCustomer().getTier() == 2).toList();
        List<Product> finalList = new ArrayList<>();
        ordersFilteredByCustomerAndDate.forEach(order -> order.getProducts().forEach(product -> finalList.add(product)));
        System.out.println("Products ordered between 01-02-2024 and 29-02-2024 by Tier 2 customer are: " + finalList.size());
        finalList.forEach(product -> System.out.println(product));
    }
}