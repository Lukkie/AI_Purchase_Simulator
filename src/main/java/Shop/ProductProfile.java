package Shop;

import Tools.RNG;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class ProductProfile {
    double greenPerceivedValue;
    double needRecognition;
    double price;
    double perceivedPriceDifference;



    public double getPerceivedPrice () {
        perceivedPriceDifference = (0.5-RNG.getInstance().nextDouble())*1.5; // tussen -0.75 en 0.75
        return (1 - perceivedPriceDifference) * price;
    }

    public double getGPV() {
        return greenPerceivedValue;
    }

    public double getPPD() {
        return perceivedPriceDifference;
    }
}
