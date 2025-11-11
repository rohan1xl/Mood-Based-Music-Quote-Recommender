package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String APP_TITLE = "Mood-Based Music & Quote Recommender";
    private static final String FXML_PATH = "/ui/main.fxml";
    private static final String CSS_PATH = "/css/style.css";

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
            Parent root = loader.load();

            // Create scene
            Scene scene = new Scene(root, 1200, 800);
            
            // Add CSS
            String cssUrl = getClass().getResource(CSS_PATH).toExternalForm();
            scene.getStylesheets().add(cssUrl);

            // Configure stage
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            
            // Center on screen
            primaryStage.centerOnScreen();
            
            // Show the application
            primaryStage.show();
            
            LOGGER.info("Application started successfully");
            
        } catch (Exception e) {
            LOGGER.severe("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("Application stopping...");
        // Cleanup resources if needed
        super.stop();
    }

    public static void main(String[] args) {
        // Set system properties for better JavaFX performance
        System.setProperty("javafx.preloader", "");
        System.setProperty("prism.lcdtext", "false");
        
        LOGGER.info("Starting Mood-Based Music & Quote Recommender...");
        launch(args);
    }
}
