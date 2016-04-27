package Agents;

/**
 *
 */
public class GeoLocation {
    private double latitude;
    private double longitude;

    public final static double MAX_DIST_TO_BE_INFLUENCED_IN_KM = 10;


    public double distance(GeoLocation location) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoLocation that = (GeoLocation) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        return Double.compare(that.longitude, longitude) == 0;

    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "\n\tlatitude\t" + latitude +
                "\n\tlongitude\t" + longitude +
                "\n}";
    }
}
