package il.ac.shenkar.ronesn;
/**
 * Created by Noam Rones on 11/28/13.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "ListManager";

    // List table name
    private static final String TABLE_NAME = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_LIST = "listNum";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // SQLiteDatabase db = this.getWritableDatabase();
      //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
                + KEY_QUANTITY  + " TEXT, " + KEY_LIST + " TEXT );";
        db.execSQL(CREATE_LIST_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addItem(ItemDetails newItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, newItem.getId());
        values.put(KEY_NAME, newItem.getName()); // item Name
        values.put(KEY_QUANTITY, newItem.getQuantity()); // QUANTITY
        values.put(KEY_LIST, newItem.getListName()); // list name
        // Inserting Row
        try{
            db.insert(TABLE_NAME, null, values);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        db.close(); // Closing database connection
    }

    // Getting single contact
    ItemDetails getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                KEY_NAME, KEY_QUANTITY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ItemDetails retItem = new ItemDetails(cursor.getString(1),Integer.parseInt(cursor.getString(0)),
                 Integer.parseInt(cursor.getString(2)),cursor.getString(3));
        // return contact
        return retItem;
    }

    // Getting All Contacts
    public List<ItemDetails> getAllContacts() {
        List<ItemDetails> contactList = new ArrayList<ItemDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ItemDetails item = new ItemDetails();
                    item.setName(cursor.getString(1));
                    item.setQuantity(Integer.parseInt(cursor.getString(2)));
                    item.setListName(cursor.getString(3));
                    item.setID(Integer.parseInt(cursor.getString(0)));
                    // Adding contact to list
                    contactList.add(item);
                } while (cursor.moveToNext());
        }}
        catch (SQLException e){e.printStackTrace();}

        // return contact list
        return contactList;
    }


    public List<ItemDetails> getAdaptList(String listName) {
        List<ItemDetails> contactList = new ArrayList<ItemDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +"WERE"+KEY_LIST+"="+listName;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemDetails item = new ItemDetails();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setQuantity(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                contactList.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(ItemDetails newItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, newItem.getName());
        values.put(KEY_QUANTITY, newItem.getQuantity());
          // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(newItem.getId()) });
    }

    // Deleting single contact
    public void deleteItem(ItemDetails item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    // Deleting single contact
    public void deleteList(String listToRemove) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_LIST + " = ?",new String[] { listToRemove});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public List<String> getListNames() {
        String distinctQuery="SELECT DISTINCT "+KEY_LIST+" FROM "+ TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        try{
           Cursor cursor = db.rawQuery(distinctQuery, null);
           List<String>array = new ArrayList<String>();
           while(cursor.moveToNext()){
               String name = cursor.getString(0);
               array.add(name);
           }
           return array;
       }
       catch (Exception e){
           e.printStackTrace();
       }
    return null;
    }
}
