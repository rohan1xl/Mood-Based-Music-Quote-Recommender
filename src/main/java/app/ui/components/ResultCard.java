package app.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ResultCard extends VBox {
    
    public ResultCard() {
        super();
        setupCard();
    }
    
    public ResultCard(String title, String content) {
        super();
        setupCard();
        setContent(title, content);
    }
    
    private void setupCard() {
        setAlignment(Pos.TOP_LEFT);
        setSpacing(10);
        setPadding(new Insets(15));
        getStyleClass().add("result-card");
        setMaxWidth(300);
    }
    
    public void setContent(String title, String content) {
        getChildren().clear();
        
        if (title != null && !title.isEmpty()) {
            Label titleLabel = new Label(title);
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            titleLabel.getStyleClass().add("card-title");
            getChildren().add(titleLabel);
        }
        
        if (content != null && !content.isEmpty()) {
            Label contentLabel = new Label(content);
            contentLabel.setWrapText(true);
            contentLabel.getStyleClass().add("card-content");
            getChildren().add(contentLabel);
        }
    }
    
    public void addCustomContent(javafx.scene.Node... nodes) {
        getChildren().addAll(nodes);
    }
}
