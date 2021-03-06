package Agents;

import java.util.Date;

/**
 * This class will represent a profile of an agent
 * These parameters will contribute to the consummation decision
 */
public class AgentProfile {

    private double selfPerception;          // how sensitive is a consumer to external attributes (price reductions)
    private double priceQualityPerception; // how sensitive is a consumer to too low of a price reduction

    private double greenPurchaseIntention;  // how sensitive is the consumer to buying green products

    private double needRecognition;         // how sensitive is the consumer to buying products
                                            // this will also depend on the category of the product

    private double willingnessToBuy;

    private double locationFlexibility;

    private double susceptibilityCollectionPoint;
    private double CollectionPointRecommendationFactor;

    private boolean alwaysAtHome; // true if delivery always at home
    private double reputationShop;

    private CollectionPoint recommendedCP; // recommendedCP recommended by someone close
    private Date recommendedDate;

    private boolean acceptedCPOffer = false;


    /* setters and getters */
    public double getSelfPerception() {
        return selfPerception;
    }

    public void setSelfPerception(double selfPerception) {
        this.selfPerception = selfPerception;
    }

    public double getPriceQualityPerception() {
        return priceQualityPerception;
    }

    public void setPriceQualityPerception(double priceQualityPerception) {
        this.priceQualityPerception = priceQualityPerception;
    }

    public double getGreenPurchaseIntention() {
        return greenPurchaseIntention;
    }

    public void setGreenPurchaseIntention(double greenPurchaseIntention) {
        this.greenPurchaseIntention = greenPurchaseIntention;
    }

    public void setAlwaysAtHome(boolean alwaysAtHome) {
        this.alwaysAtHome = alwaysAtHome;
    }

    public double getNeedRecognition() {
        return needRecognition;
    }

    public double getWillingnessToBuy() {
        return willingnessToBuy;
    }

    public void setWillingnessToBuy(double willingnessToBuy) {
        this.willingnessToBuy = willingnessToBuy;
    }

    public double getLocationFlexibility() {
        return locationFlexibility;
    }

    public void setLocationFlexibility(double locationFlexibility) {
        this.locationFlexibility = locationFlexibility;
    }

    public void setNeedRecognition(double needRecognition) {
        this.needRecognition = needRecognition;
    }

    public double getSusceptibility() {
        return susceptibilityCollectionPoint;
    }

    public void setSusceptibility(double susceptibilityCollectionPoint) {
        this.susceptibilityCollectionPoint = susceptibilityCollectionPoint;
    }

    public double getCollectionPointRecommendationFactor() {
        return CollectionPointRecommendationFactor;
    }

    public void setCollectionPointRecommendationFactor(double collectionPointRecommendationFactor) {
        CollectionPointRecommendationFactor = collectionPointRecommendationFactor;
    }

    public double getGPI() {
        return greenPurchaseIntention;
    }

    public double getPQP() {
        return priceQualityPerception;
    }

    public double getSP() {
        return selfPerception;
    }

    public double getWB() {
        return willingnessToBuy;
    }

    public CollectionPoint getRecommendedCP() {
        if(recommendedCP==null) return null;
        CollectionPoint rCP = new CollectionPoint(recommendedCP);
        if(Tools.RNG.chance(80,0,100)){
            recommendedCP = null;
        }
        return rCP;
    }

    @Override
    public String toString() {
        return "AgentProfile{" +
                "\n\tselfPerception \t\t\t\t\t\t" + selfPerception +
                "\n" +
                "\tpriceQualityPerception\t\t\t\t" + priceQualityPerception +
                "\n" +
                "\tgreenPurchaseIntention\t\t\t\t" + greenPurchaseIntention +
                "\n" +
                "\tneedRecognition\t\t\t\t\t\t" + needRecognition +
                "\n" +
                "\twillingnessToBuy\t\t\t\t\t" + willingnessToBuy +
                "\n" +
                "\tlocationFlexibility\t\t\t\t\t" + locationFlexibility +
                "\n" +
                "\tsusceptibilityCollectionPoint\t\t"+ susceptibilityCollectionPoint +
                "\n" +
                "\tCollectionPointRecommendationFactor\t" + CollectionPointRecommendationFactor +
                "\n}\n";
    }

    public boolean isAlwaysAtHome() {
        return alwaysAtHome;
    }

    public void setRecommendedCP(CollectionPoint recommendedCP) {
        this.recommendedCP = recommendedCP;
    }

    public Date getRecommendedDate() {
        return recommendedDate;
    }

    public void setWB(double WB) {
        this.willingnessToBuy = WB;
    }

    public double getReputationShop() {
        return reputationShop;
    }

    public void setReputationShop(double reputationShop) {
        this.reputationShop = reputationShop;
    }

    public double getShopReputation() {
        return reputationShop;
    }

    public void setShopReputation(double shopReputation) {
        this.reputationShop = shopReputation;
    }

    public void setRecommendedDate(Date recommendedDate) {
        this.recommendedDate = recommendedDate;
    }

    public boolean hasAcceptedCPOffer() {
        return this.acceptedCPOffer;
    }

    public void setAcceptedCPOffer(boolean acceptedCPOffer) {
        this.acceptedCPOffer = acceptedCPOffer;
    }
}
