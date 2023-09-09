package com.trendyol.shipment;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Basket {

    final static Logger LOGGER = Logger.getLogger(Basket.class.getName());

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        try {
            if (products.stream().anyMatch(product -> product.getSize().equals(ShipmentSize.X_LARGE))) {
                return ShipmentSize.X_LARGE;
            }
            ShipmentSize result = isThereSizeMoreThanTwo();
            if(Objects.nonNull(result)){
                return result;
            }
            return biggestSize();
        } catch (UnknownError ex){
            LOGGER.log( Level.SEVERE, "ERROR=", ex );
            return null;
        }
    }

    public ShipmentSize biggestSize() {
        List<ShipmentSize> sizeList = Arrays.asList(ShipmentSize.values());
        Collections.reverse(sizeList);
        for(ShipmentSize size : sizeList ) {
            if(products.stream().anyMatch(product -> product.getSize().equals(size))) {
                return size;
            }
        }
        return null;
    }

    public ShipmentSize isThereSizeMoreThanTwo(){
        Map<ShipmentSize, Long> sizeCountMap = products.stream()
                .collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));

        ShipmentSize moreThanThree = sizeCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= 3)
                .map(Map.Entry::getKey).findAny().orElse(null);

        return (Objects.nonNull(moreThanThree) ? moreThanThree.getNextSize() : moreThanThree);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
