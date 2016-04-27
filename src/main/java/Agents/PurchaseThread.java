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
        if(isHomeDelivery) System.out.println("\t\t HOME delivery");
        else System.out.println("\t\t CP delivery");

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
        if(!isHomeDelivery && cp.getLocation().equals(agent.getLocation())){
            choosenCP=null;
        }else{
            choosenCP = cp;
        }
        Agent influencedAgent = EntityPool.getRandomAgent(this.agent);
        isInfluenced = influencedAgent.influenceBuyBehaviour(agent,choosenCP);
        System.out.println("Is other agent influenced: "+isInfluenced);
        if(isInfluenced) System.out.println("\t\t\t\t\t\t\t\t\t\t!!!!!!!!!!!!!! Is other agent influenced: "+isInfluenced);
        agent.setLastPurchaseDate(today);
        Tools.Logger.writeDelivery(agent,product,cp,today, earliest,latest,beginNumOfDays,endNumOfDays,isHomeDelivery,isInfluenced,recommendedDate,agent.getProfile().getRecommendedCP());
        Date lastDate = Tools.DateUtil.addDays(today,endNumOfDays);
        PurchaseThreadPool.addDelivery(this.agent, lastDate);
        return (isInfluenced) ? influencedAgent : null;
    }
}
