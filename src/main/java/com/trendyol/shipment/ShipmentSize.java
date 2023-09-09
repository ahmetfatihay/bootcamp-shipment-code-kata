package com.trendyol.shipment;

public enum ShipmentSize {

    SMALL,
    MEDIUM,
    LARGE,
    X_LARGE;

    public ShipmentSize getNextSize() {
        ShipmentSize[] sizes = ShipmentSize.values();
        int currentIndex = this.ordinal();

        if (currentIndex < sizes.length - 1) {
            return sizes[currentIndex + 1];
        } else {
            return sizes[currentIndex];
        }
    }

}
