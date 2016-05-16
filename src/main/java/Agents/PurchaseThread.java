package Agents;

import DecisionMaker.DecisionMaker;
import GUI.GUIController;
import Shop.ProductProfile;
import javafx.application.Platform;

import java.util.Date;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
class PurchaseThread {
    private boolean influencedByPrevAgent;
    private Agent agent;
    private ProductProfile product;
    private CollectionPoint cp;
    private Date today;
    private GUIController gui;

    PurchaseThread(Agent agent, ProductProfile product, boolean influencedByPrevAgent, Date today, GUIController gui) {
        this.agent = agent;
        this.product = product;
        this.influencedByPrevAgent = influencedByPrevAgent;
        this.today = (Date) today.clone();
        this.gui = gui;
    }

    Agent start() throws Exception {
        DecisionMaker decisionMaker = new DecisionMaker(agent, product);

        if(!decisionMaker.willBuy()){
            System.out.println("\t\t Will NOT buy");
            Platform.runLater(() -> {
                gui.incrementWillNotBuy();
            });

            return null;
        }else{
            System.out.println("\t\t Will buy");
            Platform.runLater(() -> gui.incrementWillBuy());
        }

        boolean isHomeDelivery = decisionMaker.deliveryToHome(this.influencedByPrevAgent);
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

        // influence random other agent
        Agent influencedAgent = EntityPool.getRandomAgent(this.agent);
        boolean isOtherAgentInfluenced = false;
        if(cp!=null) isOtherAgentInfluenced = influencedAgent.influenceBuyBehaviour(agent,cp, Tools.DateUtil.addDays(today,beginNumOfDays));
        System.out.println("Is other agent influenced: "+isOtherAgentInfluenced);


        agent.setLastPurchaseDate(today);
        Tools.Logger.writeDelivery(agent,product,cp,today, earliest,latest,beginNumOfDays,endNumOfDays,isHomeDelivery,decisionMaker.buyBasedOnRecommendation,recommendedDate,agent.getProfile().getRecommendedCP());
        Date lastDate = Tools.DateUtil.addDays(today,endNumOfDays);
        PurchaseThreadPool.addDelivery(this.agent, lastDate);


        return (isOtherAgentInfluenced) ? influencedAgent : null;
    }
}
