package Agents;

import Tools.RNG;

import java.util.ArrayList;

/**
 * Created by Gilles Callebaut on 19/04/2016.
 *
 */
public class CollectionPoint {
    private GeoLocation location;
    private static int CHANCE_CLOSEST_CP = 90;

    public static ArrayList<CollectionPoint> CPs = new ArrayList<>();


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
        return CPs.get(index);
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }
}
