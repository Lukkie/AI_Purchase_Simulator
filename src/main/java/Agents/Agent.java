package Agents;

import Tools.DateUtil;
import Tools.RNG;

import java.util.Date;

/**
 * This is an agent who will order some products
 * And based on the profile of the product and shop and his own profile
 * the agent will make a choice on how and when the delivery will proceed
 *
 */
public class Agent {
    private static final int MAX_NUMBER_NO_BUY = 100;
    private AgentProfile profile;
    private GeoLocation location;

    private Date lastTimeBoughtSomething;
    private int ID = RNG.getInstance().nextInt(64486465);

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

    boolean influenceBuyBehaviour(Agent agent, CollectionPoint choosenCP, Date choosenDate) {
        double dist = this.location.distance(agent.getLocation());
        System.out.println("Distance between agents: "+dist);
        // to far
        //TODO OVERRIDE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // use Geolaction.MAX
        if(dist >= 100) return false;
        // not influenced
        if(this.profile.getSusceptibility()> RNG.getInstance().getDouble(0, 1)){
            System.out.println("But not influenced");
            return false;
        }

        // adjust willingnessToBuy and recommended CP if not null
        double wb = this.profile.getWB();
        //TODO
        // double newWB = wb + (1-wb)*((100-dist*GeoLocation.MAX_DIST_TO_BE_INFLUENCED_IN_KM)/100);
        double newWB = wb + (1-wb)*((100-dist*GeoLocation.MAX_DIST_TO_BE_INFLUENCED_IN_KM)/(100*100));
        this.profile.setWB(newWB);
        System.out.println("WB changed from "+wb+" to "+newWB);

        if(choosenCP!=null){
            this.profile.setRecommendedCP(choosenCP);
            this.profile.setRecommendedDate(choosenDate);
        }

        return true;
    }

    public void changeWB() {
        if(lastTimeBoughtSomething==null) return;
        int diff = DateUtil.getDifferenceDays(lastTimeBoughtSomething,PurchaseThreadPool.today);
        System.out.println("Previous buy was "+diff+" day(s) ago");
        if(diff>MAX_NUMBER_NO_BUY){
            double currentWB = this.profile.getWB();
            if(currentWB<0.2) return;
            double max = currentWB*RNG.getInstance().getDouble(0.6,0.9);
            double newWB = currentWB - RNG.getInstance().getDouble(0,max);
            System.out.println("WB is changed from "+currentWB+" to "+newWB);
            this.profile.setWB(newWB);
        }
    }

    void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastTimeBoughtSomething = lastPurchaseDate;
    }


    void wordOfMouth() {
        int num = RNG.getInstance().getInt(0,5);
        System.out.println("WordOfMouth num: "+num);
        for(int i=0; i<num;i++){
            Agent agent = EntityPool.getRandomAgent(this);
            agent.receivedWordOfMouth(RNG.getInstance().getDouble(-1,1));
        }
    }

    private void receivedWordOfMouth(double shopReputationChangeFactor) {
        if(RNG.chance(this.profile.getSusceptibility(),0, 1) || shopReputationChangeFactor==0){
            System.out.println("But not influenced");
            return;
        }
        double currentShopReputation = this.profile.getShopReputation();
        double newCurrentShopReputation;
        double maxChange;
        if(shopReputationChangeFactor>=0){
            maxChange = (1 - currentShopReputation);
        }else{
            maxChange = currentShopReputation;
        }
        newCurrentShopReputation = currentShopReputation + maxChange*shopReputationChangeFactor;
        System.out.println("Shop reputation changed from: "+currentShopReputation+" to: "+newCurrentShopReputation+
        "(factor: "+shopReputationChangeFactor+")");
        this.profile.setShopReputation(newCurrentShopReputation);
    }

    public int getID() {
        return ID;
    }

    public void offerHasBeenGranted(CollectionPoint cp) {
        this.profile.setAcceptedCPOffer(true);
        this.profile.setRecommendedCP(cp);
    }
}
