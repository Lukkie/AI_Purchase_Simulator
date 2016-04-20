package Agents;

import Tools.RNG;

/**
 * This is an agent who will order some products
 * And based on the profile of the product and shop and his own profile
 * the agent will make a choice on how and when the delivery will proceed
 */
public class Agent {
    private AgentProfile profile;
    private GeoLocation location;

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

        // to far
        if(dist >= GeoLocation.MAX_DIST_TO_BE_INFLUENCED_IN_KM) return false;
        // not influenced
        if(this.profile.getSusceptibilityCollectionPoint()> RNG.getInstance().getDouble(0, 100)) return false;

        // adjust willingnessToBuy and recommended CP if not null
        double wb = this.profile.getWB();
        this.profile.setCollectionPointRecommendationFactor(wb + (100-wb)*(100-dist*GeoLocation.MAX_DIST_TO_BE_INFLUENCED_IN_KM));

        if(choosenCP!=null) this.profile.setRecommendedCP(choosenCP);

        return true;
    }
}
