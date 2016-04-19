package Generators;

import Tools.RNG;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class AgentGenerator {
    private int agentAmount;
    private double selfPerceptionFactor;
    private double priceQualityPerceptionFactor;
    private double greenPurchaseIntentionFactor;
    private double needRecognitionFactor;
    private double willingnessToBuyFactor;
    private double locationFlexibilityFactor;
    private double susceptibilityCollectionPointFactor;
    private double CollectionPointRecommendationFactorFactor;


    public void generateAgents() {
        RNG rng = RNG.getInstance();
        rng.nexGaussian()
    }

    public void setAgentAmount(int agentAmount) {
        this.agentAmount = agentAmount;
    }

    public void setSelfPerceptionFactor(double selfPerceptionFactor) {
        this.selfPerceptionFactor = selfPerceptionFactor;
    }

    public void setPriceQualityPerceptionFactor(double priceQualityPerceptionFactor) {
        this.priceQualityPerceptionFactor = priceQualityPerceptionFactor;
    }

    public void setGreenPurchaseIntentionFactor(double greenPurchaseIntentionFactor) {
        this.greenPurchaseIntentionFactor = greenPurchaseIntentionFactor;
    }

    public void setNeedRecognitionFactor(double needRecognitionFactor) {
        this.needRecognitionFactor = needRecognitionFactor;
    }

    public void setWillingnessToBuyFactor(double willingnessToBuyFactor) {
        this.willingnessToBuyFactor = willingnessToBuyFactor;
    }

    public void setLocationFlexibilityFactor(double locationFlexibilityFactor) {
        this.locationFlexibilityFactor = locationFlexibilityFactor;
    }

    public void setSusceptibilityCollectionPointFactor(double susceptibilityCollectionPointFactor) {
        this.susceptibilityCollectionPointFactor = susceptibilityCollectionPointFactor;
    }

    public void setCollectionPointRecommendationFactorFactor(double collectionPointRecommendationFactorFactor) {
        CollectionPointRecommendationFactorFactor = collectionPointRecommendationFactorFactor;
    }
}
