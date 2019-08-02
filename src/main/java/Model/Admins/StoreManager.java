package Model.Admins;
import Model.Items.MusicItem;
import java.util.ArrayList;

/*
    Every Class implement this interface should have these essential methods
 */
public interface StoreManager {
    void addItem(MusicItem item);
    boolean deleteItem(String itemId);
    void listItems();
    void itemSummary();
    void sortItems();
    MusicItem searchItem(String itemID);
    void sellItems(ArrayList<String> items);
}