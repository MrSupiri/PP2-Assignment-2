package Model;

import java.util.ArrayList;

public interface StoreManager {
    ArrayList<MusicItem> items = new ArrayList<>();
    void addItem(MusicItem item);
    boolean deleteItem(String itemId);
    void sortItems();
    void sellItem(String itemId);
    void updateSalesLog();
}