package Model;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;

// https://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/
public class WestminsterMusicStoreManager implements StoreManager {
    private ArrayList<MusicItem> items = new ArrayList<>();
    private MongoCollection<Document> musicItemCollection;

    public WestminsterMusicStoreManager(MongoDatabase database) {
        musicItemCollection = database.getCollection("MusicItem");
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        musicItemCollection.find().forEach(printBlock);
    }

    // TODO: This need to hold max of 1000 items
    @Override
    public void addItem(MusicItem item) {
        this.items.add(item);
        Document doc = new Document("itemID", item.getItemID())
                .append("title", item.getTitle())
                .append("genre", item.getGenre())
                .append("releaseDate", new Document("year", item.getReleaseDate().getYear())
                        .append("month", item.getReleaseDate().getMonth())
                        .append("day", item.getReleaseDate().getDay())
                ).append("artist", item.getArtist())
                .append("price", item.getPrice());
        if(item.getClass().getName().equals("Model.Vinyl")){
            Vinyl vinyl = (Vinyl) item;
            doc.append("speed", vinyl.getSpeed())
                    .append("diameter", vinyl.getDiameter());
        }
        else{
            CD cd = (CD) item;
            doc.append("songs", cd.getSongs())
                    .append("totalDuration", cd.getTotalDuration());
        }
        musicItemCollection.insertOne(doc);
    }

    @Override
    public boolean deleteItem(String itemId) {
        for (MusicItem item : items) {
            if (item.getItemID().equals(itemId)) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public void sortItems() {
        items.sort((item1, item2) -> item1.getTitle().compareToIgnoreCase(item2.getTitle()));
    }

    @Override
    public void sellItem(String itemId) {

    }

    @Override
    public void updateSalesLog() {

    }
}
