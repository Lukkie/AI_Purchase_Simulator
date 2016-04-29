package GUI;

import Agents.Agent;
import Agents.CollectionPoint;
import Agents.PurchaseThreadPool;
import Generators.AgentGenerator;
import Generators.CollectionPointGenerator;
import Generators.ProductGenerator;
import Shop.ProductProfile;
import Shop.ShopProfile;
import Tools.RNG;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public Slider agentShopReputationSlider;
    public TextField agentSelfPerceptionTextField;
    public TextField agentPriceQualityPerceptionTextField;
    public TextField agentGreenPurchaseIntentionTextField;
    public TextField agentNeedRecognitionTextField;
    public TextField agentWTBTextField;
    public TextField agentLocationFlexibilityTextField;
    public TextField agentSusceptibilityCPTextField;
    public TextField agentCPRecommendationTextField;
    public TextField agentPercentageAtHomeTextField;
    public TextField agentShopReputationTextField;
    public TextField agentNumberTextField;


    @FXML
    public Tab productsTab;
    public Slider productGPVSlider;
    public Slider productNeedRecognitionSlider;
    public Slider productMeanPriceSlider;
    public Slider productStddevSlider;
    public Slider productAvailabilitySlider;
    public TextField productStddevTextField;
    public TextField productAvailabilityTextField;
    public TextField productGPVTextField;
    public TextField productNeedRecognitionTextField;
    public TextField productMeanPriceTextField;
    public TextField productNumberTextField;

    @FXML
    public Tab shopTab;
    public Slider shopGPRSlider;
    public Slider shopGPTSlider;
    public Slider shopGBISlider;
    public Slider shopEASlider;
    public Slider shopServiceSlider;
    public TextField shopGPRTextField;
    public TextField shopGBITextField;
    public TextField shopEATextField;
    public TextField shopGPTTextField;
    public TextField shopServiceTextField;

    @FXML
    public Tab collectionPointTab;
    public TextField CPTextField;


    @FXML
    public Tab simulationTab;
    public Button startConfigButton;


    @FXML
    public void initialize() {
        startConfigButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // do iets als op de knop geklikt wordt;


                // shop
                ShopProfile.setEnvironmentalAdvertisement(getPercentage(shopEATextField));
                ShopProfile.setGreenBrandImage(getPercentage(shopGBITextField));
                ShopProfile.setGreenPerceivedRisk(getPercentage(shopGPRTextField));
                ShopProfile.setGreenPerceivedTrust(getPercentage(shopGPTTextField));


                // agents
                AgentGenerator ag = new AgentGenerator();
                ag.setAgentAmount(Integer.parseInt(agentNumberTextField.getText()));
                ag.setSelfPerceptionFactor(getPercentage(agentSelfPerceptionTextField));
                ag.setPriceQualityPerceptionFactor(getPercentage(agentPriceQualityPerceptionTextField));
                ag.setGreenPurchaseIntentionFactor(getPercentage(agentGreenPurchaseIntentionTextField));
                ag.setNeedRecognitionFactor(getPercentage(agentNeedRecognitionTextField));
                ag.setWillingnessToBuyFactor(getPercentage(agentWTBTextField));
                ag.setLocationFlexibilityFactor(getPercentage(agentLocationFlexibilityTextField));
                ag.setSusceptibilityCollectionPointFactor(getPercentage(agentSusceptibilityCPTextField));
                ag.setCollectionPointRecommendationFactorFactor(getPercentage(agentCPRecommendationTextField));
                ag.setAtHomeFactor(getPercentage(agentPercentageAtHomeTextField));
                ag.setShopReputationFactor(getPercentage(agentShopReputationTextField));
                ArrayList<Agent> agents = ag.generateAgents();
                for (Agent a: agents) {
                    System.out.println(a.toString());
                }


                // products
                ProductGenerator pg = new ProductGenerator();
                pg.setAmount(Integer.parseInt(productNumberTextField.getText()));
                pg.setNeedRecognitionFactor(getPercentage(productNeedRecognitionTextField));
                pg.setGreenPerceivedValueFactor(getPercentage(productGPVTextField));
                pg.setPrice(Double.parseDouble(productMeanPriceTextField.getText()));
                pg.setPriceStdDev(Double.parseDouble(productStddevTextField.getText()));
                ArrayList<ProductProfile> products = pg.generateProducts();
                for (ProductProfile p : products) {
                    System.out.println(p.toString());
                }

                CollectionPointGenerator cpg = new CollectionPointGenerator();
                ArrayList<CollectionPoint> cps = cpg.generateCollectionPoints(Integer.parseInt(CPTextField.getText()), agents);
                for (CollectionPoint cp: cps) {
                    System.out.println(cp.toString());
                }
                CollectionPoint.pushList(cps);





                PurchaseThreadPool pool = new PurchaseThreadPool(ag.generateAgents(), pg.generateProducts(), cps);
                pool.run();

            }
        });


        // Agents
        initializeSliderAndTextField(agentSelfPerceptionSlider, agentSelfPerceptionTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentWTBSlider, agentWTBTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentCPRecommendationSlider, agentCPRecommendationTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentLocationFlexibilitySlider, agentLocationFlexibilityTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentNeedRecognitionSlider, agentNeedRecognitionTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentSusceptibilityCPSlider, agentSusceptibilityCPTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentGreenPurchaseIntentionSlider, agentGreenPurchaseIntentionTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentPriceQualityPerceptionSlider, agentPriceQualityPerceptionTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(agentPercentageAtHomeSlider, agentPercentageAtHomeTextField, 10, 0, 100, 1);
        initializeSliderAndTextField(agentShopReputationSlider, agentShopReputationTextField, 50, 0, 100, 1);
        setNotZeroTextField(agentNumberTextField, 100);

        // Products
        initializeSliderAndTextField(productAvailabilitySlider, productAvailabilityTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(productGPVSlider, productGPVTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(productStddevSlider, productStddevTextField, 50, 0, 200, 1);
        initializeSliderAndTextField(productMeanPriceSlider, productMeanPriceTextField, 200, 1, 500, 1);
        initializeSliderAndTextField(productNeedRecognitionSlider, productNeedRecognitionTextField, 50, 0, 100, 1);
        setNotZeroTextField(productNumberTextField, 10);

        // Shop
        initializeSliderAndTextField(shopEASlider, shopEATextField, 50, 0, 100, 1);
        initializeSliderAndTextField(shopGBISlider, shopGBITextField, 50, 0, 100, 1);
        initializeSliderAndTextField(shopGPRSlider, shopGPRTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(shopGPTSlider, shopGPTTextField, 50, 0, 100, 1);
        initializeSliderAndTextField(shopServiceSlider, shopServiceTextField, 50, 0, 100, 1);

        // Collection points
        setNotZeroTextField(CPTextField, 25);
    }

    private void setNotZeroTextField(TextField textField, int defaultValue) {
        textField.textProperty().setValue(Integer.toString(defaultValue));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(oldValue);
            }
        });
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!textField.getText().matches("\\d*")) {
                    textField.setText(Integer.toString(defaultValue));
                }
                else if (Integer.parseInt(textField.getText()) == 0) {
                    textField.setText("1");
                }
            }
        });
    }

    private double getPercentage(TextField tf) {
        return 0.01d * Double.parseDouble(tf.getText());
    }


    private void initializeSliderAndTextField(Slider slider, TextField textField, int defaultValue, int min, int max, int stepSize) {
        slider.setMin(min);
        slider.setMax(max);
        slider.setBlockIncrement(stepSize);
        slider.valueProperty().setValue(defaultValue);
        textField.setText(Integer.toString(defaultValue));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(String.valueOf((int)(slider.getValue())));
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {

                double value;
                if (newValue.length() == 0) {
                    value = min;
                    textField.setText("0");
                }

                else value = Integer.parseInt(newValue);
                if (value >= min && value <= max) {
                    slider.valueProperty().setValue(value);
                }
                else textField.setText(oldValue);

            } else {
                textField.setText(oldValue);
            }
        });

    }



}
