package GUI;

import Agents.Agent;
import Agents.CollectionPoint;
import Agents.PurchaseThreadPool;
import Generators.AgentGenerator;
import Generators.CollectionPointGenerator;
import Generators.ProductGenerator;
import Shop.ProductProfile;
import Tools.RNG;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class GUIController {

    @FXML
    public Tab agentsTab;
    public Slider agentSelfPerceptionSlider;
    public Slider agentPercentageAtHomeSlider;
    public Slider agentPriceQualityPerceptionSlider;
    public Slider agentGreenPurchaseIntentionSlider;
    public Slider agentNeedRecognitionSlider;
    public Slider agentWTBSlider;
    public Slider agentLocationFlexibilitySlider;
    public Slider agentSusceptibilityCPSlider;
    public Slider agentCPRecommendationSlider;
    public TextField agentSelfPerceptionTextField;
    public TextField agentPriceQualityPerceptionTextField;
    public TextField agentGreenPurchaseIntentionTextField;
    public TextField agentNeedRecognitionTextField;
    public TextField agentWTBTextField;
    public TextField agentLocationFlexibilityTextField;
    public TextField agentSusceptibilityCPTextField;
    public TextField agentCPRecommendationTextField;
    public TextField agentPercentageAtHomeTextField;

    @FXML
    public Tab productsTab;

    @FXML
    public Button startConfigButton;


    @FXML
    public void initialize() {
        startConfigButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // do iets;




                /*AgentGenerator ag = new AgentGenerator();
                for (Agent a: ag.generateAgents()) {
                    System.out.println(a.toString());
                }

                ProductGenerator pg = new ProductGenerator();
                for (ProductProfile p : pg.generateProducts()) {
                    System.out.println(p.toString());
                }

                CollectionPointGenerator cpg = new CollectionPointGenerator();
                ArrayList<CollectionPoint> cps = cpg.generateCollectionPoints(25);
                for (CollectionPoint cp: cps) { // 25 aanpassen door shizzle in GUI
                    System.out.println(cp.toString());
                }
                CollectionPoint.pushList(cps);





                PurchaseThreadPool pool = new PurchaseThreadPool(ag.generateAgents(), pg.generateProducts());
                pool.run();*/

            }
        });

        initializeSliderAndTextField(agentSelfPerceptionSlider, agentSelfPerceptionTextField, 0.5);
        initializeSliderAndTextField(agentWTBSlider, agentWTBTextField, 0.5);
        initializeSliderAndTextField(agentCPRecommendationSlider, agentCPRecommendationTextField, 0.5);
        initializeSliderAndTextField(agentLocationFlexibilitySlider, agentLocationFlexibilityTextField, 0.5);
        initializeSliderAndTextField(agentNeedRecognitionSlider, agentNeedRecognitionTextField, 0.5);
        initializeSliderAndTextField(agentSusceptibilityCPSlider, agentSusceptibilityCPTextField, 0.5);
        initializeSliderAndTextField(agentGreenPurchaseIntentionSlider, agentGreenPurchaseIntentionTextField, 0.5);
        initializeSliderAndTextField(agentPriceQualityPerceptionSlider, agentPriceQualityPerceptionTextField, 0.5);
        initializeSliderAndTextField(agentPercentageAtHomeSlider, agentPercentageAtHomeTextField, 0.1);
    }


    private void initializeSliderAndTextField(Slider slider, TextField textField, double defaultValue) {
        slider.setMin(0);
        slider.setMax(100);
        slider.setBlockIncrement(1);
        slider.valueProperty().setValue(defaultValue * 100);
        textField.setText(Integer.toString((int)(defaultValue * 100)));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf((int)(slider.getValue())));
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                double value;
                if (newValue.length() == 0) {
                    value = 0;
                    textField.setText("0");
                }

                else value = Integer.parseInt(newValue);
                if (value >= 0 && value <= 100) {
                    slider.valueProperty().setValue(value);
                }
                else textField.setText(oldValue);
            } else {
                textField.setText(oldValue);
            }
        });

    }



}
