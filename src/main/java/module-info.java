/**
 * RandomTSW provides the feature to see the information of the route, locomotive, etc. and pick them randomly
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