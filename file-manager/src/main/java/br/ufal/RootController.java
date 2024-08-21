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

  private static boolean showHidden = false;

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
      if (child.isDirectory())
        fileTree.getRoot().getChildren().add(new TreeItem<>(child));
    }
    sortTreeChildren(fileTree.getRoot().getChildren());
    fileTree.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
      @Override
      public TreeCell<File> call(TreeView<File> tree) {
        return new TreeCell<File>() {
          List<TreeItem<File>> hiddenItems = new ArrayList<>();

          @Override
          protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              this.setText(null);
              this.setGraphic(null);
              return;
            }
            this.setText(item.getName());
            File file = this.getItem();
            TreeItem<File> treeItem = this.getTreeItem();
            ObservableList<TreeItem<File>> children = treeItem.getChildren();
            if (file.isDirectory())
              this.setGraphic(createArrowGraphic());
            else
              this.setGraphic(null);

            ShowHiddenObserver.addListener(showHidden -> {
              List<TreeItem<File>> c = new ArrayList<>(children);
              for (int i = 0; i < c.size(); i++) {
                TreeItem<File> child = c.get(i);
                if (child.getValue().getName().startsWith(".")) {
                  if (!showHidden) {
                    hiddenItems.add(child);
                    children.remove(child);
                  }
                }
              }
            });
            this.setOnMouseClicked(e -> {
              if (this.isEmpty())
                return;
              if (file.isDirectory() && children.isEmpty()) {
                for (File child : file.listFiles()) {
                  if (child.isDirectory())
                    children.add(new TreeItem<File>(child));
                }
                sortTreeChildren(children);
                treeItem.setExpanded(true);
              } else if (file.isFile()) {
                System.out.println(file.getPath()); // TODO open file
              }
            });
          }
        };
      }
    });
  }

  private Node createArrowGraphic() {
    Image img = new Image(getClass().getResourceAsStream("/br/ufal/images/folder.png"));
    ImageView view = new ImageView(img);
    view.setFitWidth(16);
    view.setFitHeight(16);
    return view;
  }

  private void sortTreeChildren(List<TreeItem<File>> children) {
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
