package Agents;

import DecisionMaker.DecisionMaker;
import Shop.ProductProfile;
import Tools.RNG;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
public class PurchaseThreadPool implements Runnable{
    public boolean stop = false;
    public static final int CHANCE_INFLUENCED_AGENT_WILL_SHOP = 70;



    private boolean influencedByPrevAgent = false;
    private Agent prevAgent = null;
    private Agent influencedAgent = null;

    public static Date today = new Date();
    private static int dateChangeChanceCounter = 5;

    private static HashMap<Date,ArrayList<Agent>> deliveries = new HashMap<>();

    public PurchaseThreadPool(ArrayList<Agent> agentList, ArrayList<ProductProfile> productList, ArrayList<CollectionPoint> cps) {
        EntityPool.setAgents(agentList);
        EntityPool.setProducts(productList);
        EntityPool.setCollectionPoints(cps);
    }

    @Override
    public void run() {
        System.out.println("---------------------- ThreadPool started ----------------------");
        while (!stop){
            try {
                changeDay();
                checkDay();
                Agent agent;
                ProductProfile product = EntityPool.getRandomProduct();
                // if prev agent influenced another agent, the other (influenced) agent has a higher chance of buying
                if(influencedAgent==null){
                    agent = EntityPool.getRandomAgent(null);
                }else {
                    if (RNG.chance(CHANCE_INFLUENCED_AGENT_WILL_SHOP,0, 100)) {
                        // the agent is willing to buy online
                        System.out.println("Influenced Agent willing to buy!!");
                        agent = influencedAgent;
                        this.influencedByPrevAgent= true;
                    } else {
                        // otherwise choose random agent
                        agent = EntityPool.getRandomAgent(null);
                    }
                }
                System.out.println("\t--------------- PurchaseThread started ---------------");
                System.out.println("\t "+agent);
                System.out.println("\t "+product);
                influencedAgent = new PurchaseThread(agent, product, influencedByPrevAgent, today).start();
                System.out.println("\t--------------- PurchaseThread ended ---------------");
                prevAgent = agent;
                makeCPOffer();
                //Thread.sleep(1000);
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                    Thread.sleep(100000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            }
        }
        System.out.println("---------------------- ThreadPool ended ----------------------");
    }

    private void makeCPOffer() {
        // 15% chance
        if(RNG.chance(15,0,100)){
            CollectionPoint cp = EntityPool.getRandomCP();

            // how many ppl need to accept offer? [1,20[
            int numOfNeededAcctepedOffers = RNG.getInstance().getInt(1,20);
            System.out.println("CP ("+(cp.getName())+") has made an offer: "+numOfNeededAcctepedOffers+" with "+cp.getNearbyAgents().size()+" agents nearby.");
            ArrayList<Agent> acceptedAgents = new ArrayList<>();
            for(Agent a: cp.getNearbyAgents()){
                if(DecisionMaker.acceptDiscount(a)){
                    acceptedAgents.add(a);
                }
            }

            if(acceptedAgents.size()<numOfNeededAcctepedOffers){
                System.out.println("Offer not accepted! #agents:"+acceptedAgents.size());
                return;
            }

            System.out.println("Offer is granted! #agents:"+acceptedAgents.size());
            for(Agent a: acceptedAgents){
                a.offerHasBeenGranted(cp);
            }
        }
    }

    /**
     * Spread rumors or advise a shop when your delivery has been received
     * Word of Mouth
     */
    private void checkDay() {
        ArrayList<Date> datesToBeRemoved = new ArrayList<>();
        for(Date d: deliveries.keySet()){
            if(Tools.DateUtil.getDifferenceDays(today, d)>=0){
                for(Agent a: deliveries.get(d)){
                    a.wordOfMouth();
                }
                datesToBeRemoved.add(d);
            }
        }
        for(Date d: datesToBeRemoved){
            deliveries.remove(d);
        }
    }

    /**
     * Randomly chance the date based on the amount of deliveries made
     */
    private void changeDay() {
        if(RNG.getInstance().getInt(0,100) < dateChangeChanceCounter){
            today = Tools.DateUtil.addDays(today,1);
            dateChangeChanceCounter = 5;
        }else{
            // change chance of date with next increment
            dateChangeChanceCounter += Tools.RNG.getInstance().getDouble(0,10);
        }
        System.out.println("Current day: "+today.getDay());
    }

    public static void addDelivery(Agent a, Date lastDay){
        System.out.println("\nAdded Agent:"+a+" date: "+lastDay);
        if(deliveries.containsKey(lastDay)){
            deliveries.get(lastDay).add(a);
        }else{
            ArrayList<Agent> agents = new ArrayList<>();
            agents.add(a);
            deliveries.put(lastDay,new ArrayList<>(agents));
        }
    }
}
