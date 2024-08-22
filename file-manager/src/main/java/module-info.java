module br.ufal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens br.ufal to javafx.fxml;

    exports br.ufal;
}