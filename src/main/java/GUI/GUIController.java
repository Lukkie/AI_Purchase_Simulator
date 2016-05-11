package GUI;

import Agents.Agent;
import Agents.CollectionPoint;
import Agents.PurchaseThreadPool;
import Generators.AgentGenerator;
import Generators.CollectionPointGenerator;
import Generators.ProductGenerator;
import Shop.ProductProfile;
import Shop.ShopProfile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lukas on 19-Apr-16.
 */
public class GUIController {

    private PurchaseThreadPool pool = null;

    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<Integer> values = new ArrayList<>();
    private HashMap<TextField, Slider> tuples = new HashMap<>();

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
    public Button saveConfigButton;
    public Button loadConfigButton;


    @FXML
    public void initialize() {
        setupStartButton();



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

        // Config buttons
        initializeConfigButtons();
    }

    private void setupStartButton() {
        startConfigButton.setText("Start simulation");
        startConfigButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // do iets als op de knop geklikt wordt;


                // shop
                ShopProfile.setEnvironmentalAdvertisement(getPercentage(shopEATextField));
                ShopProfile.setGreenBrandImage(getPercentage(shopGBITextField));
                ShopProfile.setGreenPerceivedRisk(getPercentage(shopGPRTextField));
                ShopProfile.setGreenPerceivedTrust(getPercentage(shopGPTTextField));
                ShopProfile.setService(getPercentage(shopServiceTextField));


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
                pg.setAvailability(getPercentage(productAvailabilityTextField));
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



                setupStopButton();

                pool = new PurchaseThreadPool(agents, products, cps);

                new Thread(pool).start();

            }
        });
    }

    private void setupStopButton() {
        //startConfigButton.textProperty().setValue("Stop simulation");
        startConfigButton.setText("Stop simulation");
        startConfigButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                pool.stop = true;
                setupStartButton();
            }
        });
    }

    private void initializeConfigButtons() {
        saveConfigButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save config");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Data files (*.data)", "*.data");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showSaveDialog(saveConfigButton.getScene().getWindow());
                    if (file != null) {
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                        out.writeObject(values);
                        out.writeObject(CPTextField.getText());
                        out.writeObject(agentNumberTextField.getText());
                        out.writeObject(productNumberTextField.getText());
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        loadConfigButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Load config");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Data files (*.data)", "*.data");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showOpenDialog(loadConfigButton.getScene().getWindow());
                    if (file != null) {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                        ArrayList<Integer> loadedValues = (ArrayList<Integer>)in.readObject();
                        for (int i = 0; i < textFields.size(); i++) {
                            changeValue(i, textFields.get(i), loadedValues.get(i));
                        }
                        values = loadedValues;

                        CPTextField.setText((String)in.readObject());
                        agentNumberTextField.setText((String)in.readObject());
                        productNumberTextField.setText((String)in.readObject());

                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeValue(int index, TextField tf, Integer newValue) {
        tf.setText(Integer.toString(newValue));
        tuples.get(tf).valueProperty().setValue(newValue);
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
            values.set(textFields.indexOf(textField), (int) slider.getValue());
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {

                double value;
                if (newValue.length() == 0) {
                    value = min;
                    textField.setText("0");
                    values.set(textFields.indexOf(textField), 0);
                }

                else value = Integer.parseInt(newValue);
                if (value >= min && value <= max) {
                    slider.valueProperty().setValue(value);
                    values.set(textFields.indexOf(textField), (int)value);

                }
                else textField.setText(oldValue);

            } else {
                textField.setText(oldValue);
            }
        });

        textFields.add(textField);
        values.add(defaultValue);
        tuples.put(textField, slider);
    }



}
