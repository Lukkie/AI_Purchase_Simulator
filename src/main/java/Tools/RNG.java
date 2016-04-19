package Tools;

import java.util.Random;

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

    public synchronized void initGuassion(double desiredStandardDeviation, double desiredMean) {
        this.initGaussion = true;
        this.standardDev = desiredStandardDeviation;
        this.mean = desiredMean;
    }

    public synchronized double nexGaussion() throws Exception {
        if(!initGaussion) throw new Exception("Call initGaussion first!");
        return random.nextGaussian()*standardDev+mean;
    }

}
