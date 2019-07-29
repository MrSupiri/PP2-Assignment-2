import Model.*;
import Model.Admins.StoreManager;
import Model.Admins.WestminsterMusicStoreManager;
import Model.Items.CD;
import Model.Items.MusicItem;
import Model.Items.Vinyl;
import View.GUI;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String MONGODB_URI = String.format("mongodb://%s:%s@%s:27017/%s?retryWrites=true&w=majority",
                System.getenv("MONGODB_USER"), System.getenv("MONGODB_PASSWORD"),
                System.getenv("MONGODB_HOST"), System.getenv("MONGODB_DATABASE"));

        MongoClientURI uri = new MongoClientURI(MONGODB_URI);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(System.getenv("MONGODB_DATABASE"));
        StoreManager manager = new WestminsterMusicStoreManager(database);

        System.out.println("\n\n");
        System.out.println(" +---------------------------------------------------------+");
        System.out.println(" |   Welcome to Westminster Music Store Management System  |");
        System.out.println(" |   Please Select one of the following options to proceed |");
        System.out.println(" +---------------------------------------------------------+\n");
        displayMenu();

        int option;

        do {
            option = getIntegerInput(">>> ", "\nERROR 406: Invalid Input\n");
            // Switch case statement to map inputs to it's relevant methods
            switch (option) {
                case 1:
                    addItemToDatabase(manager);
                    break;

                case 2:
                    System.out.print("Enter the UUID of the Item You want to delete: ");
                    String itemID = sc.nextLine();
                    if(manager.deleteItem(itemID))
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
                    sellItems(manager);
                    break;

                case 7:
                    GUI.main(database.getCollection("MusicItem"));
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

    private static void sellItems(StoreManager manager){
        ArrayList<String> cart = new ArrayList<>();
        String itemID;
        BigDecimal total = BigDecimal.ZERO;
        System.out.print("Enter the UUID of the Item you want to buy (enter -1 to exit) : ");
        itemID = sc.nextLine();

        while(!itemID.equals("-1")){
            MusicItem item = manager.searchItem(itemID);
            if(item != null){
                total = total.add(item.getPrice());
                System.out.printf("%s was added to cart which cost USD %s\n", item.getTitle(), item.getPrice());
                cart.add(item.getItemID());
            }
            else{
                System.out.println("Items was not found the Database");
            }
            System.out.print("Enter the UUID of the Item you want to buy (enter -1 to exit) : ");
            itemID = sc.nextLine();

        }

        System.out.printf("Your Total for %s items is USD %s\n", cart.size(), total);
        if(cart.size() > 0)
            manager.sellItems(cart);
    }

    private static void addItemToDatabase(StoreManager manager){
        System.out.print("What kind of Music Item you want to Add ? (CD/Vinyl): ");
        String type;
        do{
            type = sc.nextLine().toLowerCase();
        }while(!type.equals("cd") && !type.equals("vinyl"));
        System.out.printf("Name of the %s: ", type);
        String name = sc.nextLine();
        System.out.printf("Genre of the %s: ", type);
        String genre = sc.nextLine();

        int releasedYear = getIntegerInput(String.format("Released year of this %s: ", type), "Invalid year!");
        int releasedMonth = getIntegerInput(String.format("Released month of this %s: ", type), "Invalid month!");
        int releasedDay = getIntegerInput(String.format("Released day of this %s: ", type), "Invalid day!");
        Date releaseDate = new Date(releasedYear, releasedMonth, releasedDay);

        System.out.printf("Artist of the %s: ", type);
        String artist = sc.nextLine();

        BigDecimal price = getBigDecimalInput(String.format("Price of the %s: ", type), "Invalid Price");

        if(type.equals("cd")){
            CD cd = new CD(name, genre, releaseDate, artist, price);
            System.out.println("Enter the song names and duration on the CD");
            System.out.println("When you done adding enter -1 as the song name to exit\n");
            String songName;
            int i = 1;
            do {
                System.out.printf("Enter the song number %s of %s CD: ", i, name);
                songName = sc.nextLine();
                if(!songName.equals("-1"))
                    cd.addSong(songName, getIntegerInput("Duration of the Song in seconds: ", "Invalid Duration"));
                i++;
            }while (!songName.equals("-1"));
            manager.addItem(cd);
            System.out.printf("%s CD was successfully added to the Database, Item ID - %s\n", name, cd.getItemID());
        }
        else{
            int speed = getIntegerInput("Speed of the Vinyl type in RPM: ", "Invalid RPM");
            double diameter = getDoubleInput("Diameter of the Vinyl type in CM: ", "Invalid Diameter");
            Vinyl vinyl = new Vinyl(name, genre, releaseDate, artist, price, speed, diameter);
            manager.addItem(vinyl);
            System.out.printf("%s vinyl record was successfully added to the Database, Item ID - %s\n", name, vinyl.getItemID());
        }

        displayMenu();
    }

    // Help Methods
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
    private static int getIntegerInput(String message, String error){
        System.out.print(message);
        while (!sc.hasNextInt()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }

    private static BigDecimal getBigDecimalInput(String message, String error){
        System.out.print(message);
        while (!sc.hasNextBigDecimal()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        BigDecimal input = sc.nextBigDecimal();
        sc.nextLine();
        return input;
    }

    private static double getDoubleInput(String message, String error){
        System.out.print(message);
        while (!sc.hasNextDouble()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        double input = sc.nextDouble();
        sc.nextLine();
        return input;
    }
}
