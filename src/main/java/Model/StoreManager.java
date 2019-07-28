package Model;

import java.util.ArrayList;

public interface StoreManager {
    ArrayList<MusicItem> items = new ArrayList<>();
    void addItem(MusicItem item);
    boolean deleteItem(int itemId);
    void sortItems();
    void sellItem(int itemId);
    void updateSalesLog();
}