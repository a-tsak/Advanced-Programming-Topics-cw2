package gr.uop;

import java.util.ArrayList;

import javafx.application.Application;
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
        //Importing the images for the buttons
        ImageView up = new ImageView(new Image(getClass().getResourceAsStream("images/up.png")));
        ImageView down = new ImageView(new Image(getClass().getResourceAsStream("images/down.png")));
        ImageView right = new ImageView(new Image(getClass().getResourceAsStream("images/right.png")));
        ImageView left = new ImageView(new Image(getClass().getResourceAsStream("images/left.png")));




        //making an ObservableList with strings from item 0 to item 50
        ObservableList<String> listItems =FXCollections.observableArrayList();
        for(int i=0; i<51; i++){
            listItems.add("item "+i);

        }


        //The first Vbox
        TextField filter = new TextField();
        filter.setPromptText("type to filter");
        //The left list
        ListView itemsLeft = new ListView<>(listItems);
        
        //First VBox properties
        VBox list1 = new VBox(5);
        VBox.setMargin(list1, new Insets(5, 0, 10, 10));
        list1.setAlignment(Pos.CENTER);



        list1.getChildren().addAll(filter,itemsLeft);
        



        //The second VBox containing the left and right buttons
        VBox buttons1 = new VBox(5);
        buttons1.setAlignment(Pos.CENTER);
        Button rightButton = new Button();
        Button leftButton = new Button();
        rightButton.setGraphic(right);
        leftButton.setGraphic(left);


        buttons1.getChildren().addAll(rightButton,leftButton);









        //A Hbox with all the Vboxes containing the items and controls
        HBox vBoxes = new HBox(5);

        vBoxes.getChildren().addAll(list1,buttons1);
        HBox.setMargin(vBoxes, new Insets(5, 5, 20, 20));
        vBoxes.setAlignment(Pos.CENTER);

        var scene = new Scene(vBoxes, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Lists");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}