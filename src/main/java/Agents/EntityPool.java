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
    private static ArrayList<CollectionPoint> collectionPoints;


    public static void setAgents(ArrayList<Agent> agentList) {
        agents = agentList;
    }

    static void setProducts(ArrayList<ProductProfile> productList) {
        products = productList;
    }



    static Agent getRandomAgent(Agent me){
        if(me==null) return agents.get(RNG.getInstance().getInt(0, agents.size()));
        Agent rndAgent;
        do {
             rndAgent = agents.get(RNG.getInstance().getInt(0, agents.size()));
        }while(rndAgent.equals(me));

        return rndAgent;
    }


    static ProductProfile getRandomProduct(){
        return products.get(RNG.getInstance().getInt(0,products.size()));
    }

    static CollectionPoint getRandomCP(){
        return collectionPoints.get(RNG.getInstance().getInt(0,collectionPoints.size()));
    }
    public static void setCollectionPoints(ArrayList<CollectionPoint> collectionPoints) {
        EntityPool.collectionPoints = collectionPoints;
    }
}
