package Agents;

import Shop.ProductProfile;
import Tools.RNG;

import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
public class PurchaseThreadPool implements Runnable{
    public static boolean stop = false;
    public static final int CHANCE_INFLUENCED_AGENT_WILL_SHOP = 70;


    private Agent prevAgent = null;
    private Agent influencedAgent = null;

    public void start(ArrayList<Agent> agentList, ArrayList<ProductProfile> productList) {
        EntityPool.setAgents(agentList);
        EntityPool.setProducts(productList);
    }

    @Override
    public void run() {
        while (!stop){
            try {
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
                influencedAgent = new PurchaseThread(agent, product, prevAgent).start();
                prevAgent = agent;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
