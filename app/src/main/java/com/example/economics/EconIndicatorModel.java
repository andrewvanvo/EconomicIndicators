package com.example.economics;

public class EconIndicatorModel {
    String indicatorName;
    int image;


    public EconIndicatorModel(String indicatorName, int image) {
        this.indicatorName = indicatorName;
        this.image = image;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public int getImage() {
        return image;
    }
}
