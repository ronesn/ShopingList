package il.ac.shenkar.ronesn;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Noam Rones on 1/12/14.
 */
public class ListSingleton {
    private static ListSingleton instance=null;
    private static DatabaseHandler DataBase ;
    private static List<ItemDetails> Items;
    private static List<String> listNames;
    private Context context;

    public static synchronized ListSingleton getInstance(Context context) {
        if (instance==null)
            instance=new ListSingleton(context);
        return instance;
    }

    private ListSingleton(Context context) {
        this.context=context;
        DataBase=new DatabaseHandler(context);
        newDatabase();
        Items=DataBase.getAllContacts();
        listNames = DataBase.getListNames();
        sortItem();
    }

    public static String getName(int i){return listNames.get(listNames.size()-i-1);}
    public  List<ItemDetails> getItems(){
        return Items;}
    public static List<String> getListNames(){ return listNames; }
    public static List<ItemDetails>getItemsInList (String listName){

        List itemList = new ArrayList<ItemDetails>();

            for(ItemDetails item : Items)
            {
                if(item.getListName().equals(listName))
                    itemList.add(item);
            }
        return itemList;
    }
    public int getCount() {return Items.size();}
    public ItemDetails getItem(int i) { return Items.get(Items.size()-i-1);}
    /**
     *   pushItem:
     *   @param newItem
     *   Add new item to the Item array,update the database and sort the array
    */
    public void pushItem(ItemDetails newItem){
        Items.add(newItem);
        DataBase.addItem(newItem);
      if(! listNames.contains(newItem.getListName()))
          listNames.add(newItem.getListName());
        sortItem();
    }

    /**
     *
     * @param ItemToRemove
     */
    //remove any Item
    public static void removeItem(ItemDetails ItemToRemove){
        Items.remove(ItemToRemove);
        DataBase.deleteItem(ItemToRemove);
    }

    public boolean isEmpty(){
        return Items.isEmpty();
    }

     private void newDatabase() {

         File dbtest =new File("/data/data/il.ac.shenkar.ronesn/databases/ListManager");
         if(dbtest.exists())
         {
            return; // what to do if it does exist
         }
         else
         {
             // what to do if it doesn't exist
         }
        pushItem(new ItemDetails("לחם", 1, 0,"רשימה לדוגמא"));
        pushItem(new ItemDetails("חלב", 2, 1,"רשימה לדוגמא"));
        pushItem(new ItemDetails("ביצים", 12, 2,"רשימה לדוגמא"));
        pushItem(new ItemDetails("גבינה לבנה", 1, 3,"רשימה לדוגמא"));
        pushItem(new ItemDetails("עוף", 1, 4,"רשימה לדוגמא"));
        pushItem(new ItemDetails("עגבניות", 2, 5,"רשימה לדוגמא"));
        pushItem(new ItemDetails("מלפפונים", 2, 6,"רשימה לדוגמא"));
    }

    public static void removeList(int tag) {
        String listName= listNames.get(listNames.size()-tag-1);
        DataBase.deleteList(listName);
        Items=DataBase.getAllContacts();
        listNames.remove(listNames.size()-tag-1);
    }
    public int getNewId(){
        int i=0;
        for(i=0;i<getCount();i++)
        {
            if(Items.get(i).getId()!=i)
                break;
        }
        return i;
    }

    public static String listToString(String listName){
        List<ItemDetails> list=getItemsInList (listName);
        String  strArray=listName+":\n";
        for(ItemDetails item : list){
            strArray+=String.valueOf(item.getQuantity())+","+item+".  ";

        }
        return  strArray;
    }
    private void sortItem(){
        Collections.sort(Items, new Comparator<ItemDetails>() {
            @Override
            public int compare(ItemDetails lhs, ItemDetails rhs) {
                return lhs.compareTo(rhs);
            }
        });
    }

}
