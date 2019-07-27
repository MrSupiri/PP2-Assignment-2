
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/StudentRef.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        String MONGODB_URI = String.format("mongodb+srv://%s:%s@%s/%s?retryWrites=true&w=majority",
                System.getenv("MONGODB_USER"), System.getenv("MONGODB_PASSWORD"),
                System.getenv("MONGODB_HOST"), System.getProperty("MONGODB_DATABASE"));

        MongoClientURI uri = new MongoClientURI(MONGODB_URI);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(System.getProperty("MONGODB_DATABASE"));


//        launch(args);
    }


}
