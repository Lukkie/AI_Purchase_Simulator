package Agents;

import Tools.DateUtil;
import Tools.RNG;

import java.util.Date;

/**
 * This is an agent who will order some products
 * And based on the profile of the product and shop and his own profile
 * the agent will make a choice on how and when the delivery will proceed
 */
public class Agent {
    private static final int MAX_NUMBER_NO_BUY = 100;
    private AgentProfile profile;
    private GeoLocation location;

    private Date lastTimeBoughtSomething;

    public AgentProfile getProfile() {
        return profile;
    }

    public void setProfile(AgentProfile profile) {
        this.profile = profile;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public GeoLocation getLocation() {
        return location;
    }


    @Override
    public String toString() {
        String s = "AgentProfile:";
        s += profile.toString();
        s+="\n";
        s += location.toString();
        return s;
    }

    boolean influenceBuyBehaviour(Agent agent, CollectionPoint choosenCP) {
        double dist = this.location.distance(agent.getLocation());
        System.out.println("Distance between agents: "+dist);
        // to far
        if(dist >= GeoLocation.MAX_DIST_TO_BE_INFLUENCED_IN_KM) return false;
        // not influenced
        if(this.profile.getSusceptibilityCollectionPoint()> RNG.getInstance().getDouble(0, 1)){
            System.out.println("But not influenced");
            return false;
        }

        // adjust willingnessToBuy and recommended CP if not null
        double wb = this.profile.getWB();
        double newWB = wb + (1-wb)*(100-dist*GeoLocation.MAX_DIST_TO_BE_INFLUENCED_IN_KM);
        this.profile.setWB(newWB);
        System.out.println("WB changed from "+wb+" to "+newWB);

        if(choosenCP!=null) this.profile.setRecommendedCP(choosenCP);

        return true;
    }

    public void changeWB() {
        if(lastTimeBoughtSomething==null) return;
        int diff = DateUtil.getDifferenceDays(lastTimeBoughtSomething,PurchaseThreadPool.today);

        if(diff>MAX_NUMBER_NO_BUY){
            double currentWB = this.profile.getWB();
            double max = currentWB*RNG.getInstance().getDouble(0.6,0.9);
            double newWB = currentWB - RNG.getInstance().getDouble(0,max);
            this.profile.setWB(newWB);
            return;
        }
    }

    public void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastTimeBoughtSomething = lastPurchaseDate;
    }
}
