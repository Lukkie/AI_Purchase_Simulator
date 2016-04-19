package Agents;

import DecisionMaker.DecisionMaker;
import Shop.ProductProfile;

import java.util.Date;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
class PurchaseThread {
    private Agent agent;
    private ProductProfile product;
    private CollectionPoint cp;
    private Date date;

    PurchaseThread(Agent agent, ProductProfile product) {
        this.agent = agent;
        this.product = product;
    }

    void start() throws Exception {
        DecisionMaker decisionMaker = new DecisionMaker(agent, product);

        if(!decisionMaker.willBuy()) return;

        boolean isHomeDelevery = decisionMaker.deliveryToHome();
        int pereferredNumOfDays = 0; //TODO in agentProfile.CP and agentPrifle.CPDate
        decisionMaker.numberOfDays(pereferredNumOfDays);

        int beginNumOfDays = decisionMaker.getBeginNumberOfDays();
        int endNumOfDays = decisionMaker.getEndNumberOfDays();

        //TODO Data objects

        if(!isHomeDelevery) cp = decisionMaker.getCP();
        //Tools.Logger.writeDelivery(agent,product,cp,date);

        //TODO influence other agents
    }
}
