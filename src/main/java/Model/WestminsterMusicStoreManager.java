package Model;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class  WestminsterMusicStoreManager implements StoreManager {
    private ArrayList<MusicItem> items = new ArrayList<>();
    private MongoDatabase database;
    private MongoCollection<Document> MusicItemCollection;

    public WestminsterMusicStoreManager(MongoDatabase database) {
        this.database = database;
        MusicItemCollection = database.getCollection("MusicItem");
        // https://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        MusicItemCollection.find().forEach(printBlock);

    }

    // TODO: This need to hold max of 1000 items
    @Override
    public void addItem(MusicItem[] items) {
        for(MusicItem item: items){
            this.items.add(item);
            System.out.println(item.getClass());
//            MusicItemCollection.insertOne();
        }
    }

    @Override
    public boolean deleteItem(int itemId) {
        for(MusicItem item: items) {
            if (item.getItemID() == itemId) {
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
    public void sellItem(int itemId) {

    }

    @Override
    public void updateSalesLog() {

    }
}
