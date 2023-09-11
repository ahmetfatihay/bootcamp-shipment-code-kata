package com.trendyol.shipment;

import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.Collections;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        if (isThereXlarge()) {
            return ShipmentSize.X_LARGE;
        }
        ShipmentSize result = isThereSizeMoreThanTwo();
        if (Objects.nonNull(result)) {
            return result;
        }
        return biggestSize();
    }

    private boolean isThereXlarge() {
        return products.stream().anyMatch(product -> product.getSize().equals(ShipmentSize.X_LARGE));
    }

    private ShipmentSize isThereSizeMoreThanTwo() {
        Map<ShipmentSize, Long> sizeCountMap = products.stream()
                .collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));

        ShipmentSize moreThanThree = sizeCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= 3)
                .map(Map.Entry::getKey).findFirst().orElse(null);

        return (Objects.nonNull(moreThanThree) ? moreThanThree.getNextSize() : null);
    }

    private ShipmentSize biggestSize() {
        List<ShipmentSize> shipmentSizeEnumList = Arrays.asList(ShipmentSize.values());
        Collections.reverse(shipmentSizeEnumList);
        for (ShipmentSize size : shipmentSizeEnumList) {
            if (products.stream().anyMatch(product -> product.getSize().equals(size))) {
                return size;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
