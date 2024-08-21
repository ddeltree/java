package br.ufal;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;
import java.nio.file.Path;

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
    fileTree.setRoot(new TreeItem<>(cwd));
    for (File child : cwd.listFiles()) {
      fileTree.getRoot().getChildren().add(new TreeItem<>(child));
    }
    sortTreeChildren(fileTree.getRoot().getChildren());
    fileTree.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
      @Override
      public TreeCell<File> call(TreeView<File> tree) {
        return new TreeCell<File>() {
          @Override
          protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              this.setText(null);
              this.setGraphic(null);
              return;
            }
            this.setText(item.getName());
            if (this.getItem().isDirectory()) {
              this.setGraphic(createArrowGraphic());
            } else {
              this.setGraphic(null);
            }
            this.setOnMouseClicked(e -> {
              if (this.isEmpty())
                return;
              if (this.getItem().isDirectory() && this.getTreeItem().getChildren().isEmpty()) {
                for (File child : this.getItem().listFiles()) {
                  this.getTreeItem().getChildren().add(new TreeItem<File>(child));
                }
                sortTreeChildren(this.getTreeItem().getChildren());
                this.getTreeItem().setExpanded(true);
              } else if (this.getItem().isFile()) {
                // TODO open file
                System.out.println(this.getItem().getPath());
              }
            });

          }

          private javafx.scene.Node createArrowGraphic() {
            Image img = new Image(getClass().getResourceAsStream("/br/ufal/images/folder.png"));
            ImageView view = new ImageView(img);
            view.setFitWidth(16);
            view.setFitHeight(16);
            return view;
          }
        };
      }
    });
  }

  void sortTreeChildren(List<TreeItem<File>> children) {
    children.sort(new Comparator<>() {
      public int compare(TreeItem<File> file1, TreeItem<File> file2) {
        if (file1.getValue().isDirectory() && file2.getValue().isFile())
          return -1;
        else if (file1.getValue().isFile() && file2.getValue().isDirectory())
          return 1;
        return file1.getValue().getName().compareToIgnoreCase(file2.getValue().getName());
      }
    });
  }

  void addTreeFile(File file) {
    Queue<Iterator<TreeItem<File>>> queue = new LinkedList<>();
    queue.add(this.fileTree.getRoot().getChildren().iterator());
    do {
      queue.peek().forEachRemaining(child -> {
        Path path = Path.of(file.getAbsolutePath()).relativize(Path.of(child.getValue().getAbsolutePath()));
        System.out.println(path.toString() == "");
      });
      queue.poll();
    } while (queue.peek() != null);
  }

  @FXML
  void quit(ActionEvent event) {
    System.exit(0);
  }
}
