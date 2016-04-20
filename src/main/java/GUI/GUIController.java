package GUI;

import Agents.Agent;
import Agents.CollectionPoint;
import Agents.PurchaseThreadPool;
import Generators.AgentGenerator;
import Generators.CollectionPointGenerator;
import Generators.ProductGenerator;
import Shop.ProductProfile;
import Tools.RNG;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class GUIController {

    @FXML
    public Tab agentsTab;
    @FXML
    public Tab productsTab;

    @FXML
    public Button startConfigButton;

    @FXML
    public void initialize() {
        startConfigButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // do iets;

                AgentGenerator ag = new AgentGenerator();
                for (Agent a: ag.generateAgents()) {
                    System.out.println(a.toString());
                }

                ProductGenerator pg = new ProductGenerator();
                for (ProductProfile p : pg.generateProducts()) {
                    System.out.println(p.toString());
                }

                CollectionPointGenerator cpg = new CollectionPointGenerator();
                for (CollectionPoint cp: cpg.generateCollectionPoints(25)) { // 25 aanpassen door shizzle in GUI
                    System.out.println(cp.toString());
                }

                PurchaseThreadPool pool = new PurchaseThreadPool(ag.generateAgents(), pg.generateProducts());
                pool.run();

            }
        });

    }

}
