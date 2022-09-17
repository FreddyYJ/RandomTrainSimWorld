package com.github.freddyyj.randomtrainsimworld.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Main GUI class extends {@link Application}.
 * Use {@link Main#getController()} to get Controller object of this GUI.
 */
public class Main extends Application{
	/**
	 * main {@link Scene}
	 */
	static Scene scene;
	/**
	 * main {@link Stage}
	 */
	static Stage stage;
	/**
	 * default {@link MainController}
	 */
	static MainController controller;

	/**
	 * Overrides {@link Application#start(Stage)}.
	 * @param primaryStage {@link Main#stage}
	 * @throws Exception all exception throws
	 */
	@Override
	public void start(Stage primaryStage) throws Exception{
		FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainDoc.fxml"));
		scene=new Scene(loader.load(),250,75);
		stage=primaryStage;
		primaryStage.setTitle("Random Train Sim World 2");
		primaryStage.setScene(scene);
		primaryStage.setWidth(500);
		primaryStage.setHeight(400);

		if(Thread.currentThread().getContextClassLoader().getResourceAsStream("icon.png")!=null)
			primaryStage.getIcons().add(new Image(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("icon.png"))));
		controller=loader.getController();

		com.github.freddyyj.randomtrainsimworld.Main.getInstance();

			primaryStage.setOnCloseRequest(event -> {
				try {
					com.github.freddyyj.randomtrainsimworld.Main core = com.github.freddyyj.randomtrainsimworld.Main.getInstance();
					if (core.isSaveChanged()) {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save changed?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
						alert.setHeaderText("Savefile changed!");
						alert.setTitle("Save File");
						Optional<ButtonType> result = alert.showAndWait();

						if (result.isPresent() && result.get() == ButtonType.YES) {
							controller.onSave(null);
							core.saveConfig();
							core.close();
						} else event.consume();
					}
					core.saveConfig();
					primaryStage.close();
				}catch (IOException e){
					System.out.println("Error occurred at initializing JavaFX!");
					e.printStackTrace();
					System.exit(1);
				}
			});
		primaryStage.show();
	}
	public static MainController getController(){
		return controller;
	}
}
