package com.valantic.sti.tutorial;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class Main extends Application {

    private Stage window;

    public static void main(final String... args) {
        launch(args);
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

        final GridPane layout = new GridPane();
        layout.getChildren().addAll(button1, button2, button3, button4);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(10);
        layout.setHgap(5);

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

        final Stage win = new Stage();
        win.setTitle(title);
        win.setScene(scene);
        win.show();

        return win;
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
        final ChoiceBox<String> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().add("Apples");
        choiceBox.getItems().add("Bananas");
        choiceBox.getItems().addAll("Bacon", "Ham", "Meatballs");

        choiceBox.setValue("Apples"); // pre-defined value

        final Button button = new Button("Click me");
        button.setOnAction(e -> {
            final String food = choiceBox.getValue();
            log.info("button: {}", food);
        });

        // add selection listener
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                (property, oldValue, newValue) -> log.info("selected: old value '{}', new value '{}'", oldValue, newValue)
        );

        return List.of(choiceBox, button);
    }

    private List<Node> buildComboBox() {
        final ComboBox<String> comboBox = new ComboBox<>();

        comboBox.getItems().addAll("Good Will Hunting", "St. Vincent", "Blackhat");
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

}
