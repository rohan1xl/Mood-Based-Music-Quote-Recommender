package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a simple UI without FXML to test JavaFX setup
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        
        Label titleLabel = new Label("🎭 Mood-Based Recommender");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label subtitleLabel = new Label("JavaFX is working! 🎉");
        subtitleLabel.setStyle("-fx-font-size: 16px;");
        
        Button testButton = new Button("Click me to test!");
        testButton.setOnAction(e -> {
            subtitleLabel.setText("Button clicked! The app is working! ✨");
        });
        
        root.getChildren().addAll(titleLabel, subtitleLabel, testButton);
        
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Mood Recommender - Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        System.out.println("✅ Application started successfully!");
    }

    public static void main(String[] args) {
        System.out.println("🚀 Starting simple test application...");
        launch(args);
    }
}
