package Model.Admins;
import Helpers.Date;

import Helpers.NoSpaceLeftInStoreException;
import Model.Items.CD;
import Model.Items.MusicItem;
import Model.Items.Vinyl;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

// https://mongodb.github.io/mongo-java-driver/3.11/driver/getting-started/quick-start/
@SuppressWarnings("unchecked")
/*
  This class implements StoreManager and Handle all most all the use cases
 */
public class WestminsterMusicStoreManager implements StoreManager {
    private static ArrayList<MusicItem> items = new ArrayList<>();
    private static final int MAX_COUNT = 1000;
    private MongoCollection<Document> musicItemCollection;
    private MongoDatabase database;

    /**
     * Initialed the WestminsterMusicStoreManager by fetching data from the database and making the items list
     * @param database - MongoDB Database Object that was initialized in Main Class
     */
    public WestminsterMusicStoreManager(MongoDatabase database) {
        this.database = database;
        musicItemCollection = this.database.getCollection("MusicItem");
        musicItemCollection.find().forEach((Block<Document>) document -> {
            Document date = (Document) document.get("releaseDate");
            if(document.getString("type").equals("Vinyl")){
                items.add(
                        new Vinyl(
                                document.getString("itemID"),
                                document.getString("title"),
                                document.getString("genre"),
                                new Date(date.getInteger("year"),
                                        date.getInteger("month"),
                                        date.getInteger("day")),
                                document.getString("artist"),
                                ((Decimal128) document.get("price")).bigDecimalValue(),
                                document.getInteger("speed"),
                                document.getDouble("diameter")
                        )
                );
            }
            else{
                items.add(
                        new CD(
                                document.getString("itemID"),
                                document.getString("title"),
                                document.getString("genre"),
                                new Date(date.getInteger("year"),
                                        date.getInteger("month"),
                                        date.getInteger("day")),
                                document.getString("artist"),
                                ((Decimal128) document.get("price")).bigDecimalValue(),
                                (ArrayList<String>) document.get("songs"),
                                document.getInteger("totalDuration")

                        )
                );
            }
        });

    }

    /**
     * Add new Music Item to the items list and Save it on the database
     * @param item - Music Item that need to be added
     */
    @Override
    public void addItem(MusicItem item) {
        if(items.size() > MAX_COUNT){
            throw new NoSpaceLeftInStoreException("There is no space to store more than 1000 items");
        }

        items.add(item);

        Document doc = new Document("itemID", item.getItemID())
                .append("title", item.getTitle())
                .append("genre", item.getGenre())
                .append("releaseDate", new Document("year", item.getReleaseDate().getYear())
                        .append("month", item.getReleaseDate().getMonth())
                        .append("day", item.getReleaseDate().getDay())
                ).append("artist", item.getArtist())
                .append("price", item.getPrice());

        if(item.getClass().getName().equals("Model.Items.Vinyl")){
            Vinyl vinyl = (Vinyl) item;
            doc.append("speed", vinyl.getSpeed())
                    .append("diameter", vinyl.getDiameter())
                    .append("type", "Vinyl");
        }
        else{
            CD cd = (CD) item;
            doc.append("songs", cd.getSongs())
                    .append("totalDuration", cd.getTotalDuration())
                    .append("type", "CD");
        }

        musicItemCollection.insertOne(doc);
    }

    /**
     * Remove Music Item from items list and delete it from the database
     * @param itemId - UUID of Music Item that need to be added
     */
    // TODO: Show space left
    @Override
    public boolean deleteItem(String itemId) {
        for (MusicItem item : items) {
            if (item.getItemID().equals(itemId)) {
                items.remove(item);
                musicItemCollection.deleteOne(eq("itemID", itemId));
                return true;
            }
        }
        return false;
    }

    /**
     * Print out a table that contains Detailed description of every Item in the store
     */
    @Override
    public void listItems(){
        String format = "| %-3s | %-32s | %-25s | %-10s | %-12s | %-17s | %-9s | %-18s | %-14s | %-5s | %-8s |%n";

        System.out.println("+-----+----------------------------------+---------------------------+------------+--------------+-------------------+-----------+--------------------+----------------+-------+----------+");
        System.out.println("|  #  |             Item UUID            |            Title          | Genre      | Release Date |       Artist      |   Price   |        Songs       | Total Duration | Speed | Diameter |");
        System.out.println("+-----+----------------------------------+---------------------------+------------+--------------+-------------------+-----------+--------------------+----------------+-------+----------+");

        int index = 1;
        for(MusicItem item: items){
            if(item.getClass().getName().equals("Model.Items.CD")){
                CD cd = (CD) item;
                System.out.printf(format, index, cd.getItemID(), cd.getTitle(), cd.getGenre(), cd.getReleaseDate(), cd.getArtist(), "USD "+cd.getPrice(), cd.getSongs().get(0), String.format("%s:%-2d mins", cd.getTotalDuration()/60, cd.getTotalDuration()%60),  "-", "-");
                for(int y=1; y<cd.getSongs().size(); y++){
                    System.out.printf(format, "", "", "", "", "", "", "", cd.getSongs().get(y), "", "", "");
                }
            }
            else{
                Vinyl vinyl = (Vinyl) item;
                System.out.printf(format, index, vinyl.getItemID(), vinyl.getTitle(), vinyl.getGenre(), vinyl.getReleaseDate(), vinyl.getArtist(), "USD "+vinyl.getPrice(), "-", "-", vinyl.getSpeed(), vinyl.getDiameter());
            }
            System.out.println("+-----+----------------------------------+---------------------------+------------+--------------+-------------------+-----------+--------------------+----------------+-------+----------+");
            index++;
        }
    }

    /**
     * Print out a table that contains Basic details of every Item in the store
     */
    @Override
    public void itemSummary(){
        String format = "| %-3s | %-32s | %-25s | %-6s |\n";

        System.out.println("+-----+----------------------------------+---------------------------+--------+");
        System.out.println("|  #  |             Item UUID            |            Title          |  Type  |");
        System.out.println("+-----+----------------------------------+---------------------------+--------+");

        int index = 1;
        for(MusicItem item: items){
            String type = item.getClass().getName().equals("Model.Items.CD") ? "CD" : "Vinyl";
            System.out.printf(format, index, item.getItemID(), item.getTitle(), type);
            System.out.println("+-----+----------------------------------+---------------------------+--------+");
            index++;
        }
    }

    /**
     * Sort Every Item in the items array by it's title in ascending order
     * Inspired from https://beginnersbook.com/2013/12/java-string-comparetoignorecase-method-example/
     */
    @Override
    public void sortItems() {
        items.sort((item1, item2) -> item1.getTitle().compareToIgnoreCase(item2.getTitle()));
    }

    /**
     * Add a list of items salesLog collection in the database and write it to salesLog.csv to later analyzing
     * @param items - List of Music Items IDs
     */
    @Override
    public void sellItems(ArrayList<String> items) {
        MongoCollection<Document> salesLog = this.database.getCollection("salesLog");
        StringBuilder salesLogCSV = new StringBuilder();
        List<Document> documents = new ArrayList<>();
        for(String item: items){
            MusicItem musicItem = this.searchItem(item);
            Document doc = new Document("title", musicItem.getTitle())
                    .append("itemID", musicItem.getItemID())
                    .append("price", musicItem.getPrice())
                    .append("timeOfPurchase", new java.util.Date());
            documents.add(doc);
            salesLogCSV.append(String.format("%s,%s,%s,%s\n", musicItem.getTitle(), musicItem.getItemID(), musicItem.getPrice(), new java.util.Date()));
        }
        salesLog.insertMany(documents);

        // https://stackoverflow.com/questions/9620683/java-fileoutputstream-create-file-if-not-exists
        try {
            File salesLogFile = new File("SalesLog.csv");
            boolean newFile = salesLogFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream salesLogFileSteam = new FileOutputStream(salesLogFile, true);
            if(newFile)
                salesLogFileSteam.write("title,itemID,price,timeOfPurchase\n".getBytes());
            salesLogFileSteam.write(salesLogCSV.toString().getBytes());
            salesLogFileSteam.close();
        }catch (IOException e) {
            System.out.println("\n\tError While writing to salesLog.csv\n");
        }
    }

    /**
     * Return Music Item as Object given a itemID
     * @param itemID - UUID of the Item you want to find
     * @return Item Object, This returns null if it didn't find a match
     */
    @Override
    public MusicItem searchItem(String itemID) {
        for(MusicItem item: items){
            if(item.getItemID().equals(itemID)){
                return item;
            }
        }
        return null;
    }

    /**
     * Getter for items ArrayList
     * @return - ArrayList of Music Items
     */
    public static ArrayList<MusicItem> getItems(){
        return items;
    }
}
