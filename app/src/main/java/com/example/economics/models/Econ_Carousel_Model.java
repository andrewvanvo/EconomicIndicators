package com.example.economics.models;

public class Econ_Carousel_Model {
    String stat;
    String descriptor;

    public Econ_Carousel_Model(String stat, String descriptor) {
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
