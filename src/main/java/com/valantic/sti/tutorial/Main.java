package com.valantic.sti.tutorial;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Supplier;

@Slf4j
public class Main extends Application {

    private Stage window;
    private ResourceBundle resourceBundle;

    public static void main(final String... args) {
        launch(args);
    }

    @Override
    public void init() {
        resourceBundle = ResourceBundle.getBundle("choicebox_labels");
    }

    @Override
    public void start(final Stage stage) {
        window = stage;

        final Button button1 = new Button("CheckBox");
        button1.setOnAction(e -> buildWindow("GridBox", 250, 150, this::buildCheckBox));
        GridPane.setConstraints(button1, 0, 0);

        final Button button2 = new Button("ChoiceBox");
        button2.setOnAction(e -> buildWindow("ChoiceBox (Drop-Down)", 300, 150, this::buildChoiceBox));
        GridPane.setConstraints(button2, 1, 0);

        final Button button3 = new Button("ComboBox");
        button3.setOnAction(e -> buildWindow("ComboBox", 250, 150, this::buildComboBox));
        GridPane.setConstraints(button3, 2, 0);

        final Button button4 = new Button("ListView");
        button4.setOnAction(e -> buildWindow("ListView", 250, 200, this::buildListView));
        GridPane.setConstraints(button4, 0, 1);

        final Button button5 = new Button("TreeView");
        button5.setOnAction(e -> buildWindow("TreeView", 250, 300, this::buildTreeView));
        GridPane.setConstraints(button5, 1, 1);

        final Button button6 = new Button("TableView");
        button6.setOnAction(e -> buildWindow("TableView", 650, 300, this::buildTableView));
        GridPane.setConstraints(button6, 2, 1);

        final Button button7 = new Button("Menu");
        button7.setOnAction(e -> buildWindow("Menu", 250, 150, this::buildMenu));
        GridPane.setConstraints(button7, 0, 2);

        final GridPane layout = new GridPane();
        layout.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(10);
        layout.setHgap(5);

//        final ColumnConstraints column = new ColumnConstraints();
//        column.setPercentWidth(100 / 3);
//        layout.getColumnConstraints().addAll(column, column, column);

        final Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.setTitle("JavaFX UI Controls");
        window.show();
    }

    private Stage buildWindow(final String title, final double width, final double height, final Supplier<List<Node>> supplier) {
        final List<Node> content = supplier.get();

        final VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(content);

        final Scene scene = new Scene(layout, width, height);

        final Stage popupWindow = new Stage();
        popupWindow.setTitle(title);
        popupWindow.setScene(scene);
        popupWindow.setX(window.getX() + window.getX() * 0.1);
        popupWindow.setY(window.getY() + window.getY() * 0.2);
        popupWindow.show();

        return popupWindow;
    }

    private List<Node> buildCheckBox() {
        final CheckBox box1 = new CheckBox("Bacon");
        final CheckBox box2 = new CheckBox("Tuna");

        box2.setSelected(true);

        final Button button = new Button("Order Now!");
        button.setOnAction(e -> {
            String message = "button:";
            if (box1.isSelected()) {
                message += " Bacon";
            }
            if (box2.isSelected()) {
                message += " Tuna";
            }
            log.info(message);
        });

        return List.of(box1, box2, button);
    }

    private List<Node> buildChoiceBox() {
        final Label label = new Label("Make a Choice:");

        // approach #1
        // final ChoiceBox<String> choiceBox = new ChoiceBox<>();
        // choiceBox.getItems().add("Apples");
        // choiceBox.getItems().add("Bananas");
        // choiceBox.getItems().addAll("Bacon", "Ham", "Meatballs");

        // approach #2
        // final ChoiceBox<String> choiceBox = new ChoiceBox<>();
        // choiceBox.setItems(FXCollections.observableArrayList("Apples", "Bananas", "Bacon", "Ham", "Ham"));

        // approach #3
        // final ChoiceBox<String> choiceBox = new ChoiceBox<>(
        //        FXCollections.observableArrayList("Apples", "Bananas", "Bacon", "Ham", "Meatballs"));
        final ChoiceBox<String> choiceBox = new ChoiceBox<>( //
                FXCollections.observableArrayList( //
                        resourceBundle.getString("checkbox.item1.text"), //
                        resourceBundle.getString("checkbox.item2.text"), //
                        resourceBundle.getString("checkbox.item3.text"), //
                        resourceBundle.getString("checkbox.item4.text"), //
                        resourceBundle.getString("checkbox.item5.text")) //
        );

        choiceBox.setValue(resourceBundle.getString("checkbox.item1.text")); // pre-selected value

        final Button button = new Button("Click me");
        button.setOnAction(e -> {
            final String food = choiceBox.getValue();
            log.info("button: {}", food);
        });

        // add selection listener
        choiceBox.getSelectionModel().selectedItemProperty().addListener( //
                (property, oldValue, newValue) -> log.info("selected: old value '{}', new value '{}'", oldValue, newValue));

        return List.of(label, choiceBox, button);
    }

    private List<Node> buildComboBox() {
        final ComboBox<String> comboBox = new ComboBox<>();

        comboBox.getItems().addAll("Good Will Hunting", "St. Vincent", "Pretty Woman");
        comboBox.setPromptText("What is your favorite movie?");
        comboBox.setOnAction(e -> log.info("selected: {}", comboBox.getValue()));

        // comboBox.setEditable(true); // allows adding additional items

        final Button button = new Button("Submit");
        button.setOnAction(e -> log.info("button: '{}'", comboBox.getValue()));

        return List.of(comboBox, button);
    }

    private List<Node> buildListView() {
        final ListView<String> listView = new ListView<>();

        listView.getItems().addAll("Iron Man", "Titanic", "Contact", "Surrogates"); // may create scrollbar
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // allows multiple item selection

        final Button button = new Button("Submit");
        button.setOnAction(e -> {
            final ObservableList<String> items = listView.getSelectionModel().getSelectedItems();
            log.info("button: '{}'", String.join(", ", items));
        });

        return List.of(listView, button);
    }

    private List<Node> buildTreeView() {
        final TreeItem<String> top = new TreeItem<>("Top of the Pop");
        top.setExpanded(true);

        final TreeItem<String> branch1 = new TreeItem<>("Branch One");
        branch1.setExpanded(true);
        branch1.getChildren().add(new TreeItem<>("Eins"));
        branch1.getChildren().add(new TreeItem<>("One"));
        branch1.getChildren().add(new TreeItem<>("Uno"));

        final TreeItem<String> branch2 = new TreeItem<>("Branch Two");
        branch2.setExpanded(true);
        branch2.getChildren().add(new TreeItem<>("Zwei"));
        branch2.getChildren().add(new TreeItem<>("Due"));

        top.getChildren().add(branch1);
        top.getChildren().add(branch2);

        final TreeView<String> treeView = new TreeView<>(top);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().selectedItemProperty().addListener((item, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                log.info("selected '{}'", newValue.getValue());
            }
        });

        return List.of(treeView);
    }

    private List<Node> buildTableView() {
        final ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product("Laptop", 859.00, 20));
        products.add(new Product("Bouncy Ball", 2.49, 198));
        products.add(new Product("Toilet", 99.00, 74));
        products.add(new Product("The Notebook DVD", 19.99, 12));
        products.add(new Product("Corn", 1.49, 856));

        // at least one column is required
        final TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMaxWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // has to be a property name

        final TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setMaxWidth(200);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        final TableColumn<Product, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMaxWidth(200);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        final TableView<Product> table = new TableView<>();
        table.getColumns().add(nameColumn);
        table.getColumns().add(priceColumn);
        table.getColumns().add(quantityColumn);
        table.setItems(products);

        final TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        nameTextField.setMinWidth(100);

        final TextField priceTextField = new TextField();
        priceTextField.setPromptText("Price");

        final TextField quantityTextField = new TextField();
        quantityTextField.setPromptText("Quantity");

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            final Product product = new Product( //
                    nameTextField.getText(), //
                    Double.parseDouble(priceTextField.getText()), //
                    Integer.parseInt(quantityTextField.getText()) //
            );
            table.getItems().add(product);
            nameTextField.clear();
            priceTextField.clear();
            quantityTextField.clear();
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            final ObservableList<Product> allProducts = table.getItems();
            final ObservableList<Product> selectedProducts = table.getSelectionModel().getSelectedItems();
            selectedProducts.forEach(allProducts::remove);
        });

        final HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(nameTextField, priceTextField, quantityTextField, addButton, deleteButton);

        return List.of(table, hbox);
    }

    private List<Node> buildMenu() {
        final Menu fileMenu = new Menu("File");
        final MenuItem newMenuItem = new MenuItem("New...");
        newMenuItem.setOnAction(e -> log.info("Create a new file..."));
        fileMenu.getItems().add(newMenuItem);
        fileMenu.getItems().add(new MenuItem("Open..."));
        fileMenu.getItems().add(new MenuItem("Save..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Settings..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Exit..."));

        final Menu editMenu = new Menu("_Edit"); // '_' should add mnemonic
        editMenu.getItems().add(new MenuItem("Cut"));
        editMenu.getItems().add(new MenuItem("Copy"));
        final MenuItem pasteMenuItem = new MenuItem("Paste");
        pasteMenuItem.setOnAction(e -> log.info("Paste"));
        pasteMenuItem.setDisable(true);
        editMenu.getItems().add(pasteMenuItem);

        final Menu helpMenu = new Menu("_Help");
        final CheckMenuItem showLineNumbersCheckMenuItem = new CheckMenuItem("Show line numbers");
        showLineNumbersCheckMenuItem.setOnAction(e -> {
            if (showLineNumbersCheckMenuItem.isSelected()) {
                log.info("Line numbers are shown");
            } else {
                log.info("Line numbers are hidden");
            }
        });
        final CheckMenuItem autoSaveCheckMenuItem = new CheckMenuItem("Enable autosave");
        autoSaveCheckMenuItem.setSelected(true);
        helpMenu.getItems().addAll(showLineNumbersCheckMenuItem, autoSaveCheckMenuItem);

        final Menu difficultyMenu = new Menu("Difficulty");
        final ToggleGroup difficultyToggleGroup = new ToggleGroup();
        final RadioMenuItem easy = new RadioMenuItem("Easy");
        final RadioMenuItem medium = new RadioMenuItem("Medium");
        final RadioMenuItem hard = new RadioMenuItem("Hard");
        easy.setToggleGroup(difficultyToggleGroup);
        medium.setToggleGroup(difficultyToggleGroup);
        hard.setToggleGroup(difficultyToggleGroup);
        difficultyMenu.getItems().addAll(easy, medium, hard);

        final MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, difficultyMenu, helpMenu);

        final BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        return List.of(borderPane);
    }
}
