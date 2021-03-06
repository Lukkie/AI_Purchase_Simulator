package Tools;

import Agents.Agent;
import Agents.CollectionPoint;
import GUI.GUIController;
import Shop.ProductProfile;
import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gilles Callebaut on 20/04/2016.
 *
 */
public class Logger {
    public static File file = null;
    private static boolean first = true;

    private static int isHomeDeliveryCounter =0;
    private static int hasBoughtFromRecommendedCPCounter = 0;
    private static int recommendedCPCounter = 0; // number of recommendations for CP's (not necessarily chosen)
    private static double totalBeginNumOfDays = 0, totalEndNumOfDays= 0;
    private static int numOfLogs = 0;

    public static GUIController gui;

    public static void writeDelivery(Agent agent, ProductProfile product, CollectionPoint cp, Date today, Date earliest, Date latest, int beginNumOfDays, int endNumOfDays,
                                     boolean isHomeDelivery, boolean hasBoughtFromRecommendedCP, Date recommendedDate, CollectionPoint recommendedCP) {


        updateVariables(isHomeDelivery, hasBoughtFromRecommendedCP, recommendedCP, beginNumOfDays, endNumOfDays);
        try {
            FileWriter writer = new FileWriter(file, true);
            File fileBuyVsCP = new File(file.getPath().replace(".csv","")+"_buy_vs_CP.csv");
            FileWriter writerBuyVsCP = new FileWriter(fileBuyVsCP,true);

            if(first){
                addHeader(writer);
                first = false;
            }

            DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add(String.valueOf(agent.getID()));
            arguments.add(String.valueOf(product.getID()));
            if(cp==null)  arguments.add("NONE");
            else arguments.add(cp.getName());
            arguments.add(df.format(today));
            arguments.add(df.format(earliest));
            arguments.add(df.format(latest));
            arguments.add(String.valueOf(beginNumOfDays));
            arguments.add(String.valueOf(endNumOfDays));
            arguments.add(""+isHomeDelivery);
            arguments.add(""+hasBoughtFromRecommendedCP);
            if(recommendedDate==null) arguments.add("NONE");
            else arguments.add(df.format(recommendedDate));
            if(recommendedCP==null) arguments.add("NONE");
            else arguments.add(recommendedCP.getName());

            addLine(writer, arguments);

                arguments = new ArrayList<>();
                arguments.add(""+numOfLogs);
                double ratio = ((double) hasBoughtFromRecommendedCPCounter/ (double)numOfLogs)*100;
                arguments.add((""+ratio).replace(".",","));

                addLine(writerBuyVsCP, arguments);

            writer.flush();
            writer.close();

            writerBuyVsCP.flush();
            writerBuyVsCP.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateVariables(boolean isHomeDelivery, boolean hasBoughtFromRecommendedCP, CollectionPoint recommendedCP, int beginNumOfDays, int endNumOfDays) {
        if(isHomeDelivery) isHomeDeliveryCounter++;
        if(hasBoughtFromRecommendedCP) hasBoughtFromRecommendedCPCounter++;
        if(recommendedCP!=null) recommendedCPCounter++;
        totalBeginNumOfDays += beginNumOfDays;
        totalEndNumOfDays += endNumOfDays;
        numOfLogs++;
        updateGUI();
    }

    private static void addLine(FileWriter writer, ArrayList<String> arguments) throws IOException {
        int n = arguments.size();
        int cnt = 0;
        for(String s: arguments){
            writer.append(s);
            cnt++;
            // be sure that the last char in line is not ','
            if(cnt!=n) writer.append(';');
        }
        writer.append('\n');
    }

    private static void addHeader(FileWriter writer) throws IOException {
        writer.append("AgentID");
        writer.append(';');
        writer.append("ProductID");
        writer.append(';');
        writer.append("CollectionPointName");
        writer.append(';');
        writer.append("date");
        writer.append(';');
        writer.append("earliestDate");
        writer.append(';');
        writer.append("latestDate");
        writer.append(';');
        writer.append("beginNumberOfDaysCanWait");
        writer.append(';');
        writer.append("endNumberOfDaysCanWait");
        writer.append(';');
        writer.append("isHomeDelivery");
        writer.append(';');
        writer.append("isOtherAgentInfluenced");
        writer.append(';');
        writer.append("recommendedDate");
        writer.append(';');
        writer.append("recommendedCP");
        writer.append('\n');
    }

    private static void updateGUI() {
        Platform.runLater(() -> {
            gui.updateStatistics(getHomeDeliveredPercentage(), hasBoughtFromRecommendedCPPercentage(), getRecommendedCPPercentage(),
                    getMeanBeginNumOfDays(), getMeanEndNumOfDays());
        });
    }


    private static double getHomeDeliveredPercentage() {
        return isHomeDeliveryCounter/(double)numOfLogs;
    }

    private static double hasBoughtFromRecommendedCPPercentage() {
        return hasBoughtFromRecommendedCPCounter /(double)numOfLogs;
    }

    private static double getRecommendedCPPercentage() {
        return recommendedCPCounter/(double)numOfLogs;
    }

    private static double getMeanBeginNumOfDays() {
        return totalBeginNumOfDays / (double)numOfLogs;
    }

    private static double getMeanEndNumOfDays() {
        return totalEndNumOfDays / (double)numOfLogs;
    }

    public static void clearStats() {
        first = true;
        numOfLogs = 0;
        hasBoughtFromRecommendedCPCounter = 0;
        isHomeDeliveryCounter = 0;
        recommendedCPCounter = 0;
        totalBeginNumOfDays = 0;
        totalEndNumOfDays = 0;
    }
}
