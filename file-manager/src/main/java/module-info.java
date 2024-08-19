module br.ufal {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.ufal to javafx.fxml;
    exports br.ufal;
}
