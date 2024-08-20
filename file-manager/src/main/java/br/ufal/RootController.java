package br.ufal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

import java.io.File;

public class RootController {

  @FXML
  private Menu editMenuItem;

  @FXML
  private GridPane fileGrid;

  @FXML
  private TreeView<String> fileTree;

  @FXML
  private void initialize() {
    String homeDirectory = System.getProperty("user.home");
    File cwd = new File(homeDirectory);

    if (cwd.isDirectory()) {
      for (File file : cwd.listFiles()) {
        System.out.println(file.getName());
      }
    }

    fileTree.setRoot(
        createItemHierarchy(
            new TreeItem<String>("root1"),
            new TreeItem<String>("root2")));
  }

  private TreeItem<String> createItemHierarchy(TreeItem<String>... root1) {
    TreeItem<String> root = new TreeItem<>("base root");
    root.getChildren().addAll(root1);
    return root;
  }

  @FXML
  void quit(ActionEvent event) {
    System.exit(0);
  }
}
