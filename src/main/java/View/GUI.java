package View;

import Model.Items.MusicItem;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bson.Document;

public class GUI extends Application {
    private static MongoCollection<Document> musicItemCollection;

    public static void main(MongoCollection<Document> collection) {
        launch();
        musicItemCollection = collection;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(new VBox(), 500, 500));
        primaryStage.show();
    }

//    private TableView generateTableForCD(){
//        TableView tableView = new TableView();
//
//        TableColumn<String, MusicItem> itemID = new TableColumn<>("Item ID");
//        itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
//        tableView.getColumns().add(itemID);
//
//        TableColumn<String, MusicItem> title = new TableColumn<>("Item Name");
//        title.setCellValueFactory(new PropertyValueFactory<>("title"));
//        tableView.getColumns().add(title);
//
//        TableColumn<String, MusicItem> genre = new TableColumn<>("Genre");
//        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
//        tableView.getColumns().add(genre);
//
//        TableColumn<String, MusicItem> releaseDate = new TableColumn<>("Release Date");
//        releaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
//        tableView.getColumns().add(releaseDate);
//
//        TableColumn<String, MusicItem> artist = new TableColumn<>("Artist");
//        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
//        tableView.getColumns().add(artist);
//
//        TableColumn<String, MusicItem> price = new TableColumn<>("Price");
//        price.setCellValueFactory(new PropertyValueFactory<>("price"));
//        tableView.getColumns().add(price);
//
//        musicItemCollection.find().forEach((Block<Document>) document -> {
//
//        });
//
//        return tableView;
//    }

}
