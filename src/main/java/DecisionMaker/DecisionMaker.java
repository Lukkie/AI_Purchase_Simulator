package DecisionMaker;

import Agents.Agent;
import Agents.AgentProfile;
import Agents.CollectionPoint;
import Shop.ProductProfile;
import Shop.ShopProfile;
import Tools.RNG;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 */
public class DecisionMaker {

    public static final int MAX_WAIT_TIME_IN_DAYS = 7;

    private Agent agent;
    private ProductProfile product;
    private CollectionPoint cp;
    private AgentProfile agentProfile;

    private int beginNumberOfDays = 0, endNumberOfDays = 0;

    public DecisionMaker(Agent agent, ProductProfile product) {
        this.agent = agent;
        this.product = product;
        this.agentProfile = agent.getProfile();
    }


    public boolean willBuy() throws Exception {

        double GPIN = (18 / 100) * agentProfile.getGPI() + (13 / 100) * ShopProfile.getGPR() + (17 / 100) * ShopProfile.getGPT()
                + (12 / 100) * ShopProfile.getGBI() + (32 / 100) * ShopProfile.getEA() + (8 / 100) * product.getGPV();

        // be sure the agent is influenced by the quality and the perceived price
        double quality;
        double pp;

        // when ppd<0 -> price < preceivedPrice => so that's good, because we won't cheap products
        if (product.getPPD() < 0) {
            pp = Math.abs(product.getPPD());
            // price is lower dan pp so -> will to buy down
            quality = 1 - Math.abs(product.getPPD());
        } else {
            pp = 1 - Math.abs(product.getPPD());
            quality = Math.abs(product.getPPD());
        }

        //first change WB based on previous purchases
        agent.changeWB();


        double changeToBuy;
        if (product.getPPD() == 0) {
            changeToBuy = (GPIN + agentProfile.getWB() + agentProfile.getShopReputation()) / 3;
        } else {
            /*
            Mean {GPIN, ServiceAfterPurchase, Availability, WB, ReputationShop}
            and if PPD is not zero also {Quality, PP}
             */
            changeToBuy = (GPIN + (quality * agentProfile.getPQP()) + (pp * agentProfile.getSP())
                    + agentProfile.getWB() + agentProfile.getShopReputation()
            + ShopProfile.getService() + product.getAvailability())
                    / (7);
        }

        if(agentProfile.hasAcceptedCPOffer()){
            double max = 1-changeToBuy;
            changeToBuy += max*RNG.getInstance().getDouble(0.3,1);
        }

        if (changeToBuy < 0) changeToBuy = 0;

        RNG rng = RNG.getInstance();
        double rndDouble = rng.getDouble(0, 1);
        System.out.println("ChangeToBuy: " + changeToBuy + "\tRandom: " + rndDouble);
        return (changeToBuy) > rndDouble;
    }


    /*
    @return boolean: if(true) home delivery else collectionPoint
                     if(false) getCollectionPoint()
     */
    public boolean deliveryToHome(boolean wasInfluenced) {

        if(agentProfile.hasAcceptedCPOffer()){
            cp = agentProfile.getRecommendedCP();
            cp.hasBeenChoosenAsDeleveryPoint();
            agentProfile.setAcceptedCPOffer(false);
            return false;
        }

        /*
        If agent is alwaysHome he has a chance of 99% to request home delivery
         */
        if (this.agentProfile.isAlwaysAtHome()) {
            if (RNG.chance(99, 0, 100)) {
                return true;
            }
        }
        double chanceCP;
        if (wasInfluenced) {
            chanceCP = (agentProfile.getSP() + agentProfile.getGPI() + agentProfile.getLocationFlexibility() + agentProfile.getSusceptibility()) / 4;
        } else {
            chanceCP = (agentProfile.getSP() + agentProfile.getGPI() + agentProfile.getLocationFlexibility()) / 3;
        }

        double rnd = RNG.getInstance().getDouble(0, 1);

        if (chanceCP < rnd) return true; // delivery to home is selected

        // if susceptible for CP -> choose CP
        if (RNG.chance(agentProfile.getSusceptibility(), 0, 1)) {
            System.out.println("\tAgent is susceptible for CP");
            cp = agentProfile.getRecommendedCP();
            System.out.println("\tRecommended CP: " + cp);
            if (cp == null) {
                cp = CollectionPoint.getRandomClosestCP(agent.getLocation());
            }
        }
        // else choose closest CP
        else {
            cp = CollectionPoint.getRandomClosestCP(agent.getLocation());
        }
        cp.hasBeenChoosenAsDeleveryPoint();
        return false;
    }


    public void numberOfDays(int preferredNumberOfDate) {
        System.out.println("Prefered Number of days: " + preferredNumberOfDate);
        double chanceNotToday = ((1 - agentProfile.getNeedRecognition()) + agentProfile.getSP() + agentProfile.getGPI()) / 3;

        // not today has been choosen
        if (RNG.chance(chanceNotToday, 0, 1)) {
            // first check preferredDate
            if (preferredNumberOfDate <= MAX_WAIT_TIME_IN_DAYS && preferredNumberOfDate >= 0) {
                if (RNG.chance(agentProfile.getSusceptibility(), 0, 1)) {
                    beginNumberOfDays = preferredNumberOfDate;
                    endNumberOfDays = preferredNumberOfDate;
                    return;
                }
            }
            // [0 ......... MAX_WAIT_TIME_IN_DAYS]
            double moved = ((agentProfile.getSP() + agentProfile.getGPI()) / 2 - agentProfile.getNeedRecognition());
            int days = (int) (MAX_WAIT_TIME_IN_DAYS / 2) + (int) (moved * MAX_WAIT_TIME_IN_DAYS / 2);
            System.out.println("number of days : " + days);
            if (days > 0) {
                beginNumberOfDays = RNG.getInstance().getInt(0, days);
                endNumberOfDays = RNG.getInstance().getInt(days, MAX_WAIT_TIME_IN_DAYS);
            } else {
                beginNumberOfDays = 0;
                endNumberOfDays = 0;
            }
        }
        // He/She wants them today
    }

    public CollectionPoint getCP() {
        return cp;
    }

    public int getBeginNumberOfDays() {
        return beginNumberOfDays;
    }

    public int getEndNumberOfDays() {
        return endNumberOfDays;
    }


    public static boolean acceptDiscount(Agent agent) {
        AgentProfile agentProfile = agent.getProfile();

        if (agentProfile.isAlwaysAtHome()) {
            if (RNG.chance(99, 0, 100)) {
                //System.out.println("Offer not accepted because of always home");
                return false;
            }
        }
        double chanceCP = (agentProfile.getSP() + agentProfile.getGPI() + agentProfile.getLocationFlexibility()) / 3;

        double rnd = RNG.getInstance().getDouble(0, 1);

        if (chanceCP > rnd) return false; // delivery to home is selected

        return true;
    }
}
