/**
 * RandomTSW2 is random picker for Train Sim World 2.
 * @author FreddyYJ_
 */
module RandomTrainSimWorld2.main {
    exports com.github.freddyyj.randomtrainsimworld2;
    exports com.github.freddyyj.randomtrainsimworld2.exception;
    exports com.github.freddyyj.randomtrainsimworld2.config;
    exports com.github.freddyyj.randomtrainsimworld2.gui;
    exports com.github.freddyyj.randomtrainsimworld2.util;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    opens com.github.freddyyj.randomtrainsimworld2.gui to javafx.fxml;
}