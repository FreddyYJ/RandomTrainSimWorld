package com.github.freddyyj.randomtrainsimworld.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
	// Set when an error alert is shown, other errors after it are only printed
	private static final AtomicBoolean errorShown = new AtomicBoolean(false);

	/**
	 * Overrides {@link Application#start(Stage)}.
	 * @param primaryStage {@link Main#stage}
	 * @throws Exception all exception throws
	 */
	@Override
	public void start(Stage primaryStage) throws Exception{
		// Show unhandled exceptions of any thread (including event handlers), then terminate
		Thread.setDefaultUncaughtExceptionHandler((thread, e) -> showErrorAndExit(e));

		// Exceptions in start() are not passed to the handler above, so catch them here
		try {
			// JavaFX shows its own labels (Yes, No, ...) in Korean, but the default font has no Hangul
			if (Locale.getDefault().getLanguage().equals(Locale.KOREAN.getLanguage())) {
				final List<String> families = Font.getFamilies();
				String koreanFont = null;
				for (final String family : List.of("Malgun Gothic", "Gulim", "Apple SD Gothic Neo", "Noto Sans CJK KR", "NanumGothic")) {
					if (families.contains(family)) {
						koreanFont = family;
						break;
					}
				}

				if (koreanFont == null) {
					// No Korean font, use English labels instead (before any alert is created)
					Locale.setDefault(Locale.ENGLISH);
				} else {
					final String css = ".root { -fx-font-family: \"" + koreanFont + "\"; }";
					final String stylesheet = "data:text/css;base64,"
							+ Base64.getEncoder().encodeToString(css.getBytes(StandardCharsets.UTF_8));
					// Add the font to every window when it is shown, including alerts
					Window.getWindows().addListener((ListChangeListener<Window>) change -> {
						while (change.next()) {
							for (final Window window : change.getAddedSubList()) {
								if (window.getScene() != null && !window.getScene().getStylesheets().contains(stylesheet)) {
									window.getScene().getStylesheets().add(stylesheet);
								}
							}
						}
					});
				}
			}

			FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainDoc.fxml"));
			scene=new Scene(loader.load(),1200,750);
			stage=primaryStage;
			primaryStage.setTitle("Random Train Sim World");
			primaryStage.setScene(scene);
			primaryStage.setWidth(1200);
			primaryStage.setHeight(750);

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

							// Yes: save and close, No: close without saving, Cancel: keep the window open
							if (result.isPresent() && result.get() == ButtonType.YES) {
								if (core.hasConfigFile()) {
									core.save();
								} else {
									// No config file yet, ask where to save it
									FileChooser chooser = new FileChooser();
									chooser.setTitle("Save Config File");
									chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON File", "*.json"));
									if (com.github.freddyyj.randomtrainsimworld.Settings.DOCUMENT_DIRECTORY.isDirectory()) {
										chooser.setInitialDirectory(com.github.freddyyj.randomtrainsimworld.Settings.DOCUMENT_DIRECTORY);
									}
									chooser.setInitialFileName("randomtsw.json");
									File file = chooser.showSaveDialog(primaryStage);
									if (file == null) {
										event.consume(); // Cancelled, keep the window open
										return;
									}
									core.saveAs(file.getPath());
								}
							} else if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
								event.consume();
							}
						}
					} catch (IOException e){
						// Keep the window open, so the user can retry or close without saving
						event.consume();
						new Alert(Alert.AlertType.ERROR, "Failed to save the config file:\n" + e.getMessage()).show();
					}
				});
			primaryStage.show();
		} catch (Throwable e) {
			showErrorAndExit(e);
		}
	}
	// Shows the error with its stack trace, then terminates the program
	static void showErrorAndExit(final Throwable e) {
		e.printStackTrace();
		if (!errorShown.compareAndSet(false, true)) return; // Show the first error only

		// Wrappers like FXML LoadException hide the real message, so show the root cause
		Throwable cause = e;
		while (cause.getCause() != null && cause.getCause() != cause) cause = cause.getCause();
		final String message = cause.getClass().getName() + ": " + cause.getMessage();

		final StringWriter stackTrace = new StringWriter();
		e.printStackTrace(new PrintWriter(stackTrace));
		try {
			// Show later on the JavaFX thread, showAndWait() may not be allowed where it was thrown
			Platform.runLater(() -> {
				try {
					final Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Unexpected Error");
					alert.setHeaderText("An unexpected error occurred. The program will be terminated.");
					alert.setContentText(message);

					final TextArea stackTraceArea = new TextArea(stackTrace.toString());
					stackTraceArea.setEditable(false);
					stackTraceArea.setPrefSize(800, 400);
					alert.getDialogPane().setExpandableContent(stackTraceArea);
					alert.getDialogPane().setExpanded(true);
					alert.showAndWait();
				} finally {
					System.exit(1);
				}
			});
		} catch (IllegalStateException notStarted) {
			// JavaFX is not running, so only the printed stack trace is left
			System.exit(1);
		}
	}

	public static MainController getController(){
		return controller;
	}
}
