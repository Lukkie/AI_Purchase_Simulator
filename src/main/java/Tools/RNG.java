package Tools;

import java.util.Random;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class RNG {
    private static RNG rng = new RNG();
    public static synchronized RNG getInstance() {
        return rng;
    }



    private Random random;



    public synchronized Random getRandom() {
        return random;
    }

    public synchronized double nextDouble() {
        return random.nextDouble();
    }

    public synchronized int nextInt(int bound) {
        return random.nextInt(bound);
    }


}
