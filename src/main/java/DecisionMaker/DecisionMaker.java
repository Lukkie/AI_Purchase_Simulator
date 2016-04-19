package DecisionMaker;

import Agents.Agent;
import Agents.AgentProfile;
import Agents.CollectionPoint;
import Shop.ProductProfile;
import Shop.ShopProfile;
import Tools.RNG;

import java.util.Date;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 *
 */
public class DecisionMaker {
    private static CollectionPoint cp;
    public static final int MAX_WAIT_TIME_IN_DAYS = 7;


    public static boolean willBuy(AgentProfile agent, ProductProfile product) throws Exception {
        double GPIN = (18/100) * agent.getGPI() + (13/100) * ShopProfile.getGPR() + (17/100) * ShopProfile.getGPT()
                + (12/100) * ShopProfile.getGBI() + (32/100) * ShopProfile.getEA() + (8/100) * product.getGPV();

        double quality = product.getPPD() * agent.getPQP();

        double pp = - product.getPPD() * agent.getSP();

        if(quality<0) quality = 100 - quality;
        if(pp<0) pp = 100 - pp;

        double changeToBuy = (GPIN + quality + pp + agent.getWB())/4;

        RNG rng = RNG.getInstance();
        rng.initGaussian(2,0);
        double rndDouble = rng.nextGaussian();

        return (changeToBuy / 100) > rndDouble;
    }


    /*
    @return boolean: if(true) home delivery else collectionPoint
                     if(false) getCollectionPoint()
     */
    public static boolean deliveryToHome(Agent agent, ProductProfile product) throws Exception {
        AgentProfile agentProfile = agent.getProfile();
        double chanceCP = (agentProfile.getSP() + agentProfile.getGPI() + agentProfile.getLocationFlexibility() + agentProfile.getSusceptibilityCollectionPoint())/4;

        double rnd = RNG.getInstance().getDouble(0, 100);

        if(chanceCP>rnd)  return true; // delivery to home is selected

        // if susceptible for CP -> choose CP
        if(agentProfile.getSusceptibilityCollectionPoint()<RNG.getInstance().getDouble(0, 100)){
            cp = agentProfile.getCP();
        }
        // else choose closest CP
        else{
            cp = CollectionPoint.getRandomClosestCP(agent.getLocation());
        }

        return false;
    }

    /**
     *
     * @param agent
     * @param product
     * @param preferedNumberOfDate : number of days till preferred pickup
     * @return
     */
    public static int numberOfDays(AgentProfile agent, ProductProfile product, int preferedNumberOfDate){
        double chanceNotToday = ((100-agent.getNeedRecognition()) + agent.getSP() + agent.getGPI())/3;

        // not today has been choosen
        if(chanceNotToday>RNG.getInstance().getDouble(0,100)){
            // first check preferredDate
            if(preferedNumberOfDate<=MAX_WAIT_TIME_IN_DAYS){
                if(agent.getSusceptibilityCollectionPoint()<RNG.getInstance().getDouble(0, 100)){
                    return preferedNumberOfDate;
                }
            }
            // [0 ......... MAX_WAIT_TIME_IN_DAYS]
            double moved = ((agent.getSP() + agent.getGPI())/2 - agent.getNeedRecognition());
            double days = ((moved*50)/100) * MAX_WAIT_TIME_IN_DAYS;
            return (int) days;

        }
        return 0;
    }

    public static CollectionPoint getCP(){
        CollectionPoint temp = cp;
        cp = null;
        return temp;
    }
}
