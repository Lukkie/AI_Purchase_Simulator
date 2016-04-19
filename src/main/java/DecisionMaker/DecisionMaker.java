package DecisionMaker;

import Agents.AgentProfile;
import Shop.ProductProfile;
import Tools.RNG;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 */
public class DecisionMaker {

    public static boolean willBuy(AgentProfile agent, ProductProfile product) throws Exception {

        int GPIN = (18/100) * agent.getGPI() + (13/100) * Shop.getGPR() + (17/100) * Shop.getGPT()
                + (12/100) * Shop.getGBI() + (32/100) * Shop.getEA() + (8/100) * product.getGPV();

        int quality = product.getPPD() * agent.getPQP();

        int pp = product.getPPD() * agent.getSP();

        if(quality<0) quality = 100 - quality;
        if(pp<0) pp = 100 - pp;

        int changeToBuy = (GPIN + quality + pp + agent.getWBO())/4;

        RNG rng = RNG.getInstance();
        rng.initGuassian(2,0);
        double rndDouble = rng.nexGaussian();

        return (changeToBuy / 100) > rndDouble;
    }
}
