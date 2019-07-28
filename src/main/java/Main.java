import Model.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.math.BigDecimal;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/StudentRef.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        String MONGODB_URI = String.format("mongodb://%s:%s@%s:27017/%s?retryWrites=true&w=majority",
                System.getenv("MONGODB_USER"), System.getenv("MONGODB_PASSWORD"),
                System.getenv("MONGODB_HOST"), System.getenv("MONGODB_DATABASE"));

        MongoClientURI uri = new MongoClientURI(MONGODB_URI);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(System.getenv("MONGODB_DATABASE"));
        StoreManager manager = new WestminsterMusicStoreManager(database);
//        manager.deleteItem("5bb1dd8cf4ff45eeb4eb4ee3dd508392");
//        manager.sortItems();
        manager.addItem(new Vinyl("Reputation", "POP", new Date(2017, 11, 10), "Taylor Swift", new BigDecimal("32.86"), 200, 30));
        manager.listItems();
//        CD differentWorld = new CD("Different World", "EDM", new Date(2018, 12, 14), "Alan Walker", new BigDecimal("9.99"));
//        differentWorld.addSong("Intro", 76);
//        differentWorld.addSong("Lost Control", 222);
//        differentWorld.addSong("I Don't Wanna Go", 161);
//        differentWorld.addSong("Lily", 195);
//        differentWorld.addSong("Lonely", 216);
//        differentWorld.addSong("Different World", 202);
//        differentWorld.addSong("Interlude", 79);
//        differentWorld.addSong("Sing Me To Sleep", 188);
//        manager.addItem(differentWorld);
    }


}
