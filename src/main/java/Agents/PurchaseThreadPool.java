package Agents;

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

    public PurchaseThreadPool(ArrayList<Agent> agentList, ArrayList<ProductProfile> productList) {
        EntityPool.setAgents(agentList);
        EntityPool.setProducts(productList);
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
                // if prev agent influenced another agent, the other agent has a higher chance of buying
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
