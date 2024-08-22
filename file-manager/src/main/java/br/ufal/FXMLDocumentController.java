package br.ufal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author sedri
 */
public class FXMLDocumentController implements Initializable {

    HostServices hostServices;

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        hostServices.showDocument("www.google.com");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setGetHostController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

}