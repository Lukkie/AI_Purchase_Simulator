package Agents;

import Tools.RNG;

import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
public class CollectionPoint{
    private GeoLocation location;
    private String name;
    private int count = 0;
    private ArrayList<Agent> nearbyAgents = new ArrayList<Agent>();

    private static int CHANCE_CLOSEST_CP = 90;

    private static ArrayList<CollectionPoint> CPs = new ArrayList<>();
    private int counter;

    public static void pushList(ArrayList<CollectionPoint> collectionPoints){
        CPs.addAll(collectionPoints);
    }

    public static CollectionPoint getRandomClosestCP(GeoLocation locationAgent) {
        if (CHANCE_CLOSEST_CP > RNG.getInstance().getDouble(0, 100)) {
            // search the closest CP
            double closest = Double.MAX_VALUE;
            CollectionPoint closestCP = null;
            for (CollectionPoint cp : CPs) {
                double dist = cp.getLocation().distance(locationAgent);
                if (closest > dist ){
                    closestCP = cp;
                    closest = dist;
                }
            }
            return closestCP;
        }
        int cnt = CPs.size();
        int index = RNG.getInstance().getInt(0, cnt);
        CollectionPoint cp = CPs.get(index);
        System.out.println("Random collection Point selected: "+cp);
        return cp;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        String s = "CollectionPoint:\n";
        s += "\tName: "+name+"\n";
        s += "\tLocation: "+location.toString();
        return s;
    }

    public void hasBeenChoosenAsDeleveryPoint(){
        count++;
    }

    public String getName() {
        return name;
    }

    public void addNearbyAgent(Agent agent) {
        nearbyAgents.add(agent);
    }

    public ArrayList<Agent> getNearbyAgents() {
        return nearbyAgents;
    }

    public CollectionPoint(){

    }

    CollectionPoint(CollectionPoint cp) {
        this.location = cp.getLocation();
        this.name = cp.getName();
        this.count = cp.getCounter();
        this.nearbyAgents = cp.getNearbyAgents();
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectionPoint that = (CollectionPoint) o;

        if (count != that.count) return false;
        if (counter != that.counter) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return nearbyAgents != null ? nearbyAgents.equals(that.nearbyAgents) : that.nearbyAgents == null;

    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (nearbyAgents != null ? nearbyAgents.hashCode() : 0);
        result = 31 * result + counter;
        return result;
    }
}
