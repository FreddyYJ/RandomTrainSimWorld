package com.github.freddyyj.randomtrainsimworld2.gui;

import com.github.freddyyj.randomtrainsimworld2.*;
import com.github.freddyyj.randomtrainsimworld2.Main;
import com.github.freddyyj.randomtrainsimworld2.Random;
import com.github.freddyyj.randomtrainsimworld2.config.Config;
import com.github.freddyyj.randomtrainsimworld2.config.SaveLoco;
import com.github.freddyyj.randomtrainsimworld2.exception.FileNotFoundException;
import com.github.freddyyj.randomtrainsimworld2.exception.PermissionDeniedException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Main controller of {@link com.github.freddyyj.randomtrainsimworld2.gui.Main}.
 * Call {@link MainController#setRandom(Random)} to set random object to your custom class.
 */
public class MainController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox boxRoute;
    @FXML
    private ScrollPane boxLoco;
    @FXML
    private VBox boxWeather;
    @FXML
    private TextField textPickedRoute;
    @FXML
    private TextField textPickedLoco;
    @FXML
    private TextField textPickedWeather;
    @FXML
    private MenuItem itemSave;
    @FXML
    private MenuItem itemSaveAs;
    @FXML
    private MenuItem itemLoad;
    @FXML
    private MenuItem itemClose;
    @FXML
    private MenuItem itemAbout;
    private VBox currentRoute; // Box of locos that selected
    private CheckBox currentBox; // Route Checkbox
    private Random random;

    private HashMap<String,VBox> locomotiveVBoxs=new HashMap<>();

    /**
     * default constructor
     */
    public MainController() {
    }

    @FXML
    private void initialize() {
        Main core= null;
        try {
            core = Main.getInstance();
        } catch (IOException e) {
            System.out.println("Error occurred at initializing JavaFX!");
            e.printStackTrace();
            System.exit(1);
        }

        // create UI elements depend on JSON file
        for(int i=0;i<core.getRoutes().size();i++){
            // add route checkbox
            CheckBox route=new CheckBox(core.getRoutes().get(i).getName());
            route.setSelected(true);
            route.setOnAction(this::onCheckRouteSelect);
            route.setOnMouseClicked(this::onCheckRouteClick);
            route.setPadding(new Insets(5.,0.,0.,5.));
            route.setId("check"+core.getRoutes().get(i).getCode());
            boxRoute.getChildren().add(route);

            // add route VBox in loco pane
            VBox locoOfRoute=new VBox();
            locoOfRoute.setDisable(true);
            locoOfRoute.setVisible(false);
            locoOfRoute.prefHeight(200.);
            locoOfRoute.setId("box"+core.getRoutes().get(i).getCode());

            // add locomotives in route VBox
            for (int j=0;j<core.getLocomotive(core.getRoutes().get(i).getName()).size();j++){
                String locoName=core.getLocomotive(core.getRoutes().get(i).getName()).get(j).getName();
                CheckBox locomotive=new CheckBox(locoName);
                locomotive.setOnAction(this::onCheckLocoSelect);
                locomotive.setSelected(true);
                locomotive.setPadding(new Insets(5.,0.,0.,5.));
                locoOfRoute.getChildren().add(locomotive);
            }

            locomotiveVBoxs.put(core.getRoutes().get(i).getName(),locoOfRoute);
        }

        // add weather checkbox
        for (int i=0;i<core.getWeathers().size();i++){
            CheckBox weather=new CheckBox(core.getWeathers().get(i).getName());
            weather.setSelected(true);
            weather.setOnAction(this::onCheckWeatherSelect);
            weather.setPadding(new Insets(5.,0.,0.,5.));
            boxWeather.getChildren().add(weather);
        }

        anchorPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                newScene.setOnKeyPressed(this::onShortcut);
            }
        });

        random=Random.getInstance();
        try {
            if (Main.getInstance().getUnselectedLocos().hasSavefile())
                reload(Main.getInstance().getUnselectedLocos());
        } catch (IOException e) {
            System.out.println("Error occurred at initializing JavaFX!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Set random class.
     * <p>
     *     If you want to set custom random class, call {@link com.github.freddyyj.randomtrainsimworld2.gui.Main}.{@link com.github.freddyyj.randomtrainsimworld2.gui.Main#controller}.setRandom(Random).
     * </p>
     * @param random extended {@link Random} object
     */
    public void setRandom(Random random){
        this.random=random;
    }

    @FXML
    protected void onCheckRouteClick(MouseEvent e) {
        if (e.getSource() instanceof CheckBox) {
            currentBox = (CheckBox) e.getSource();
        }
        if( currentRoute!=null){
            currentRoute.setVisible(false);
            currentRoute.setDisable(true);
        }
        Node routeBox = (Node) e.getSource();
        String id = routeBox.getId();
        currentRoute = getLocoBoxByID(id);
        currentRoute.setVisible(true);
        currentRoute.setDisable(false);
        boxLoco.setContent(currentRoute);

        textPickedRoute.setText(((CheckBox) routeBox).getText());
    }

    @FXML
    protected void onRandomAll(ActionEvent e) {
        List<List<Locomotive>> locoList=new ArrayList<>();
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        for (int i=0;i<core.getRoutes().size();i++){
            locoList.add(core.getLocomotive(core.getRoutes().get(i).getName()));
        }

        Locomotive loco = random.randomLocomotiveInAll(locoList);
        Route route = loco.getRoute();

        Weather weather = random.randomWeather(core.getWeathers());

        textPickedRoute.setText(route.getName());
        textPickedLoco.setText(loco.getName());
        textPickedWeather.setText(weather.getName());

        if (currentRoute!=null){
            currentRoute.setVisible(false);
            currentRoute.setDisable(true);
        }
        String name=getRouteByName(route.getName()).getId();
        currentRoute = getLocoBoxByID(name);
        currentRoute.setVisible(true);
        currentRoute.setDisable(false);
        boxLoco.setContent(currentRoute);

        currentBox=getRouteByName(loco.getRoute().getName());
    }

    @FXML
    protected void onRandomRoute(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        Route selected=random.randomRoute(core.getRoutes());
        CheckBox selectedRoute = getRouteByName(selected.getName());

        if (currentRoute!=null){
            currentRoute.setVisible(false);
            currentRoute.setDisable(true);
        }
        textPickedRoute.setText(selectedRoute.getText());
        currentRoute = getLocoBoxByID(selectedRoute.getId());
        currentRoute.setVisible(true);
        currentRoute.setDisable(false);
        boxLoco.setContent(currentRoute);

        currentBox=selectedRoute;
    }

    @FXML
    protected void onRandomLoco(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        if (currentBox==null) return;
        Locomotive selected=random.randomLocomotive(core.getLocomotive(currentBox.getText()));
        CheckBox loco = getLocoByName(selected.getName(),currentBox.getText());

        textPickedRoute.setText(currentBox.getText());
        textPickedLoco.setText(loco.getText());
    }

    @FXML
    protected void onRandomWeather(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        Weather weather = random.randomWeather(core.getWeathers());
        textPickedWeather.setText(weather.getName());
    }

    @FXML
    protected void onCheckLocoSelect(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        if (e.getSource() instanceof CheckBox) {
            CheckBox selectedLoco = (CheckBox) e.getSource();
            core.selectLocomotive(selectedLoco.isSelected(), core.getLocomotive(currentBox.getText(),selectedLoco.getText()), core.getRoute(currentBox.getText()));
        }
    }

    @FXML
    protected void onCheckRouteSelect(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        if (e.getSource() instanceof CheckBox) {
            CheckBox selectedRoute = (CheckBox) e.getSource();
            core.selectRoute(selectedRoute.isSelected(), core.getRoute(selectedRoute.getText()));
        }

    }

    @FXML
    protected void onCheckWeatherSelect(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        if (e.getSource() instanceof CheckBox) {
            CheckBox selectedWeather = (CheckBox) e.getSource();
            core.selectWeather(selectedWeather.isSelected(), core.getWeather(selectedWeather.getText()));
        }

    }

    @FXML
    protected void onSaveAs(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save File as");
            chooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
            File currentFile = chooser.showSaveDialog(anchorPane.getScene().getWindow());
            if (currentFile != null) {
                core.saveAs(currentFile.getPath());
            }
        }catch (IOException excep){
            handleException(excep);
        }

    }

    @FXML
    protected void onLoad(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();
        }catch (IOException excep){
            handleException(excep);
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Save File");
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        File file = chooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (file != null) {
            try {
                core.reloadSaveFile(file.getPath());
            } catch (IOException excep) {
                handleException(excep);
                return;
            }
            reload(core.getUnselectedLocos());
        }
    }

    @FXML
    protected void onSave(ActionEvent e) {
        Main core=null;
        try{
            core=Main.getInstance();

            if (core.getSaveFilePath() != null)
                core.saveAs(core.getSaveFilePath());
            else {
                onSaveAs(e);
            }
        }catch (IOException excep){
            handleException(excep);
        }
}

    @FXML
    protected void onClose(ActionEvent e) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onHelp(ActionEvent e) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Random Train Sim World 2");
        alert.setHeaderText("Random picker for Train Sim World");
        alert.setContentText("Homepage: https://github.com/FreddyYJ/RandomTrainSimWorld2");

        alert.showAndWait();
    }

    @FXML
    protected void onShortcut(KeyEvent e) {
        if (e.isShortcutDown()) {
            if (e.getCode() == KeyCode.S) {
                onSave(null);
            }
        }
    }

    /**
     * Reload selection of {@link CheckBox}s of routes, locomotives and weathers.
     * @param save a {@link SaveLoco} object
     */
    public void reload(SaveLoco save) {
        // reload routes
        ObservableList<Node> routeList=this.boxRoute.getChildren();
        for (int i = 0; i < routeList.size(); i++) {
            CheckBox route= (CheckBox) routeList.get(i);
            if (save.getRoute().contains(route.getText())) {
                route.setSelected(false);
            } else {
                route.setSelected(true);
            }
        }

        // reload locomotives
//        ObservableList<Node> locoBoxList=this.boxLoco.getChildren();
        for (Map.Entry<String,VBox> boxByRoute: locomotiveVBoxs.entrySet()) {
            VBox box= boxByRoute.getValue();
            CheckBox route=null;
            for (int i=0;i<routeList.size();i++){
                if (((CheckBox) routeList.get(i)).getText().equals(boxByRoute.getKey())) {
                    route = (CheckBox) routeList.get(i);
                    break;
                }
            }
            if (route==null){
                throw new NullPointerException("MainController.reload(): No route found: "+boxByRoute.getKey());
            }

            ObservableList<Node> locoBox=box.getChildren();
            for (int j = 0; j < locoBox.size(); j++) {
                CheckBox loco= (CheckBox) locoBox.get(j);
                try {
                    loco.setSelected(!save.getLocomotive(Main.getInstance().getRoute(route.getText())).contains(loco.getText()));
                } catch (IOException e) {
                    handleException(e);
                }

            }
        }

        // reload weathers
        ObservableList<Node> weatherList=this.boxWeather.getChildren();
        for (int i = 0; i < weatherList.size(); i++) {
            CheckBox weather= (CheckBox) weatherList.get(i);
            if (save.getWeather().contains(weather.getText())) {
                weather.setSelected(false);
            } else {
                weather.setSelected(true);
            }
        }

    }

    /**
     * Get {@link VBox} that has locomotive {@link CheckBox}s.
     * @param routeId route {@link CheckBox} ID
     * @return {@link VBox} that has locomotive {@link CheckBox}s
     */
    protected VBox getLocoBoxByID(String routeId) {
        Collection<VBox> loco = locomotiveVBoxs.values();

        String locoId;
        String[] id = routeId.split("check");
        locoId = id[1];
        locoId = "box" + locoId;

        for (VBox locomotive: loco) {
            if (locomotive.getId().equals(locoId))
                return locomotive;
        }
        return null;
    }

    /**
     * Get route {@link CheckBox} with {@link VBox}.
     * @param locoId {@link VBox} ID
     * @return route {@link CheckBox}
     */
    protected CheckBox getRouteByVBox(VBox locoId) {
        String id;
        id = (locoId.getId().split("box"))[1];
        id = "check" + id;

        ObservableList<Node> routeList=this.boxRoute.getChildren();
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).getId().equals(id))
                return (CheckBox) routeList.get(i);
        }
        return null;
    }

    /**
     * Get route {@link CheckBox} with route name.
     * @param name route name
     * @return route {@link CheckBox}
     */
    protected CheckBox getRouteByName(String name){
        ObservableList<Node> routeList=this.boxRoute.getChildren();
        for (int i=0;i<routeList.size();i++){
            if (((CheckBox)routeList.get(i)).getText().equals(name)) return (CheckBox)routeList.get(i);
        }
        return null;
    }

    /**
     * Get locomotive {@link CheckBox} with locomotive name and route name.
     * @param name locomotive name
     * @param route route name
     * @return locomotive {@link CheckBox}
     */
    protected CheckBox getLocoByName(String name, String route) {
        int routeIndex=0;

        ObservableList<Node> routeList=this.boxRoute.getChildren();
        for (int i=0;i<routeList.size();i++){
            if (((CheckBox)routeList.get(i)).getText().equals(route))
                routeIndex=i;
        }
        ObservableList<Node> locoList = getLocoBoxByID(routeList.get(routeIndex).getId()).getChildren();
        for (int i = 0; i < locoList.size(); i++) {
            if (((CheckBox) locoList.get(i)).getText().equals(name))
                return (CheckBox) locoList.get(i);
        }
        return null;
    }

    /**
     * Get weather {@link CheckBox} with weather name.
     * @param name weather name
     * @return weather {@link CheckBox}
     */
    protected CheckBox getWeatherByName(String name) {
        ObservableList<Node> weatherList=this.boxWeather.getChildren();
        for (int i = 0; i < weatherList.size(); i++) {
            if (((CheckBox) weatherList.get(i)).getText().equals(name))
                return (CheckBox) weatherList.get(i);
        }
        return null;
    }
    private void handleException(Exception e){
        Alert errorAlert=new Alert(AlertType.ERROR);
        if (e instanceof FileNotFoundException){
            FileNotFoundException exception= (FileNotFoundException) e;
            if (exception.getErrorFile().equals(Config.FILE_NAME)){
                errorAlert.setTitle("File Not Fonud!");
                errorAlert.setHeaderText("Config file not found!");
                errorAlert.setContentText("Did you delete config file manually?");
            }
            else{
                errorAlert.setTitle("File Not Found!");
                errorAlert.setHeaderText("Savefile not found!");
                errorAlert.setContentText("Did you delete "+exception.getErrorFile()+" manually?");
            }
        }
        else if (e instanceof PermissionDeniedException){
            PermissionDeniedException exception= (PermissionDeniedException) e;
            if (exception.getErrorFile().equals(Config.FILE_NAME)){
                errorAlert.setTitle("Permission Denied!");
                errorAlert.setHeaderText("Cannot access config file!");
                errorAlert.setContentText("Check permission of home or document directory.");
            }
            else{
                errorAlert.setTitle("Permission Denied!");
                errorAlert.setHeaderText("Cannot access savefile!");
                errorAlert.setContentText("Check permission of "+exception.getErrorFile()+", or run in root.");
            }
        }
        else{
            errorAlert.setTitle("Unknown error!");
            errorAlert.setHeaderText("Unknown exception thrown!");
            errorAlert.setContentText("error Message:\n"+e.getMessage());
        }
        errorAlert.show();
    }
}
