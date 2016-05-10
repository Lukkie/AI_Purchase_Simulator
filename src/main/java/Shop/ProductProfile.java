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
    private int ID = RNG.getInstance().nextInt(64486465);
    private double availability;

    public ProductProfile(){
        perceivedPriceDifference = (0.5-RNG.getInstance().nextDouble())*1.5; // tussen -0.75 en 0.75
    }

    public double getPerceivedPrice () {
        return (1 - perceivedPriceDifference) * price;
    }

    public double getGPV() {
        return greenPerceivedValue;
    }

    public double getPPD() {
        return perceivedPriceDifference;
    }

    public void setGreenPerceivedValue(double greenPerceivedValue) {
        this.greenPerceivedValue = greenPerceivedValue;
    }

    public void setNeedRecognition(double needRecognition) {
        this.needRecognition = needRecognition;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAvailability() {
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }



    @Override
    public String toString() {
        String s = "\nProduct{\n";
        s += "\tGPV\t\t\t\t\t" + greenPerceivedValue +"\n";
        s += "\tNeed Recognition\t" + needRecognition +"\n";
        s += "\tPrice\t\t\t\t" + price+"\n}";
        return s;
    }

    public int getID() {
        return ID;
    }
}
