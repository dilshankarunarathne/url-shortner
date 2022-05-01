package me.karunarathne.urlshortener ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import me.karunarathne.adfly.RestAPI.APIWrapper;
import me.karunarathne.adfly.app.Shorten;

public class AuthWindowController {
    APIWrapper apiWrapper ;

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
        statusLabel.setText("Auth called for User: " + userId.getText());
        Long USER_ID = Long.parseLong (userId.getText ()) ;
        String PUBLIC_KEY = puk.getText() ;
        String SECRET_KEY = sk.getText() ;

        if (PUBLIC_KEY.isEmpty() || SECRET_KEY.isEmpty()) {
            // TODO: error message
            statusLabel.setText ("Error: PUBLIC_KEY or SECRET_kEY was null !") ;
        }

        apiWrapper 
    }
}