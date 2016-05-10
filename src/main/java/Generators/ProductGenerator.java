package Generators;

import Shop.ProductProfile;
import Shop.ShopProfile;
import Tools.RNG;

import java.util.ArrayList;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class ProductGenerator {
    private int amount = 10;

    private double greenPerceivedValueFactor = 0.5;
    private double needRecognitionFactor = 0.5;
    private double price = 200;
    private double priceStdDev = 50;
    private double availability = 0.5;

    private final double stddev = 0.2;



    public ArrayList<ProductProfile> generateProducts() {
        RNG rng = RNG.getInstance();
        ArrayList<ProductProfile> products = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ProductProfile product = new ProductProfile();
            product.setGreenPerceivedValue(getValue(greenPerceivedValueFactor, stddev, rng));
            product.setNeedRecognition(getValue(needRecognitionFactor, stddev, rng));
            product.setPrice(getPrice(rng));
            products.add(product);
        }

        return products;
    }

    private double getValue(double mean, double stddev, RNG rng)  {
        double x = rng.nextGaussian(stddev, mean);
        if (x < 0) x = 0;
        if (x > 1) x = 1;
        return x;
    }

    private double getPrice(RNG rng) {
        double price = rng.nextGaussian(priceStdDev, this.price);
        if (price < 0) price = 0;
        return price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setGreenPerceivedValueFactor(double greenPerceivedValueFactor) {
        this.greenPerceivedValueFactor = greenPerceivedValueFactor;
    }

    public void setNeedRecognitionFactor(double needRecognitionFactor) {
        this.needRecognitionFactor = needRecognitionFactor;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPriceStdDev(double priceStdDev) {
        this.priceStdDev = priceStdDev;
    }

    public double getAvailability() {
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }

}
