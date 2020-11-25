package com.github.freddyyj.randomtrainsimworld2.gui;

import com.github.freddyyj.randomtrainsimworld2.*;
import com.github.freddyyj.randomtrainsimworld2.Main;
import com.github.freddyyj.randomtrainsimworld2.config.SaveLoco;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.List;

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
    private Pane boxLoco;
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

    /**
     * default constructor
     */
    public MainController() {
    }

    @FXML
    private void initialize() {
        Main core=Main.getInstance();

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

            boxLoco.getChildren().add(locoOfRoute);
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
        reload(Main.getInstance().getUnselectedLocos());
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

        textPickedRoute.setText(((CheckBox) routeBox).getText());
    }

    @FXML
    protected void onRandomAll(ActionEvent e) {
        ArrayList<ArrayList<Locomotive>> locoList=new ArrayList<>();
        for (int i=0;i<Main.getInstance().getRoutes().size();i++){
            locoList.add(Main.getInstance().getLocomotive(Main.getInstance().getRoutes().get(i).getName()));
        }

        Locomotive loco = random.randomLocomotiveInAll(locoList);
        Route route = loco.getRoute();

        Weather weather = random.randomWeather(Main.getInstance().getWeathers());

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

        currentBox=getRouteByName(loco.getRoute().getName());
    }

    @FXML
    protected void onRandomRoute(ActionEvent e) {
        Route selected=random.randomRoute(Main.getInstance().getRoutes());
        CheckBox selectedRoute = getRouteByName(selected.getName());

        if (currentRoute!=null){
            currentRoute.setVisible(false);
            currentRoute.setDisable(true);
        }
        textPickedRoute.setText(selectedRoute.getText());
        currentRoute = getLocoBoxByID(selectedRoute.getId());
        currentRoute.setVisible(true);
        currentRoute.setDisable(false);

        currentBox=selectedRoute;
    }

    @FXML
    protected void onRandomLoco(ActionEvent e) {
        if (currentBox==null) return;
        Locomotive selected=random.randomLocomotive(Main.getInstance().getLocomotive(currentBox.getText()));
        CheckBox loco = getLocoByName(selected.getName(),currentBox.getText());

        textPickedRoute.setText(currentBox.getText());
        textPickedLoco.setText(loco.getText());
    }

    @FXML
    protected void onRandomWeather(ActionEvent e) {
        Weather weather = random.randomWeather(Main.getInstance().getWeathers());
        textPickedWeather.setText(weather.getName());
    }

    @FXML
    protected void onCheckLocoSelect(ActionEvent e) {
        if (e.getSource() instanceof CheckBox) {
            CheckBox selectedLoco = (CheckBox) e.getSource();
            Main.getInstance().selectLocomotive(selectedLoco.isSelected(), Main.getInstance().getLocomotive(currentBox.getText(),selectedLoco.getText()), Main.getInstance().getRoute(currentBox.getText()));
        }
    }

    @FXML
    protected void onCheckRouteSelect(ActionEvent e) {
        if (e.getSource() instanceof CheckBox) {
            CheckBox selectedRoute = (CheckBox) e.getSource();
            Main.getInstance().selectRoute(selectedRoute.isSelected(), Main.getInstance().getRoute(selectedRoute.getText()));
        }

    }

    @FXML
    protected void onCheckWeatherSelect(ActionEvent e) {
        if (e.getSource() instanceof CheckBox) {
            CheckBox selectedWeather = (CheckBox) e.getSource();
            Main.getInstance().selectWeather(selectedWeather.isSelected(), Main.getInstance().getWeather(selectedWeather.getText()));
        }

    }

    @FXML
    protected void onSaveAs(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save File as");
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        File currentFile = chooser.showSaveDialog(anchorPane.getScene().getWindow());
        if (currentFile != null) {
            Main.getInstance().saveAs(currentFile.getPath());
        }
    }

    @FXML
    protected void onLoad(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Save File");
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        File file = chooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (file != null) {
            Main.getInstance().reloadSaveFile(file.getPath());
            reload(Main.getInstance().getUnselectedLocos());
        }
    }

    @FXML
    protected void onSave(ActionEvent e) {
        if (Main.getInstance().getSaveFilePath() != null)
            Main.getInstance().saveAs(Main.getInstance().getSaveFilePath());
        else {
            onSaveAs(e);
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
        alert.setTitle("Random Train Sim World");
        alert.setHeaderText("Random picker for Train Sim World");
        alert.setContentText("Homepage: https://github.com/FreddyYJ/RandomTrainSimWorld");

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
        ObservableList<Node> locoBoxList=this.boxLoco.getChildren();
        for (int i = 0; i < locoBoxList.size(); i++) {
            VBox box= (VBox) locoBoxList.get(i);
            CheckBox route= (CheckBox) routeList.get(i);
            ObservableList<Node> locoBox=box.getChildren();
            for (int j = 0; j < locoBox.size(); j++) {
                CheckBox loco= (CheckBox) locoBox.get(j);
                if (save.getLocomotive(route.getText()).contains(loco.getText())) {
                    loco.setSelected(false);
                } else {
                    loco.setSelected(true);
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
        List<Node> loco = boxLoco.getChildren();

        String locoId;
        String[] id = routeId.split("check");
        locoId = id[1];
        locoId = "box" + locoId;

        for (int i = 0; i < loco.size(); i++) {
            if (loco.get(i).getId().equals(locoId))
                return (VBox) loco.get(i);
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
}
