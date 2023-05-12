package gr.uop;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
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
        rightButton.setDisable(true);
        leftButton.setDisable(true);
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
        upButton.setDisable(true);
        downButton.setDisable(true);
        upButton.setGraphic(up);
        downButton.setGraphic(down);

        buttons2.getChildren().addAll(upButton, downButton);

        // Functionalities





        leftList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);    //Setting the selection mode for single item
        rightList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



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
            buttonCheck(rightList, upButton, downButton);


            

        });

        leftButton.setOnAction((e) -> { // Left button pressed
            Object temp = rightList.getSelectionModel().getSelectedItem();

            


            
            if (temp != null) {

                rightList.getSelectionModel().select(rightList.getSelectionModel().getSelectedIndex() + 1); // selects
                                                                                                            // automatically
                                                                                                            // the next
                                                                                                            // item
                rightList.getItems().remove(temp);
                leftList.getItems().add(temp);



                leftList.getItems().sort(new CustomComparator());       //Whenever an item is added to the left list, it sorts it by comparing the last digits of the string
            }
            buttonCheck(rightList, upButton, downButton);

        });

        upButton.setOnAction((e)->{
            Object temp = rightList.getSelectionModel().getSelectedItem();
            int currIndex, newIndex;

            currIndex = rightList.getSelectionModel().getSelectedIndex();
            newIndex = currIndex - 1;


            rightList.getItems().remove(temp);
            rightList.getItems().add(newIndex,temp);
            rightList.getSelectionModel().select(newIndex);
            

            buttonCheck(rightList, upButton, downButton);

        });


        downButton.setOnAction((e)->{
            Object temp = rightList.getSelectionModel().getSelectedItem();
            int currIndex, newIndex;

            currIndex = rightList.getSelectionModel().getSelectedIndex();
            newIndex = currIndex + 1;


            rightList.getItems().remove(temp);
            rightList.getItems().add(newIndex,temp);
            rightList.getSelectionModel().select(newIndex);


            buttonCheck(rightList, upButton, downButton);

        });







        leftList.setOnMouseClicked((e)->{       //enables the right button once you choose something from the list
            if(leftList.getSelectionModel().getSelectedItem()!=null){
                rightButton.setDisable(false);
            }
        });

        rightList.setOnMouseClicked((e)->{       //enables the left button once you choose something from the list and checks the position of the selected item
            if(rightList.getSelectionModel().getSelectedItem()!=null){
                leftButton.setDisable(false);


                if(rightList.getSelectionModel().getSelectedIndex()==0){
                    upButton.setDisable(true);
                    downButton.setDisable(false);
                }
                else if(rightList.getSelectionModel().getSelectedIndex()==rightList.getItems().size()-1){
                    upButton.setDisable(false);
                    downButton.setDisable(true);
                }
                else{
                    upButton.setDisable(false);
                    downButton.setDisable(false);
                }





            }
            

        });
        
       
        leftList.getItems().addListener(new ListChangeListener<ObservableList>() {

            @Override
            public void onChanged(Change<? extends ObservableList> c) {
                if(leftList.getItems().isEmpty()){
                    rightButton.setDisable(true);
                }
            }
            
        });


        rightList.getItems().addListener(new ListChangeListener<ObservableList>() {

            @Override
            public void onChanged(Change<? extends ObservableList> c) {
                if(rightList.getItems().isEmpty()){
                    leftButton.setDisable(true);
                    upButton.setDisable(true);
                    downButton.setDisable(true);
                }
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




    public void buttonCheck(ListView rightList, Button upButton, Button downButton){        //A method that disables and enables buttons 

        if(rightList.getItems().size()<2){
            upButton.setDisable(true);
            downButton.setDisable(true);
            return;
        }



        if(rightList.getSelectionModel().getSelectedIndex()==0){
            upButton.setDisable(true);
            downButton.setDisable(false);
        }
        else if(rightList.getSelectionModel().getSelectedIndex()==rightList.getItems().size()-1){
            upButton.setDisable(false);
            downButton.setDisable(true);
        }
        else if(rightList.getSelectionModel().getSelectedItem()!=null){
            upButton.setDisable(false);
            downButton.setDisable(false);
        }
        return;
    }








}