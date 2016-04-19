package Generators;


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

    int amount = 25;

    public ArrayList<CollectionPoint> generateCollectionPoints() {
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

            collectionPoints.add(cp);
        }

        return collectionPoints;

    }





}
