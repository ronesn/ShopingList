package il.ac.shenkar.ronesn;

import java.util.ArrayList;

public class ItemListSingleton {

    private static ItemListSingleton instance ;
    private ArrayList<ItemDetails> itemList;

    private ItemListSingleton() {
        itemList = new ArrayList<ItemDetails>();
        setFirstList();
    }

    public static synchronized ItemListSingleton getInstance() {
        if(instance == null) {
            instance = new ItemListSingleton();
        }
        return instance;
    }


    public void addItem(ItemDetails newItem){
        itemList.add(newItem);
    }

    public void removeItem(ItemDetails itemToRemove){
        itemList.remove(itemToRemove);}

    public ItemDetails getItem(int i) {
        return itemList.get(itemList.size()-i-1);
    }

    public int getCount() {
        return itemList.size();
    }

    public boolean isEmpty(){
        return itemList.isEmpty();
    }

    private void setFirstList(){

        ItemDetails item_details = new ItemDetails("Pizza",1,Long.valueOf(1));
        itemList.add(item_details);

        item_details = new ItemDetails("Milk",2,Long.valueOf(2));
        itemList.add(item_details);

        item_details = new ItemDetails("Eggs",1,Long.valueOf(3));
        itemList.add(item_details);

        item_details = new ItemDetails("Pizza",1,Long.valueOf(4));
        itemList.add(item_details);

    }

}

