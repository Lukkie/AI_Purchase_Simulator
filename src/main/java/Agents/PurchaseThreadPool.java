package Agents;

import Shop.ProductProfile;
import Tools.RNG;

import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 */
public class PurchaseThreadPool implements Runnable{
    public static boolean stop = false;
    private ArrayList<Agent> agents;
    private ArrayList<ProductProfile> products;

    public void start(ArrayList<Agent> agentList, ArrayList<ProductProfile> productList) {
        agents = agentList;
        products = productList;
    }

    @Override
    public void run() {
        while (!stop){
            try {
                int rndAgentIndex = RNG.getInstance().getInt(0,agents.size());
                int rndProductIndex = RNG.getInstance().getInt(0,products.size());
                new PurchaseThread(agents.get(rndAgentIndex), products.get(rndProductIndex)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
