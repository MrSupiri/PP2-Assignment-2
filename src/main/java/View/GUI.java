package View;

import Model.Admins.WestminsterMusicStoreManager;
import Model.Helpers.Date;
import Model.Items.CD;
import Model.Items.MusicItem;
import Model.Items.Vinyl;

import javafx.application.Application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.util.ArrayList;

public class GUI extends Application {
    private static String searchTerm = "";
    private static TableView table;

    public static void main() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        Label searchBoxLabel = new Label("Find a Item");
        TextField searchBox = new TextField();
        searchBox.setPromptText("Enter the Name");

        //  https://stackoverflow.com/questions/30160899/value-change-listener-for-javafxs-textfield
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTerm = newValue;
            updateTable();
        });

        HBox hBox = new HBox(searchBoxLabel, searchBox);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(30);
        VBox.setMargin(hBox, new Insets(50, 0 , 30, 30));

        table = generateTableForCD();
        VBox vBox = new VBox(hBox, table);

        Scene scene = new Scene(vBox,  1617, 616);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // http://tutorials.jenkov.com/javafx/tableview.html
    @SuppressWarnings("unchecked")
    private static TableView generateTableForCD(){
        TableView tableView = new TableView();

        TableColumn<MusicItem, String> itemID = new TableColumn<>("Item ID");
        itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        tableView.getColumns().add(itemID);

        TableColumn<MusicItem, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.getColumns().add(title);

        TableColumn<MusicItem, String> genre = new TableColumn<>("Genre");
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableView.getColumns().add(genre);

        TableColumn<MusicItem, Date> releaseDate = new TableColumn<>("Release Date");
        releaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        tableView.getColumns().add(releaseDate);

        TableColumn<MusicItem, String> artist = new TableColumn<>("Artist");
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        tableView.getColumns().add(artist);

        TableColumn<MusicItem, BigDecimal> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.getColumns().add(price);

        TableColumn<MusicItem, String> songs = new TableColumn<>("Songs");
        songs.setCellValueFactory(p -> {
            if(p.getValue().getClass().getName().equals("Model.Items.CD")){
                CD item = (CD) p.getValue();
                return new SimpleStringProperty(item.getSongs().toString().replace("[", "").replace("]", ""));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(songs);

        TableColumn<MusicItem, String> totalDuration = new TableColumn<>("Total Duration");
        totalDuration.setCellValueFactory(p -> {
            if(p.getValue().getClass().getName().equals("Model.Items.CD")){
                CD item = (CD) p.getValue();
                return new SimpleStringProperty(String.format("%s:%-2d mins", item.getTotalDuration()/60, item.getTotalDuration()%60));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(totalDuration);

        TableColumn<MusicItem, String> speed = new TableColumn<>("Speed");
        speed.setCellValueFactory(p -> {
            if(p.getValue().getClass().getName().equals("Model.Items.Vinyl")){
                Vinyl item = (Vinyl) p.getValue();
                return new SimpleStringProperty(Integer.toString(item.getSpeed()));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(speed);

        TableColumn<MusicItem, String> diameter = new TableColumn<>("Diameter");
        diameter.setCellValueFactory(p -> {
            if(p.getValue().getClass().getName().equals("Model.Items.Vinyl")){
                Vinyl item = (Vinyl) p.getValue();
                return new SimpleStringProperty(Double.toString(item.getDiameter()));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(diameter);

//        for(MusicItem item: WestminsterMusicStoreManager.getItems()){
//            if(item.getClass().getName().equals("Model.Items.Vinyl")){
//                Vinyl vinyl = (Vinyl)  item;
//                tableView.getItems().add(vinyl);
//            }
//            else{
//                CD cd = (CD) item;
//                tableView.getItems().add(cd);
//            }
//        }
//
        tableView.getItems().addAll( WestminsterMusicStoreManager.getItems());

        return tableView;
    }

    private static void updateTable(){
        table.getItems().clear();
        for(MusicItem item: WestminsterMusicStoreManager.getItems()){
            if(item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
                table.getItems().add(item);
            }
        }
        table.refresh();
    }

}
