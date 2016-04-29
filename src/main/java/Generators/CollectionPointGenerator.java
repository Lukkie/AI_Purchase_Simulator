package Generators;


import Agents.Agent;
import Agents.CollectionPoint;
import Agents.GeoLocation;
import Tools.RNG;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class CollectionPointGenerator {


    public ArrayList<CollectionPoint> generateCollectionPoints(int amount, ArrayList<Agent> agents) {
        ArrayList<CollectionPoint> collectionPoints = new ArrayList<>();
        RNG rng = RNG.getInstance();


        for (int i = 0; i < amount; i++) {
            CollectionPoint cp = new CollectionPoint();
            GeoLocation loc = rng.getRandomLocation();
            cp.setLocation(loc);
            int lineNumber = rng.nextInt(123000);

            try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/names.txt"))) {
                    String line = lines.skip(lineNumber).findFirst().get();
                    cp.setName(line);
            } catch (IOException e) {
                e.printStackTrace();
            }


            for (Agent agent : agents) {
                if (agent.getLocation().distance(loc) <= 30) {
                    cp.addNearbyAgent(agent);
                    System.out.println("Agent "+agent.getID()+" is close to collection point "+cp.getName());
                }
            }

            collectionPoints.add(cp);
        }

        return collectionPoints;

    }





}
