package View;

import Model.Admins.WestminsterMusicStoreManager;
import Model.Helpers.Date;
import Model.Items.CD;
import Model.Items.MusicItem;
import Model.Items.Vinyl;

import javafx.application.Application;

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

        TableColumn<CD, ArrayList> songs = new TableColumn<>("Songs");
        songs.setCellValueFactory(new PropertyValueFactory<>("songs"));
        tableView.getColumns().add(songs);

        TableColumn<CD, Integer> totalDuration = new TableColumn<>("Total Duration");
        totalDuration.setCellValueFactory(new PropertyValueFactory<>("totalDuration"));
        tableView.getColumns().add(totalDuration);

        TableColumn<Vinyl, Integer> speed = new TableColumn<>("Speed");
        speed.setCellValueFactory(new PropertyValueFactory<>("speed"));
        tableView.getColumns().add(speed);

        TableColumn<Vinyl, Double> diameter = new TableColumn<>("Diameter");
        diameter.setCellValueFactory(new PropertyValueFactory<>("diameter"));
        tableView.getColumns().add(diameter);

        for(MusicItem item: WestminsterMusicStoreManager.getItems()){
            if(item.getClass().getName().equals("Model.Items.Vinyl")){
                tableView.getItems().add((Vinyl) item);
            }
            else{
                tableView.getItems().add((CD) item);
            }
        }

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
