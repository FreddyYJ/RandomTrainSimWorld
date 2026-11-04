/**
 * RandomTSW is random picker for Train Sim World.
 * @author FreddyYJ_
 */
module RandomTrainSimWorld.main {
    exports com.github.freddyyj.randomtrainsimworld;
    exports com.github.freddyyj.randomtrainsimworld.gui;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires org.apache.commons.io;
    requires com.google.common;
    opens com.github.freddyyj.randomtrainsimworld.gui to javafx.fxml;
}