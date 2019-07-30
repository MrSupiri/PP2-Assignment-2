import Model.Admins.StoreManager;
import Model.Admins.WestminsterMusicStoreManager;
import Model.Helpers.Date;
import Model.Helpers.Utilities;
import Model.Items.CD;
import Model.Items.MusicItem;
import Model.Items.Vinyl;

import View.GUI;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.math.BigDecimal;
import java.util.*;

import static Model.Helpers.Utilities.*;

@SuppressWarnings("SameParameterValue")
public class Main {
    private static final double BUFFER_STOCK_PERCENTAGE = 60.0;
    private static StoreManager manager;

    public static void main(String[] args) {
        String MONGODB_URI = String.format("mongodb://%s:%s@%s:27017/%s?retryWrites=true&w=majority",
                System.getenv("MONGODB_USER"), System.getenv("MONGODB_PASSWORD"),
                System.getenv("MONGODB_HOST"), System.getenv("MONGODB_DATABASE"));

        MongoClientURI uri = new MongoClientURI(MONGODB_URI);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(System.getenv("MONGODB_DATABASE"));
        manager = new WestminsterMusicStoreManager(database);

        Utilities.sc = new Scanner(System.in);

        System.out.println("\n\n");
        System.out.println(" +---------------------------------------------------------+");
        System.out.println(" |   Welcome to Westminster Music Store Management System  |");
        System.out.println(" |   Please Select one of the following options to proceed |");
        System.out.println(" +---------------------------------------------------------+\n");
        displayMenu();

        int option;

        do {
            option = getIntegerInput(">>> ", "ERROR 406: Invalid Input");
            // Switch case statement to map inputs to it's relevant methods
            switch (option) {
                case 1:
                    addItemToDatabase();
                    break;

                case 2:
                    System.out.print("Enter the UUID of the Item You want to delete: ");
                    String itemID = Utilities.sc.nextLine();
                    if (manager.deleteItem(itemID))
                        System.out.println(itemID + " was deleted from the database");
                    else
                        System.out.println(itemID + " was not found on the database");
                    break;

                case 3:
                    manager.itemSummary();
                    break;

                case 4:
                    manager.listItems();
                    break;

                case 5:
                    System.out.println("Sorting in ascending Items by Item Name");
                    manager.sortItems();
                    break;

                case 6:
                    sellItems();
                    break;

                case 7:
                    GUI.main();
                    break;

                case 8:
                    displayMenu();
                    break;

                case 9:
                    stockRecommendation(database.getCollection("salesLog"));
                    break;

                case -1:
                    break;

                // Entered Value didn't match with any of the outputs
                default:
                    System.out.println("\nERROR 406: Invalid Input");
                    displayMenu();
            }

        } while (option != -1);

        System.out.println("Syncing with Database");
        System.out.println("Gracefully exiting the Program");
        System.exit(0);

    }

    private static void sellItems() {
        ArrayList<String> cart = new ArrayList<>();
        String itemID;
        BigDecimal total = BigDecimal.ZERO;
        System.out.print("Enter the UUID of the Item you want to buy (enter -1 to exit) : ");
        itemID = Utilities.sc.nextLine();

        while (!itemID.equals("-1")) {
            MusicItem item = manager.searchItem(itemID);
            if (item != null) {
                total = total.add(item.getPrice());
                System.out.printf("%s was added to cart which cost USD %s\n", item.getTitle(), item.getPrice());
                cart.add(item.getItemID());
            } else {
                System.out.println("Items was not found the Database");
            }
            System.out.print("Enter the UUID of the Item you want to buy (enter -1 to exit) : ");
            itemID = Utilities.sc.nextLine();

        }

        System.out.printf("Your Total for %s items is USD %s\n", cart.size(), total);
        if (cart.size() > 0)
            manager.sellItems(cart);
    }

    // TODO: Handle Custom Exceptions
    private static void addItemToDatabase() {
        String type;
        System.out.print("What kind of Music Item you want to Add ? (CD/Vinyl): ");
        type = Utilities.sc.nextLine().toLowerCase();
        while (!type.equals("cd") && !type.equals("vinyl")){
            System.out.println("\n\tInvalid Input !");
            System.out.print("What kind of Music Item you want to Add ? (CD/Vinyl): ");
            type = Utilities.sc.nextLine().toLowerCase();
        }
        System.out.printf("Name of the %s: ", type);
        String name = Utilities.sc.nextLine();
        System.out.printf("Genre of the %s: ", type);
        String genre = Utilities.sc.nextLine();

        Date releaseDate = getReleaseDate(type);

        System.out.printf("Artist of the %s: ", type);
        String artist = Utilities.sc.nextLine();

        BigDecimal price = getBigDecimalInput(String.format("Price of the %s: ", type), "Invalid Price");
            if (type.equals("cd")) {
                CD cd = new CD(name, genre, releaseDate, artist, price);
                System.out.println("Enter the song names and duration on the CD");
                System.out.println("When you done adding enter -1 as the song name to exit\n");
                String songName;
                int i = 1;
                do {
                    System.out.printf("Enter the song number %s of %s CD: ", i, name);
                    songName = Utilities.sc.nextLine();
                    if (!songName.equals("-1"))
                        cd.addSong(songName, getIntegerInput("Duration of the Song in seconds: ", "Invalid Duration"));
                    i++;
                } while (!songName.equals("-1"));
                manager.addItem(cd);
                System.out.printf("%s CD was successfully added to the Database, Item ID - %s\n", name, cd.getItemID());
            } else {
                int speed = getIntegerInput("Speed of the Vinyl type in RPM: ", "Invalid RPM", true);
                double diameter = getDoubleInput("Diameter of the Vinyl type in CM: ", "Invalid Diameter", true);
                Vinyl vinyl = new Vinyl(name, genre, releaseDate, artist, price, speed, diameter);
                manager.addItem(vinyl);
                System.out.printf("%s vinyl record was successfully added to the Database, Item ID - %s\n", name, vinyl.getItemID());
            }

        displayMenu();
    }

    /**
     * Get All the sales happened within last month
     * Count much each item was sold
     * Add Buffer stock percentage to that count
     */
    private static void stockRecommendation(MongoCollection<Document> salesLog) {
        HashMap<String, Integer> items = new HashMap<>();

        // https://stackoverflow.com/questions/6840540/java-mongodb-query-by-date
        Bson filter = new Document("$gte", firstOfLastMonth()).append("$lt", firstOfThisMonth());

        salesLog.find(new Document("timeOfPurchase", filter)).forEach((Block<Document>) document -> {
            String itemID = document.getString("itemID");

            // If the Item was deleted from Music Item Collection but has sold copies in last month
            if (items.containsKey(itemID)) {
                items.put(itemID, items.get(itemID) + 1);
            } else {
                items.put(itemID, 1);
            }
        });

        for (MusicItem item : WestminsterMusicStoreManager.getItems()) {
            items.putIfAbsent(item.getItemID(), 0);
        }

        String format = "| %-3s | %-32s | %-25s | %-9s |\n";

        System.out.println("+-----+----------------------------------+---------------------------+-----------+");
        System.out.println("|  #  |             Item UUID            |            Title          |  Quantity |");
        System.out.println("+-----+----------------------------------+---------------------------+-----------+");

        int index = 1;

        // https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            MusicItem musicItem = manager.searchItem(((Map.Entry) entry).getKey().toString());
            if (musicItem != null) {
                System.out.printf(format, index, musicItem.getItemID(), musicItem.getTitle(), calculateStockRecommendation((int) ((Map.Entry) entry).getValue()));
                System.out.println("+-----+----------------------------------+---------------------------+-----------+");
            }
            index++;
        }
    }

    // Help Methods

    /**
     * Return the Recommendation for Restock items
     * @param sales - Number of sales done last month
     * @return - number of copies need to buy
     */
    private static int calculateStockRecommendation(int sales) {
        return (int) Math.ceil(sales + (sales * BUFFER_STOCK_PERCENTAGE / 100));
    }

    private static void displayMenu() {
        System.out.println(" +---------------------------------------------------------+");
        System.out.println(" | 1 | Add a Item to them System                           |");
        System.out.println(" | 2 | Delete Item from the System                         |");
        System.out.println(" | 3 | Print Summery about Items in the System             |");
        System.out.println(" | 4 | Print Detailed Summery about Items in the System    |");
        System.out.println(" | 5 | Sort Items in ascending order by title              |");
        System.out.println(" | 6 | Sell Item(s) to User                                |");
        System.out.println(" | 7 | Launch JavaFX UI                                    |");
        System.out.println(" | 8 | Display the Menu Again                              |");
        System.out.println(" | 9 | Stock recommendation                                |");
        System.out.println(" | -1 | Exit Program                                       |");
        System.out.println(" +---------------------------------------------------------+");
    }

}
