package Agents;

import Shop.ProductProfile;
import Tools.RNG;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
public class PurchaseThreadPool implements Runnable{
    public boolean stop = false;
    public static final int CHANCE_INFLUENCED_AGENT_WILL_SHOP = 70;



    private Agent prevAgent = null;
    private Agent influencedAgent = null;

    public Date today = new Date();
    private int dateChangeChanceCounter = 5;

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
                Agent agent;
                ProductProfile product = EntityPool.getRandomProduct();
                // if prev agent influenced another agent, the other agent has a higher chance of buying
                if(influencedAgent==null){
                    agent = EntityPool.getRandomAgent();
                }else {
                    if (RNG.getInstance().getInt(0, 100) < CHANCE_INFLUENCED_AGENT_WILL_SHOP) {
                        // the agent is willing to buy online
                        agent = influencedAgent;
                    } else {
                        // otherwise choose random agent
                        agent = EntityPool.getRandomAgent();
                    }
                }

                System.out.println("\t--------------- PurchaseThread started ---------------");
                influencedAgent = new PurchaseThread(agent, product, prevAgent).start();
                System.out.println("\t--------------- PurchaseThread ended ---------------");
                prevAgent = agent;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("---------------------- ThreadPool ended ----------------------");
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
    }
}
