import Model.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Scanner;

public class Main extends Application {
    private static Scanner sc = new Scanner(System.in);

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("View/StudentRef.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(new VBox(), 500, 500));
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



        System.out.println("\n\n\n\n");
        System.out.println(" +---------------------------------------------------------+");
        System.out.println(" |   Welcome to Westminster Music Store Management System  |");
        System.out.println(" |   Please Select one of the following options to proceed |");
        System.out.println(" +---------------------------------------------------------+\n");
        displayMenu();

        int option;

        do {
            // Prevent User from entering a Letter
            System.out.print(">>> ");
            while (!sc.hasNextInt()) {
                System.out.println("\nERROR 406: Invalid Input\n");
                System.out.print(">>> ");
                // Take the next Input
                if(sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
            option = sc.nextInt();
            sc.nextLine();
            // Switch case statement to map inputs to it's relevant methods
            switch (option) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    launch(args);
                    break;

                case 8:
                    displayMenu();
                    break;

                case 9:
                    System.out.println("Syncing with Database");
                    System.out.println("Gracefully exiting the Program");
                    System.exit(0);
                    break;

                // Entered Value didn't match with any of the outputs
                default:
                    System.out.println("\nERROR 406: Invalid Input");
                    displayMenu();
            }

        } while (option != 9);


    }

    private static void displayMenu(){
        System.out.println(" +---------------------------------------------------------+");
        System.out.println(" | 1 | Add a Item to them System                           |");
        System.out.println(" | 2 | Delete Item from the System                         |");
        System.out.println(" | 3 | Print Summery about Items in the System             |");
        System.out.println(" | 4 | Print Detailed Summery about Items in the System    |");
        System.out.println(" | 5 | Sort Items in ascending order by title              |");
        System.out.println(" | 6 | Sell Item(s) to User                                |");
        System.out.println(" | 7 | Launch JavaFX UI                                    |");
        System.out.println(" | 8 | Display the Menu Again                              |");
        System.out.println(" | 9 | Exit Program                                        |");
        System.out.println(" +---------------------------------------------------------+");
    }

}
