package me.karunarathne.urlshortener ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField userId ;
    @FXML
    private TextField puk ;
    @FXML
    private TextField sk ;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void authenticate(ActionEvent actionEvent) {
        System.out.println("Auth called with " + userId.getText());
    }
}