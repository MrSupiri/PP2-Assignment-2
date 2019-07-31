package View;

import Helpers.Date;
import Model.Admins.WestminsterMusicStoreManager;
import Model.Items.CD;
import Model.Items.MusicItem;
import Model.Items.Vinyl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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

public class GUI extends Application {
    private static String searchTerm = "";
    private static TableView table;
    private static Stage stage;

    public static void main() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // This will make sure JavaFX application is not destroyed when user click on the close button
        Platform.setImplicitExit(false);

        Label searchBoxLabel = new Label("Find a Item");
        TextField searchBox = new TextField();
        searchBox.setPromptText("Enter the Name");

        // This will execute anonymous function every time text in the searchBox changes
        //  https://stackoverflow.com/questions/30160899/value-change-listener-for-javafxs-textfield
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTerm = newValue;
            updateTable();
        });

        HBox hBox = new HBox(searchBoxLabel, searchBox);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(30);
        VBox.setMargin(hBox, new Insets(50, 0, 30, 30));

        table = generateTable();
        VBox vBox = new VBox(hBox, table);

        Scene scene = new Scene(vBox, 1617, 616);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        stage = primaryStage;
//        primaryStage.show();
    }

    // Show the JavaFX Application
    public static void showStage(){
        stage.show();
    }

    // http://tutorials.jenkov.com/javafx/tableview.html
    @SuppressWarnings("unchecked")
    private static TableView generateTable() {
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
        // As Every Music Item don't have songs attribute I create lambda function that will check the Object type
        // and give different outputs to different object types
        // Ex - Vinyl class don't have songs attribute so it will display Not Applicable for Row that contains Vinyl Objects
        songs.setCellValueFactory(p -> {
            if (p.getValue().getClass().getName().equals("Model.Items.CD")) {
                CD item = (CD) p.getValue();
                return new SimpleStringProperty(item.getSongs().toString().replace("[", "").replace("]", ""));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(songs);

        TableColumn<MusicItem, String> totalDuration = new TableColumn<>("Total Duration");
        totalDuration.setCellValueFactory(p -> {
            if (p.getValue().getClass().getName().equals("Model.Items.CD")) {
                CD item = (CD) p.getValue();
                return new SimpleStringProperty(String.format("%s:%-2d mins", item.getTotalDuration() / 60, item.getTotalDuration() % 60));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(totalDuration);

        TableColumn<MusicItem, String> speed = new TableColumn<>("Speed");
        speed.setCellValueFactory(p -> {
            if (p.getValue().getClass().getName().equals("Model.Items.Vinyl")) {
                Vinyl item = (Vinyl) p.getValue();
                return new SimpleStringProperty(Integer.toString(item.getSpeed()));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(speed);

        TableColumn<MusicItem, String> diameter = new TableColumn<>("Diameter");
        diameter.setCellValueFactory(p -> {
            if (p.getValue().getClass().getName().equals("Model.Items.Vinyl")) {
                Vinyl item = (Vinyl) p.getValue();
                return new SimpleStringProperty(Double.toString(item.getDiameter()));
            }
            return new SimpleStringProperty("N/A");
        });
        tableView.getColumns().add(diameter);

        tableView.getItems().addAll(WestminsterMusicStoreManager.getItems());

        return tableView;
    }

    /**
     * This will clear the all the entries on the table insert only entries that match the searchTerm
     */
    private static void updateTable() {
        table.getItems().clear();
        for (MusicItem item : WestminsterMusicStoreManager.getItems()) {
            if (item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                //noinspection unchecked
                table.getItems().add(item);
            }
        }
        table.refresh();
    }

}
