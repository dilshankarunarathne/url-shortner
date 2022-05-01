module me.karunarathne.urlshortener.urlshortner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens me.karunarathne.urlshortener to javafx.fxml;
    exports me.karunarathne.urlshortener ;
}