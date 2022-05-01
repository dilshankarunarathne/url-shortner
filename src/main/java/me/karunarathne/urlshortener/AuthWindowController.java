package me.karunarathne.urlshortener ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuthWindowController {
    @FXML
    private Label statusLabel ;

    @FXML
    private TextField userId ;
    @FXML
    private TextField puk ;
    @FXML
    private TextField sk ;

    public AuthWindowController () {
    }

    @FXML
    public void authenticate(ActionEvent actionEvent) {
        System.out.println("Auth called with " + userId.getText());
    }
}