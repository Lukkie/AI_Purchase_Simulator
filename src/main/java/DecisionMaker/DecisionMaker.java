package DecisionMaker;

import Agents.AgentProfile;
import Shop.ProductProfile;
import Shop.ShopProfile;
import Tools.RNG;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 *
 */
public class DecisionMaker {

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
}
