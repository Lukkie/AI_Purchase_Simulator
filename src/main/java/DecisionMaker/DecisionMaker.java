package DecisionMaker;

import Agents.Agent;
import Agents.AgentProfile;
import Agents.CollectionPoint;
import Shop.ProductProfile;
import Shop.ShopProfile;
import Tools.RNG;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 *
 */
public class DecisionMaker {

    public static final int MAX_WAIT_TIME_IN_DAYS = 7;

    private Agent agent;
    private ProductProfile product;
    private CollectionPoint cp;
    private AgentProfile agentProfile;

    private int beginNumberOfDays, endNumberOfDays;

    public DecisionMaker(Agent agent, ProductProfile product) {
        this.agent= agent;
        this.product = product;
        this.agentProfile = agent.getProfile();
    }


    public boolean willBuy() throws Exception {
        double GPIN = (18/100) * agentProfile.getGPI() + (13/100) * ShopProfile.getGPR() + (17/100) * ShopProfile.getGPT()
                + (12/100) * ShopProfile.getGBI() + (32/100) * ShopProfile.getEA() + (8/100) * product.getGPV();

        double quality = product.getPPD() * agentProfile.getPQP();

        double pp = - product.getPPD() * agentProfile.getSP();

        if(quality<0) quality = 100 - quality;
        if(pp<0) pp = 100 - pp;

        double changeToBuy = (GPIN + quality + pp + agentProfile.getWB())/4;

        RNG rng = RNG.getInstance();
        rng.initGaussian(2,0);
        double rndDouble = rng.nextGaussian();

        return (changeToBuy / 100) > rndDouble;
    }


    /*
    @return boolean: if(true) home delivery else collectionPoint
                     if(false) getCollectionPoint()
     */
    public boolean deliveryToHome() throws Exception {

        double chanceCP = (agentProfile.getSP() + agentProfile.getGPI() + agentProfile.getLocationFlexibility() + agentProfile.getSusceptibilityCollectionPoint())/4;

        double rnd = RNG.getInstance().getDouble(0, 100);

        if(chanceCP>rnd)  return true; // delivery to home is selected

        // if susceptible for CP -> choose CP
        if(agentProfile.getSusceptibilityCollectionPoint()<RNG.getInstance().getDouble(0, 100)){
            cp = agentProfile.getRecommendedCP();
        }
        // else choose closest CP
        else{
            cp = CollectionPoint.getRandomClosestCP(agent.getLocation());
        }

        cp.hasBeenChoosenAsDeleveryPoint();

        return false;
    }


    public void numberOfDays(int preferedNumberOfDate){
        double chanceNotToday = ((100-agentProfile.getNeedRecognition()) + agentProfile.getSP() + agentProfile.getGPI())/3;

        // not today has been choosen
        if(chanceNotToday>RNG.getInstance().getDouble(0,100)){
            // first check preferredDate
            if(preferedNumberOfDate<=MAX_WAIT_TIME_IN_DAYS && preferedNumberOfDate>=0){
                if(agentProfile.getSusceptibilityCollectionPoint()<RNG.getInstance().getDouble(0, 100)){
                    beginNumberOfDays =  preferedNumberOfDate;
                    endNumberOfDays = preferedNumberOfDate;
                    return;
                }
            }
            // [0 ......... MAX_WAIT_TIME_IN_DAYS]
            double moved = ((agentProfile.getSP() + agentProfile.getGPI())/2 - agentProfile.getNeedRecognition());
            int days = (int) ((moved*50)/100) * MAX_WAIT_TIME_IN_DAYS;

            beginNumberOfDays = RNG.getInstance().getInt(0,days);
            endNumberOfDays = RNG.getInstance().getInt(days,MAX_WAIT_TIME_IN_DAYS);
            return;
        }
        beginNumberOfDays = 0;
        endNumberOfDays = 0;
    }

    public CollectionPoint getCP(){
        return cp;
    }

    public int getBeginNumberOfDays() {
        return beginNumberOfDays;
    }

    public int getEndNumberOfDays() {
        return endNumberOfDays;
    }
}
