package br.ufal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
  private TreeView<File> fileTree;

  @FXML
  private void initialize() {
    String homeDirectory = System.getProperty("user.home");
    File cwd = new File(homeDirectory);

    TreeItem<File> root = new TreeItem<>(cwd);
    Collection<TreeItem<File>> children = root.getChildren();
    if (cwd.isDirectory()) {
      for (File file : cwd.listFiles()) {
        if (file.getName().startsWith(".")) {
          continue;
        }
        TreeItem<File> item = new TreeItem<>(file);
        children.add(item);
      }
    }
    fileTree.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
      @Override
      public TreeCell<File> call(TreeView<File> fileTree) {
        return new TreeCell<>() {
          @Override
          protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              this.setText(null);
              this.setGraphic(null);
            } else {
              this.setText(item.getName());
              // Add event handler for mouse clicks
              this.setOnMouseClicked(e -> {
                if (!this.isEmpty()) {
                  System.out.println("Clicked on: " + this.getItem().getName());
                }
              });
            }
          }
        };
      }
    });
    fileTree.setRoot(root);
  }

  void pushTreeItem(File item) {

  }

  @FXML
  void quit(ActionEvent event) {
    System.exit(0);
  }
}
