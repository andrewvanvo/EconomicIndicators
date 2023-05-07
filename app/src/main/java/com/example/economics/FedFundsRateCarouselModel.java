package com.example.economics;

public class FedFundsRateCarouselModel {
    String stat;
    String descriptor;

    public FedFundsRateCarouselModel(String stat, String descriptor) {
        this.stat = stat;
        this.descriptor = descriptor;
    }

    public String getStat() {
        return stat;
    }

    public String getDescriptor() {
        return descriptor;
    }

}
