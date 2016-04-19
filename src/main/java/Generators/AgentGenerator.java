package Generators;

import Agents.Agent;
import Agents.AgentProfile;
import Agents.GeoLocation;
import Tools.RNG;

import java.util.ArrayList;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class AgentGenerator {
    private int agentAmount = 100;

    private double selfPerceptionFactor = 0.5;
    private double priceQualityPerceptionFactor = 0.5;
    private double greenPurchaseIntentionFactor = 0.5;
    private double needRecognitionFactor = 0.5;
    private double willingnessToBuyFactor = 0.5;
    private double locationFlexibilityFactor = 0.5;
    private double susceptibilityCollectionPointFactor = 0.5;
    private double collectionPointRecommendationFactorFactor = 0.5;

    private final double stddev = 0.2;

    public ArrayList<Agent> generateAgents() {
        RNG rng = RNG.getInstance();

        ArrayList<Agent> agents = new ArrayList<>();
        for (int i = 0; i < agentAmount; i++) {
            Agent agent = new Agent();

            AgentProfile ap = new AgentProfile();
            ap.setSelfPerception(getValue(selfPerceptionFactor, stddev, rng));

            ap.setPriceQualityPerception(getValue(priceQualityPerceptionFactor, stddev, rng));
            ap.setGreenPurchaseIntention(getValue(greenPurchaseIntentionFactor, stddev, rng));
            ap.setNeedRecognition(getValue(needRecognitionFactor, stddev, rng));
            ap.setWillingnessToBuy(getValue(willingnessToBuyFactor, stddev, rng));
            ap.setLocationFlexibility(getValue(locationFlexibilityFactor, stddev, rng));
            ap.setSusceptibilityCollectionPoint(getValue(susceptibilityCollectionPointFactor, stddev, rng));
            ap.setCollectionPointRecommendationFactor(getValue(collectionPointRecommendationFactorFactor, stddev, rng));
            agent.setProfile(ap);


            GeoLocation geo = rng.getRandomLocation();
            agent.setLocation(geo);


            agents.add(agent);
        }

        return agents;

    }

    private double getValue(double mean, double stddev, RNG rng)  {
        double x = rng.nextGaussian(stddev, mean);
        if (x < 0) x = 0;
        if (x > 1) x = 1;
        return x;
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
        this.collectionPointRecommendationFactorFactor = collectionPointRecommendationFactorFactor;
    }

}
