package Agents;

/**
 *
 */
public class GeoLocation {
    private double latitude;
    private double longitude;


    public double distance(GeoLocation location, String unit) {
        double R=6371; // radius earth (in km)
        double theta = longitude - location.getLongitude();
        //dist = arccos(sin(lat1) · sin(lat2) + cos(lat1) · cos(lat2) · cos(lon1 - lon2)) · R
        double dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(location.getLatitude())) + Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(location.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist) * R;
        return dist;
    }

    private static double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
}

    /* setters and getters */

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
