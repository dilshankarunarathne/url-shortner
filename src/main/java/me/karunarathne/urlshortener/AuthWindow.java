package me.karunarathne.urlshortener;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class AuthWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AuthWindow.class.getResource("auth-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        stage.setTitle("URL Shortener");
        stage.setScene(scene);

        String path = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/me/karunarathne/urlshortener/icon.png" ;
        stage.getIcons().add(new Image(path));
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}