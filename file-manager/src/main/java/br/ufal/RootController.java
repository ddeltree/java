package br.ufal;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.File;
import java.util.Collection;

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

    TreeItem<String> root = new TreeItem<>(cwd.getName());
    Collection<TreeItem<String>> children = root.getChildren();
    if (cwd.isDirectory()) {
      for (File file : cwd.listFiles()) {
        if (file.getName().startsWith(".")) {
          continue;
        }
        TreeItem<String> item = new TreeItem<>(file.getName());

        fileTree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
          @Override
          public TreeCell<String> call(TreeView<String> fileTree) {
            return new TreeCell<>() {
              @Override
              protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                  this.setText(null);
                  this.setGraphic(null);
                } else {
                  this.setText(item);

                  // Add event handler for mouse clicks
                  this.setOnMouseClicked(e -> {
                    if (!this.isEmpty()) {
                      System.out.println("Clicked on: " + this.getItem());
                    }
                  });
                }
              }
            };
          }
        });
        children.add(item);
      }
    }
    fileTree.setRoot(root);
  }

  @FXML
  void quit(ActionEvent event) {
    System.exit(0);
  }
}
