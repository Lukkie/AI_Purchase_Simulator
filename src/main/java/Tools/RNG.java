package Tools;

import Agents.GeoLocation;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Lukas on 19-Apr-16.
 *
 */
public class RNG {
    private static RNG rng = new RNG();
    private Random random = new Random();
    private double standardDev;
    private double mean;
    private boolean initGaussion;

    public static synchronized RNG getInstance() {
        return rng;
    }

    public synchronized Random getRandom() {
        return random;
    }

    public synchronized double nextDouble() {
        return random.nextDouble();
    }

    public synchronized int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public synchronized void initGaussian(double desiredStandardDeviation, double desiredMean) {
        this.initGaussion = true;
        this.standardDev = desiredStandardDeviation;
        this.mean = desiredMean;
    }

    public synchronized double nextGaussian() throws Exception {
        if(!initGaussion) throw new Exception("Call initGaussion first!");
        return random.nextGaussian()*standardDev+mean;
    }


    public synchronized double nextGaussian(double desiredStandardDeviation, double desiredMean) {
        return random.nextGaussian()*desiredStandardDeviation+desiredMean;
    }

    /*
    min inclusive and max exclusive
     */
    public synchronized double getDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public synchronized int getInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    public synchronized GeoLocation getRandomLocation() {
        GeoLocation loc = new GeoLocation();
        final double lat_min = 49.9;
        final double lat_max = 51.5;
        final double long_min = 1.3;
        final double long_max = 7.0;

        double lat = lat_min + random.nextDouble() * (lat_max - lat_min);
        double longit = long_min + random.nextDouble() * (long_max - long_min);

        loc.setLatitude(lat);
        loc.setLongitude(longit);
        return loc;
    }


    public static boolean chance(double val, int min, int max) {
        return val > RNG.getInstance().getDouble(min,max);
    }
}
