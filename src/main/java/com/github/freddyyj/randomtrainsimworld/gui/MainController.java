package com.github.freddyyj.randomtrainsimworld.gui;

import com.github.freddyyj.randomtrainsimworld.*;
import com.github.freddyyj.randomtrainsimworld.Main;
import com.github.freddyyj.randomtrainsimworld.Config;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Main controller of {@link com.github.freddyyj.randomtrainsimworld.gui.Main}.
 */
public class MainController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private SplitPane splitMain;

    // Menu
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

    // Route pane
    @FXML
    private SplitPane splitRoute;
    @FXML
    private VBox boxRoute;
    @FXML
    private CheckBox dummyRouteCheckBox; // Dummy, remove later
    @FXML
    private TextArea textRouteDescription;
    @FXML
    private TextField textPickedRoute;
    @FXML
    private Button buttonRandomRoute;

    // Locomotive pane
    @FXML
    private SplitPane splitLoco;
    @FXML
    private ScrollPane boxLoco;
    @FXML
    private TextArea textLocoDescription;
    @FXML
    private TextField textPickedLoco;
    @FXML
    private Button buttonRandomLoco;

    // Livery pane
    @FXML
    private ScrollPane boxLivery;
    @FXML
    private TextField textPickedLivery;
    @FXML
    private Button buttonRandomLivery;

    // Journey pane
    @FXML
    private ScrollPane boxJourney;
    @FXML
    private TextField textPickedJourney;
    @FXML
    private Button buttonRandomJourney;

    // Scenario pane
    @FXML
    private SplitPane splitScenario;
    @FXML
    private ScrollPane boxScenario;
    @FXML
    private TextArea textScenarioDescription;
    @FXML
    private TextField textPickedScenario;
    @FXML
    private Button buttonRandomScenario;

    @FXML
    private Button buttonRandomAll;
    private VBox currentRoute; // Box of locos of the picked route
    private CheckBox currentBox; // Route Checkbox
    private Random random;

    // Currently picked items (by click or random), null if not picked yet
    private Route pickedRoute;
    private Locomotive pickedLoco;
    private String pickedLivery;
    private String pickedJourney;
    private Scenario pickedScenario;

    // Checkbox boxes built once and reused, so the ticks are kept when shown again
    private final Map<Route, VBox> locoBoxes = new HashMap<>();
    private final Map<Route, VBox> journeyBoxes = new HashMap<>();
    private final Map<Route, VBox> scenarioBoxes = new HashMap<>();
    private final Map<Route, Map<Locomotive, VBox>> liveryBoxes = new HashMap<>();

    /**
     * default constructor
     */
    public MainController() {
    }

    @FXML
    private void initialize() {
        final Main core = Main.getInstance();

        // Fill the route pane only, other panes are filled when a route is picked
        boxRoute.getChildren().clear(); // Removes the dummy route
        for (final String routeName : core.routes.keySet()) {
            final CheckBox routeCheckBox = new CheckBox(routeName); // Route is found by this name later
            routeCheckBox.setSelected(core.includedRoutes.contains(core.routes.get(routeName))); // Ticks from the config
            routeCheckBox.setPadding(new Insets(5., 0., 0., 5.));
            routeCheckBox.setOnAction(this::onCheckRouteInclude);
            routeCheckBox.setOnMouseClicked(this::onCheckRouteClick);
            boxRoute.getChildren().add(routeCheckBox);
        }

        random = new Random();
    }

    /**
     * Update Locomotive pane with the given {@link Route}.
     * @param route a route object
     */
    private void updateLocoPane(final Route route) {
        VBox locoBox = locoBoxes.get(route);
        if (locoBox == null) {
            // First time for this route, build the checkboxes
            locoBox = new VBox();
            for (final Locomotive locomotive : route.locomotives.keySet()) {
                final CheckBox locoCheckBox = new CheckBox(locomotive.name);
                locoCheckBox.setSelected(Main.getInstance().includedLocomotives.get(route).contains(locomotive));
                locoCheckBox.setUserData(locomotive); // Keep the locomotive to find it from the checkbox
                locoCheckBox.setPadding(new Insets(5., 0., 0., 5.));
                locoCheckBox.setOnAction(this::onCheckLocoInclude);
                locoCheckBox.setOnMouseClicked(this::onCheckLocoClick);
                locoBox.getChildren().add(locoCheckBox);
            }
            locoBoxes.put(route, locoBox);
        }
        boxLoco.setContent(locoBox);
        textLocoDescription.clear();
        textPickedLoco.clear();
        pickedLoco = null;

        // Liveries depend on the locomotive, so reset them until one is picked
        boxLivery.setContent(null);
        textPickedLivery.clear();
        pickedLivery = null;
    }

    /**
     * Update Journey pane with the given {@link Route}.
     * @param route a route object
     */
    private void updateJourneyPane(final Route route) {
        VBox journeyBox = journeyBoxes.get(route);
        if (journeyBox == null) {
            // First time for this route, build the checkboxes
            journeyBox = new VBox();
            for (final String journey : route.journeys) {
                final CheckBox journeyCheckBox = new CheckBox(journey);
                journeyCheckBox.setSelected(Main.getInstance().includedJourneys.get(route).contains(journey));
                journeyCheckBox.setPadding(new Insets(5., 0., 0., 5.));
                journeyCheckBox.setOnAction(this::onCheckJourneyInclude);
                journeyBox.getChildren().add(journeyCheckBox);
            }
            journeyBoxes.put(route, journeyBox);
        }
        boxJourney.setContent(journeyBox);
        textPickedJourney.clear();
        pickedJourney = null;
    }

    /**
     * Update Scenario pane with the given {@link Route}.
     * @param route a route object
     */
    private void updateScenarioPane(final Route route) {
        VBox scenarioBox = scenarioBoxes.get(route);
        if (scenarioBox == null) {
            // First time for this route, build the checkboxes
            scenarioBox = new VBox();
            for (final Scenario scenario : route.scenarios) {
                final CheckBox scenarioCheckBox = new CheckBox(scenario.name);
                scenarioCheckBox.setSelected(Main.getInstance().includedScenarios.get(route).contains(scenario));
                scenarioCheckBox.setUserData(scenario); // Keep the scenario to find it from the checkbox
                scenarioCheckBox.setPadding(new Insets(5., 0., 0., 5.));
                scenarioCheckBox.setOnAction(this::onCheckScenarioInclude);
                scenarioBox.getChildren().add(scenarioCheckBox);
            }
            scenarioBoxes.put(route, scenarioBox);
        }
        boxScenario.setContent(scenarioBox);
        textScenarioDescription.clear();
        textPickedScenario.clear();
        pickedScenario = null;
    }

    private void updateLiveryPane(final Route route, final Locomotive locomotive) {
        // Same locomotive can be on several routes with different liveries, so cache per route
        final Map<Locomotive, VBox> routeLiveryBoxes = liveryBoxes.computeIfAbsent(route, r -> new HashMap<>());
        VBox liveryBox = routeLiveryBoxes.get(locomotive);
        if (liveryBox == null) {
            // First time for this locomotive on this route, build the checkboxes
            liveryBox = new VBox();
            // Liveries available on this route, null if the locomotive is not on the route
            final List<String> liveries = route.locomotives.get(locomotive);
            if (liveries != null) {
                for (final String livery : liveries) {
                    final CheckBox liveryCheckBox = new CheckBox(livery);
                    liveryCheckBox.setSelected(Main.getInstance().includedLiveries.get(route).get(locomotive).contains(livery));
                    liveryCheckBox.setUserData(locomotive); // Keep the locomotive this livery belongs to
                    liveryCheckBox.setPadding(new Insets(5., 0., 0., 5.));
                    liveryCheckBox.setOnAction(this::onCheckLiveryInclude);
                    liveryBox.getChildren().add(liveryCheckBox);
                }
            }
            routeLiveryBoxes.put(locomotive, liveryBox);
        }
        boxLivery.setContent(liveryBox);
        textPickedLivery.clear();
        pickedLivery = null;
    }

    /**
     * An event handler when a route is clicked.
     * Show the description and update the panes.
     * @param event an event object
     */
    @FXML
    private void onCheckRouteClick(MouseEvent event) {
        final Main main = Main.getInstance();
        final Route route = main.routes.get(((CheckBox) event.getSource()).getText());
        if (route == null) {
            throw new Main.RouteNotFoundException(((CheckBox) event.getSource()).getText());
        }

        // Show the clicked route and refill the other panes for it
        pickedRoute = route;
        textPickedRoute.setText(route.name);
        textRouteDescription.setText(route.description);
        updateLocoPane(route);
        updateJourneyPane(route);
        updateScenarioPane(route);
    }

    /**
     * An event handler when a route checkbox is ticked or unticked.
     * Include or exclude the route with the checkbox state.
     * @param event an event object
     */
    @FXML
    private void onCheckRouteInclude(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        final Main main = Main.getInstance();
        final Route route = main.routes.get(checkBox.getText());
        if (route == null) {
            throw new Main.RouteNotFoundException(checkBox.getText());
        }

        if (!checkBox.isSelected()) {
            // Excluded
            main.excludeRoute(route);
        }
        else {
            // Included
            main.includeRoute(route);
        }
    }

    /**
     * An event handler when a locomotive is clicked.
     * Show the description and update the livery pane.
     * @param event an event object
     */
    @FXML
    private void onCheckLocoClick(MouseEvent event) {
        if (pickedRoute == null) {
            // Route not picked, early return
            Alert routeNotPicked = new Alert(AlertType.ERROR, "A route is not picked!");
            routeNotPicked.show();
            return;
        }

        CheckBox checkBox = (CheckBox) event.getSource();
        final Locomotive loco = (Locomotive) checkBox.getUserData(); // Set in updateLocoPane

        // Show the clicked loco and refill the other panes for it
        textPickedLoco.setText(loco.name);
        textLocoDescription.setText(loco.description);
        updateLiveryPane(pickedRoute, loco);
        pickedLoco = loco; // After updateLiveryPane, which resets the picked livery only
    }

    /**
     * An event handler when a locomotive checkbox is ticked or unticked.
     * Include or exclude the locomotive with the checkbox state.
     * @param event an event object
     */
    @FXML
    private void onCheckLocoInclude(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        final Main main = Main.getInstance();
        final Route route = pickedRoute; // These checkboxes are shown only for the picked route
        final Locomotive loco = (Locomotive) checkBox.getUserData(); // Set in updateLocoPane

        if (!checkBox.isSelected()) {
            // Excluded
            main.excludeLocomotive(route, loco);
        }
        else {
            // Included
            main.includeLocomotive(route, loco);
        }
    }

    /**
     * An event handler when a livery checkbox is ticked or unticked.
     * Include or exclude the livery with the checkbox state.
     * @param event an event object
     */
    @FXML
    private void onCheckLiveryInclude(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        final Main main = Main.getInstance();
        final Route route = pickedRoute; // These checkboxes are shown only for the picked route
        final Locomotive loco = (Locomotive) checkBox.getUserData(); // Set in updateLiveryPane

        if (!checkBox.isSelected()) {
            // Excluded
            main.excludeLivery(route, loco, checkBox.getText());
        }
        else {
            // Included
            main.includeLivery(route, loco, checkBox.getText());
        }
    }

    @FXML
    private void onCheckJourneyInclude(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        final Main main = Main.getInstance();
        final Route route = pickedRoute; // These checkboxes are shown only for the picked route

        if (!checkBox.isSelected()) {
            // Excluded
            main.excludeJourney(route, checkBox.getText());
        }
        else {
            // Included
            main.includeJourney(route, checkBox.getText());
        }
    }

    @FXML
    private void onCheckScenarioInclude(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        final Main main = Main.getInstance();
        final Route route = pickedRoute; // These checkboxes are shown only for the picked route
        final Scenario scenario = (Scenario) checkBox.getUserData(); // Set in updateScenarioPane

        if (!checkBox.isSelected()) {
            // Excluded
            main.excludeScenario(route, scenario);
        }
        else {
            // Included
            main.includeScenario(route, scenario);
        }
    }

    @FXML
    private void onSave(ActionEvent event) {
        final Main main = Main.getInstance();
        if (!main.hasConfigFile()) {
            // No config file yet, ask where to save it
            onSaveAs(event);
            return;
        }
        try {
            main.save();
        } catch (IOException e) {
            new Alert(AlertType.ERROR, "Failed to save the config file:\n" + e.getMessage()).show();
        }
    }

    @FXML
    private void onSaveAs(ActionEvent event) {
        final FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Config File");
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        if (Settings.DOCUMENT_DIRECTORY.isDirectory()) {
            chooser.setInitialDirectory(Settings.DOCUMENT_DIRECTORY);
        }
        chooser.setInitialFileName("randomtsw.json");
        final File file = chooser.showSaveDialog(anchorPane.getScene().getWindow());
        if (file == null) return; // Cancelled

        try {
            Main.getInstance().saveAs(file.getPath()); // Also remembered for the next startup
        } catch (IOException e) {
            new Alert(AlertType.ERROR, "Failed to save " + file.getPath() + ":\n" + e.getMessage()).show();
        }
    }

    @FXML
    private void onLoad(ActionEvent event) {
        final FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Config File");
        chooser.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
        if (Settings.DOCUMENT_DIRECTORY.isDirectory()) {
            chooser.setInitialDirectory(Settings.DOCUMENT_DIRECTORY);
        }
        final File file = chooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (file == null) return; // Cancelled

        final Main main = Main.getInstance();
        try {
            main.reload(file.getPath());
        } catch (IOException e) {
            new Alert(AlertType.ERROR, "Failed to load " + file.getPath() + ":\n" + e.getMessage()).show();
            return;
        }

        // Update the route ticks, they are kept by the route pane
        for (final Node node : boxRoute.getChildren()) {
            final CheckBox routeCheckBox = (CheckBox) node;
            routeCheckBox.setSelected(main.includedRoutes.contains(main.routes.get(routeCheckBox.getText())));
        }

        // Drop the cached checkboxes, they are built again with the loaded ticks
        locoBoxes.clear();
        journeyBoxes.clear();
        scenarioBoxes.clear();
        liveryBoxes.clear();
        if (pickedRoute != null) {
            // Show the picked route again, this resets the other picks
            updateLocoPane(pickedRoute);
            updateJourneyPane(pickedRoute);
            updateScenarioPane(pickedRoute);
        }
    }

    @FXML
    private void onHelp(ActionEvent event) {
    }

    @FXML
    private void onRandomRoute(ActionEvent event) {
        final Main main = Main.getInstance();
        List<Route> includedRoutes = new ArrayList<>(main.includedRoutes);
        if (includedRoutes.isEmpty()) {
            Alert emptyRoutes = new Alert(AlertType.ERROR, "No routes are included to pick from!");
            emptyRoutes.show();
            return;
        }
        final Route route = includedRoutes.get(random.nextInt(includedRoutes.size()));

        pickedRoute = route;
        textPickedRoute.setText(route.name);
        textRouteDescription.setText(route.description);
        updateLocoPane(route);
        updateJourneyPane(route);
        updateScenarioPane(route);
    }

    @FXML
    private void onRandomLoco(ActionEvent event) {
        final Main main = Main.getInstance();
        if (pickedRoute == null) {
            new Alert(AlertType.ERROR, "A route is not picked!").show();
            return;
        }

        List<Locomotive> includedLocos = new ArrayList<>(main.includedLocomotives.get(pickedRoute));
        if (includedLocos.isEmpty()) {
            new Alert(AlertType.ERROR, "No locomotives are included to pick from!").show();
            return;
        }
        final Locomotive loco = includedLocos.get(random.nextInt(includedLocos.size()));

        textPickedLoco.setText(loco.name);
        textLocoDescription.setText(loco.description);
        updateLiveryPane(pickedRoute, loco);
        pickedLoco = loco; // After updateLiveryPane, which resets the picked livery only
    }

    @FXML
    private void onRandomLivery(ActionEvent event) {
        final Main main = Main.getInstance();
        if (pickedRoute == null || pickedLoco == null) {
            new Alert(AlertType.ERROR, "A route or locomotive is not picked!").show();
            return;
        }

        List<String> includedLiveries = new ArrayList<>(main.includedLiveries.get(pickedRoute).get(pickedLoco));
        if (includedLiveries.isEmpty()) {
            new Alert(AlertType.ERROR, "No liveries are included to pick from!").show();
            return;
        }
        pickedLivery = includedLiveries.get(random.nextInt(includedLiveries.size()));
        textPickedLivery.setText(pickedLivery);
    }

    @FXML
    private void onRandomJourney(ActionEvent event) {
        final Main main = Main.getInstance();
        if (pickedRoute == null) {
            new Alert(AlertType.ERROR, "A route is not picked!").show();
            return;
        }

        List<String> includedJourneys = new ArrayList<>(main.includedJourneys.get(pickedRoute));
        if (includedJourneys.isEmpty()) {
            new Alert(AlertType.ERROR, "No journeys are included to pick from!").show();
            return;
        }
        pickedJourney = includedJourneys.get(random.nextInt(includedJourneys.size()));
        textPickedJourney.setText(pickedJourney);
    }

    @FXML
    private void onRandomScenario(ActionEvent event) {
        final Main main = Main.getInstance();
        if (pickedRoute == null) {
            new Alert(AlertType.ERROR, "A route is not picked!").show();
            return;
        }

        List<Scenario> includedScenarios = new ArrayList<>(main.includedScenarios.get(pickedRoute));
        if (includedScenarios.isEmpty()) {
            new Alert(AlertType.ERROR, "No scenarios are included to pick from!").show();
            return;
        }
        pickedScenario = includedScenarios.get(random.nextInt(includedScenarios.size()));
        textPickedScenario.setText(pickedScenario.name);
        // Scenarios have no description, so show their details instead
        textScenarioDescription.setText("Difficulty: " + pickedScenario.difficulty + "\nLength: " + pickedScenario.length
                + "\nLocomotives: " + String.join(", ", pickedScenario.locomotives));
    }

    @FXML
    private void onRandomAll(ActionEvent event) {
        final Main main = Main.getInstance();

        // Route first, the others depend on it
        onRandomRoute(event);
        if (main.includedRoutes.isEmpty()) return; // Route pick failed, alert already shown

        // A new route resets the picked locomotive, so null means the pick failed
        onRandomLoco(event);
        if (pickedLoco != null) {
            onRandomLivery(event);
        }

        onRandomJourney(event);
        onRandomScenario(event);
    }
}
