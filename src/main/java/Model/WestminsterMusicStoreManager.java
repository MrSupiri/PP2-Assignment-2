package Model;

import java.util.ArrayList;

public class  WestminsterMusicStoreManager implements StoreManager {
    private ArrayList<MusicItem> items = new ArrayList<>();

    @Override
    public void addItem(MusicItem item) {
        items.add(item);
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
