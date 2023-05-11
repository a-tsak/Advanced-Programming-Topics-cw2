package gr.uop;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        // Importing the images for the buttons
        ImageView up = new ImageView(new Image(getClass().getResourceAsStream("images/up.png")));
        ImageView down = new ImageView(new Image(getClass().getResourceAsStream("images/down.png")));
        ImageView right = new ImageView(new Image(getClass().getResourceAsStream("images/right.png")));
        ImageView left = new ImageView(new Image(getClass().getResourceAsStream("images/left.png")));

        // making an ObservableList with strings from item 0 to item 50
        ObservableList<String> itemsLeft = FXCollections.observableArrayList();
        for (int i = 0; i < 51; i++) {
            itemsLeft.add("item " + i);

        }
        // making another empty observable list
        ObservableList<String> itemsRight = FXCollections.observableArrayList(); // the list on the right

        // The first Vbox containing the filter and the list
        TextField filter = new TextField();
        filter.setPromptText("type to filter");
        // The left list
        ListView leftList = new ListView<>(itemsLeft);

        // First VBox properties
        VBox list1 = new VBox(5);
        list1.setAlignment(Pos.CENTER);
        list1.setPrefWidth(150);
        list1.setMinWidth(150);

        list1.getChildren().addAll(filter, leftList);

        // The second VBox containing the left and right buttons
        VBox buttons1 = new VBox(5);
        buttons1.setAlignment(Pos.CENTER);
        Button rightButton = new Button();
        Button leftButton = new Button();
        rightButton.setGraphic(right);
        leftButton.setGraphic(left);

        buttons1.getChildren().addAll(rightButton, leftButton);

        // The third VBox containing the second list
        ListView rightList = new ListView<>(itemsRight);

        // Third VBox properties
        VBox list2 = new VBox(5);
        list2.setPadding(new Insets(30, 0, 0, 0));
        list2.setAlignment(Pos.CENTER);
        list2.setPrefWidth(150);
        list2.setMinWidth(150);
        list2.getChildren().addAll(rightList);

        // The fourth VBox containing the up and down buttons
        VBox buttons2 = new VBox(5);
        buttons2.setAlignment(Pos.CENTER);
        Button upButton = new Button();
        Button downButton = new Button();
        upButton.setGraphic(up);
        downButton.setGraphic(down);

        buttons2.getChildren().addAll(upButton, downButton);

        // Functionalities
        filter.textProperty().addListener(new ChangeListener<String>() { // Filter application

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                leftList.setItems(showList(itemsLeft, newValue));

            }

        });

        rightButton.setOnAction((e) -> { // Right button pressed
            Object temp = leftList.getSelectionModel().getSelectedItem();

            if (temp != null) {

                leftList.getSelectionModel().select(leftList.getSelectionModel().getSelectedIndex() + 1); // selects
                                                                                                          // automatically
                                                                                                          // the next
                                                                                                          // item
                leftList.getItems().remove(temp);
                rightList.getItems().add(temp);
            }

        });

        leftButton.setOnAction((e) -> { // Left button pressed
            Object temp = rightList.getSelectionModel().getSelectedItem();
            int index;
            if (temp != null) {

                rightList.getSelectionModel().select(rightList.getSelectionModel().getSelectedIndex() + 1); // selects
                                                                                                            // automatically
                                                                                                            // the next
                                                                                                            // item
                rightList.getItems().remove(temp);

                String[] split = temp.toString().split(" ");
                index = Integer.parseInt(split[1]);
                System.out.println(temp.toString());
                leftList.getItems().add(index, temp);
            }

        });

        // A Hbox with all the Vboxes containing the items and controls
        HBox vBoxes = new HBox(5);
        vBoxes.setPadding(new Insets(10, 10, 50, 10));
        vBoxes.getChildren().addAll(list1, buttons1, list2, buttons2);
        vBoxes.setAlignment(Pos.CENTER);

        var scene = new Scene(vBoxes, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Lists");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList showList(ObservableList leftList, String filter) {
        ObservableList<String> filteredList = FXCollections.observableArrayList(); // another list that is used to show
                                                                                   // filtered items

        leftList.forEach((item) -> {
            if (item.toString().contains(filter)) {
                filteredList.add(item.toString());
            }
        });

        if (filter.isBlank()) {
            return leftList;

        }

        return filteredList;
    }

}