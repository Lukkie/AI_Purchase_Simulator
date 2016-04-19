package Shop;

/**
 * This class will represent a profile of a shop
 * These parameters will contribute to the consummation decision
 */
public class ShopProfile {

    private static double greenPerceivedRisk;
    private static double greenPerceivedTrust;
    private static double greenBrandImage;
    private static double environmentalAdvertisement;

    public static double getGPR() {
        return greenPerceivedRisk;
    }

    public static double getGPT() {
        return greenPerceivedTrust;
    }

    public static double getGBI() {
        return greenBrandImage;
    }

    public static double getEA() {
        return environmentalAdvertisement;
    }
}
