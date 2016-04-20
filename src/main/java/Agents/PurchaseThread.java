package Agents;

import DecisionMaker.DecisionMaker;
import Shop.ProductProfile;

import java.util.Date;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
class PurchaseThread {
    private final Agent prevAgent;
    private Agent agent;
    private ProductProfile product;
    private CollectionPoint cp;
    private Date today;

    PurchaseThread(Agent agent, ProductProfile product, Agent prevAgent, Date today) {
        this.agent = agent;
        this.product = product;
        this.prevAgent = prevAgent;
        this.today = (Date) today.clone();
    }

    Agent start() throws Exception {
        DecisionMaker decisionMaker = new DecisionMaker(agent, product);

        if(!decisionMaker.willBuy()){
            System.out.println("\t\t Will NOT buy");
            return null;
        }else{
            System.out.println("\t\t Will buy");
        }


        boolean isHomeDelivery = decisionMaker.deliveryToHome();
        int preferredNamOfDays = Tools.DateUtil.getDifferenceDays(today, this.agent.getProfile().getRecommendedDate());
        decisionMaker.numberOfDays(preferredNamOfDays);

        int beginNumOfDays = decisionMaker.getBeginNumberOfDays();
        int endNumOfDays = decisionMaker.getEndNumberOfDays();

        //TODO Data objects

        if(!isHomeDelivery) cp = decisionMaker.getCP();
        //Tools.Logger.writeDelivery(agent,product,cp,date);

        // influence other agents
        if(prevAgent==null) return null;
        CollectionPoint choosenCP;
        if(cp.getLocation().equals(agent.getLocation())){
            choosenCP=null;
        }else{
            choosenCP = cp;
        }
        Agent influencedAgent = EntityPool.getRandomAgent();
        boolean isInfluenced = influencedAgent.influenceBuyBehaviour(agent,choosenCP);


        return (isInfluenced) ? influencedAgent : null;
    }
}
