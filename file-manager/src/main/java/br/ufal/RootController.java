package br.ufal;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

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
    File userHome = new File(homeDirectory);
    TreeItem<File> rootItem = new TreeItem<>(userHome);
    fileTree.setRoot(rootItem);
    TreeItem<File> homeItem = new TreeItem<>(userHome);
    rootItem.getChildren().add(homeItem);
    for (File rootDir : File.listRoots()) {
      TreeItem<File> item = new TreeItem<>(rootDir);
      rootItem.getChildren().add(item);
    }
    addTreeItemChildren(homeItem, userHome.listFiles());
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
            this.setText(item.getName() != "" ? item.getName() : "/");
            File file = this.getItem();
            if (file.isDirectory())
              this.setGraphic(createArrowGraphic());
            else
              this.setGraphic(null);

            this.setOnMouseClicked(e -> {
              if (this.isEmpty())
                return;
              TreeItem<File> treeItem = this.getTreeItem();
              ObservableList<TreeItem<File>> children = treeItem.getChildren();
              if (file.isDirectory() && children.isEmpty()) {
                addTreeItemChildren(treeItem, file.listFiles());
                sortTreeChildren(children);
                treeItem.setExpanded(true);
              }
            });
          }
        };
      }
    });

    fileGrid.setHgap(10);
    fileGrid.setVgap(20);
    fileGrid.setPadding(new Insets(8));
    int numColumns = 4;
    for (int i = 0; i < numColumns; i++) {
      ColumnConstraints constraints = new ColumnConstraints();
      constraints.setPercentWidth(25);
      constraints.setHalignment(HPos.CENTER);
      constraints.setHgrow(Priority.ALWAYS);
      fileGrid.getColumnConstraints().add(constraints);
    }

    MultipleSelectionModel<TreeItem<File>> selectionModel = fileTree.getSelectionModel();
    selectionModel.setSelectionMode(SelectionMode.SINGLE);
    selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      replaceGridPane(newValue.getValue());
    });
    selectionModel.select(homeItem);

    ShowHiddenObserver.addListener(showHidden -> {
      replaceGridPane(selectionModel.getSelectedItem().getValue());
    });
  }

  private void replaceGridPane(File directory) {
    fileGrid.getChildren().clear();
    List<File> files = new ArrayList<>();
    for (File file : directory.listFiles()) {
      if (!file.getName().startsWith(".") || !ShowHiddenObserver.shouldHide())
        files.add(file);
    }
    sortByFilename(files);
    for (int i = 0; i < files.size(); i++) {
      File file = files.get(i);
      Button fileName = new Button(file.getName());

      fileName.setOnMouseClicked(e -> {
        if (file.isDirectory())
          replaceGridPane(file);
        else // TODO open file with default program

          System.out.println(file);
      });
      fileName.setWrapText(true);
      fileGrid.add(fileName, i % 4, (int) i / 4);
    }
  }

  private void addTreeItemChildren(TreeItem<File> item, File... children) {
    if (children == null)
      return;
    for (File child : children) {
      if (child.isDirectory() && !child.getName().startsWith("."))
        item.getChildren().add(new TreeItem<>(child));
    }
  }

  private Node createArrowGraphic() {
    Image img = new Image(getClass().getResourceAsStream("/br/ufal/images/folder.png"));
    ImageView view = new ImageView(img);
    view.setFitWidth(16);
    view.setFitHeight(16);
    return view;
  }

  private void sortTreeChildren(List<TreeItem<File>> children) {
    // TODO refactor to use sortFiles
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

  private void sortByFilename(List<File> files) {
    files.sort(new Comparator<>() {
      public int compare(File file1, File file2) {
        if (file1.isDirectory() && file2.isFile())
          return -1;
        else if (file1.isFile() && file2.isDirectory())
          return 1;
        return file1.getName().compareToIgnoreCase(file2.getName());
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
