package Tools;

import Agents.Agent;
import Agents.CollectionPoint;
import Shop.ProductProfile;

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
    private static final String FILE_NAME = "deliveries.csv";
    private static boolean first = true;

    public static void writeDelivery(Agent agent, ProductProfile product, CollectionPoint cp, Date today, Date earliest, Date latest, int beginNumOfDays, int endNumOfDays,
                                     boolean isHomeDelivery, boolean isInfluenced, Date recommendedDate, CollectionPoint recommendedCP) {
        try {
            File file = new File(FILE_NAME);

            FileWriter writer = new FileWriter(file, true);

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
            arguments.add(""+isInfluenced);
            if(recommendedDate==null) arguments.add("NONE");
            else arguments.add(df.format(recommendedDate));
            if(recommendedCP==null) arguments.add("NONE");
            else arguments.add(recommendedCP.getName());

            addLine(writer, arguments);


            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
