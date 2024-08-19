package br.ufal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class TestController {

  @FXML
  private Button cancel;

  @FXML
  private Button ok;

  @FXML
  void cancelHandler(ActionEvent event) throws IOException {
    System.out.println("Cancelado!");
    App.setRoot("secondary");
  }

  @FXML
  void okHandler(ActionEvent event) throws IOException {
    System.out.println("OK!");
    App.setRoot("primary");

  }

}
