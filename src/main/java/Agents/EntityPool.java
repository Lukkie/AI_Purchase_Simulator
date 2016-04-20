package Agents;

import Shop.ProductProfile;
import Tools.RNG;

import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 20/04/2016.
 */
public class EntityPool {
    private static ArrayList<Agent> agents;
    private static ArrayList<ProductProfile> products;


    public static void setAgents(ArrayList<Agent> agentList) {
        agents = agentList;
    }

    public static void setProducts(ArrayList<ProductProfile> productList) {
        products = productList;
    }

    public static Agent getRandomAgent(){
        return agents.get(RNG.getInstance().getInt(0,agents.size()));
    }


    public static ProductProfile getRandomProduct(){
        return products.get(RNG.getInstance().getInt(0,products.size()));
    }
}
