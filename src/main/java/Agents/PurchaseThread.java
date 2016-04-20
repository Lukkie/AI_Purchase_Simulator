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
        Date recommendedDate = this.agent.getProfile().getRecommendedDate();
        int preferredNumOfDays;
        if(recommendedDate==null){
            preferredNumOfDays = -1;
        }else{
            preferredNumOfDays = Tools.DateUtil.getDifferenceDays(today, recommendedDate);
        }


        decisionMaker.numberOfDays(preferredNumOfDays);

        int beginNumOfDays = decisionMaker.getBeginNumberOfDays();
        int endNumOfDays = decisionMaker.getEndNumberOfDays();

        Date earliest = Tools.DateUtil.addDays(today,beginNumOfDays);
        Date latest = Tools.DateUtil.addDays(today,endNumOfDays);

        if(!isHomeDelivery) cp = decisionMaker.getCP();


        boolean isInfluenced;
        // influence other agents
        if(prevAgent==null) isInfluenced =  false;
        CollectionPoint choosenCP;
        if(cp.getLocation().equals(agent.getLocation())){
            choosenCP=null;
        }else{
            choosenCP = cp;
        }
        Agent influencedAgent = EntityPool.getRandomAgent();
        isInfluenced = influencedAgent.influenceBuyBehaviour(agent,choosenCP);

        Tools.Logger.writeDelivery(agent,product,cp,today, earliest,latest,beginNumOfDays,endNumOfDays,isHomeDelivery,isInfluenced,recommendedDate,agent.getProfile().getRecommendedCP());
        return (isInfluenced) ? influencedAgent : null;
    }
}
