package br.ufal;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import java.io.IOException;
import java.awt.Desktop;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootController {

  private VBox gridSelection;
  private TreeItem<File> treeSelection;
  private File currentDir;

  @FXML
  private Menu editMenu;

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
              if (treeSelection != treeItem) {
                treeSelection = treeItem;
                return;
              }
              replaceGridPane(treeItem.getValue());
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
      treeSelection = newValue;
    });
    selectionModel.select(homeItem);

    ShowHiddenObserver.addListener(showHidden -> {
      replaceGridPane(selectionModel.getSelectedItem().getValue());
    });
  }

  private void replaceGridPane(File directory) {
    fileGrid.getChildren().clear();
    currentDir = directory;
    List<File> files = new ArrayList<>();
    for (File file : directory.listFiles()) {
      if (!file.getName().startsWith(".") || !ShowHiddenObserver.shouldHide())
        files.add(file);
    }
    sortByFilename(files);
    for (int i = 0; i < files.size(); i++) {
      File file = files.get(i);
      VBox fileName = createGridItem(file);
      fileName.setOnMouseClicked(e -> {
        if (gridSelection != fileName) {
          if (gridSelection != null)
            gridSelection.setBackground(null);
          gridSelection = fileName;
          BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY,
              javafx.geometry.Insets.EMPTY);
          Background background = new Background(backgroundFill);
          fileName.setBackground(background);
          return;
        }
        if (file.isDirectory()) {
          replaceGridPane(file);
        } else {
          if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
              try {
                Desktop.getDesktop().open(file);
              } catch (IOException err) {
                Platform.runLater(() -> alertCouldNotProcessFile("O arquivo não pôde ser aberto."));
              }
            }).start();
          } else {
            Platform.runLater(() -> alertCouldNotProcessFile("Não há suporte na plataforma!"));
          }
        }
      });
      fileGrid.add(fileName, i % 4, (int) i / 4);
    }
  }

  private VBox createGridItem(File file) {
    Image folderImage = new Image(
        getClass().getResourceAsStream(file.isDirectory() ? "/br/ufal/images/folder.png" : "/br/ufal/images/file.png"));
    ImageView folderImageView = new ImageView(folderImage);
    folderImageView.setFitWidth(32);
    folderImageView.setFitHeight(32);
    Label nameLabel = new Label(file.getName());
    nameLabel.setWrapText(true);
    nameLabel.alignmentProperty().set(Pos.CENTER);
    VBox vbox = new VBox(folderImageView, nameLabel);
    vbox.setSpacing(5); // Optional: spacing between icon and label
    vbox.alignmentProperty().set(Pos.CENTER);
    return vbox;
  }

  private void alertCouldNotProcessFile(String msg) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erro");
    alert.setHeaderText("Erro durante a ação sobre o arquivo");
    alert.setContentText(msg);
    alert.showAndWait();
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

  @FXML
  void renameFile(ActionEvent event) {

  }

  @FXML
  void createFile(ActionEvent event) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Criar arquivo");
    dialog.setHeaderText("Insira o nome do arquivo");
    dialog.setContentText("Nome:");
    Optional<String> result = dialog.showAndWait();
    result.ifPresent(name -> {
      if (!FilenameValidator.isValidFilename(name) || name.strip() == "") {
        alertCouldNotProcessFile(name + " não é um nome de arquivo válido");
        return;
      }
      boolean isDirectory = name.endsWith("/");
      File file = new File(currentDir, name);
      if (file.exists()) {
        alertCouldNotProcessFile("O arquivo já existe!");
        return;
      }
      try {
        if (isDirectory) {
          file.mkdirs();
        } else {
          file.createNewFile();
        }
        replaceGridPane(currentDir);
      } catch (IOException err) {
        alertCouldNotProcessFile("Erro inesperado ao criar arquivo!");
      }
    });
    if (!result.isPresent()) {
      alertCouldNotProcessFile("É preciso inserir um nome de arquivo!");
    }
  }
}
