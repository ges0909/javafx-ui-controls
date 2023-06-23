package com.valantic.sti.tutorial;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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

        final GridPane layout = new GridPane();
        layout.getChildren().addAll(button1, button2);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);

        final Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.setTitle("JavaFX UI Controls");
        window.show();
    }

    private void buildWindow(final String title, final double width, final double height, final Supplier<List<Node>> supplier) {
        final List<Node> content = supplier.get();

        final VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(content);

        final Scene scene = new Scene(layout, width, height);

        final Stage win = new Stage();
        win.setTitle(title);
        win.setScene(scene);
        win.show();
    }

    private List<Node> buildCheckBox() {
        final CheckBox box1 = new CheckBox("Bacon");
        final CheckBox box2 = new CheckBox("Tuna");
        box2.setSelected(true);

        final Button button = new Button("Order Now!");
        button.setOnAction(e -> {
            String message = "Selected:";
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

        choiceBox.setValue("Apples");

        final Button button = new Button("Click me");
        button.setOnAction(e -> {
            final String food = choiceBox.getValue();
            log.info("Selected: {}", food);
        });

        return List.of(choiceBox, button);
    }
}
