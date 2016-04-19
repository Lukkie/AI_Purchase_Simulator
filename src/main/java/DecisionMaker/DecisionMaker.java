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
    private static CollectionPoint cp;


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
        double changeCP = (agentProfile.getSP() + agentProfile.getGPI() + agentProfile.getLocationFlexibility() + agentProfile.getSusceptibilityCollectionPoint())/4;

        double rnd = RNG.getInstance().getDouble(0, 100);

        if(changeCP>rnd)  return true; // delivery to home is selected

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
}
