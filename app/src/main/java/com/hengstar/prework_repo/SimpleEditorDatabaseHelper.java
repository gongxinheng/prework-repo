package com.hengstar.prework_repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hengstar.prework_repo.models.ListItem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Practice SQLiteOpenHelper directly to understand how it works
 */
public class SimpleEditorDatabaseHelper extends SQLiteOpenHelper {
    private static SimpleEditorDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "simpleEditorDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_LIST_ITEMS = "list";

    // List Items Table Columns
    private static final String KEY_ITEM_ID = "itemId";
    private static final String KEY_ITEM_VALUE = "itemValue";

    public static synchronized SimpleEditorDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SimpleEditorDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private SimpleEditorDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SIMPLE_EDITOR_TABLE = "CREATE TABLE " + TABLE_LIST_ITEMS +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_ITEM_VALUE + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_SIMPLE_EDITOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_ITEMS);
            onCreate(sqLiteDatabase);
        }
    }

    /**
     * Insert a list item into the database
     * @param value Item value to be inserted
     * @return item id inserted
     */
    public long addItem(String value) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        long id = -1;
        // Use transaction to keep consistency
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_VALUE, value);

            // TODO: It is possible that the key id is existed because of the data inconsistency. Consider deal with the case.
            id = db.insertOrThrow(TABLE_LIST_ITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }

        return id;
    }

    public ArrayList<ListItem> getAllItems() {
        ArrayList<ListItem> items = new ArrayList<>();

        // Assume index alway starts from 0 and no gaps in between
        String POSTS_SELECT_QUERY = String.format("SELECT %s, %s FROM %s ORDER BY %s",
                KEY_ITEM_ID, KEY_ITEM_VALUE, TABLE_LIST_ITEMS, KEY_ITEM_ID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(KEY_ITEM_ID));
                    String value = cursor.getString(cursor.getColumnIndex(KEY_ITEM_VALUE));
                    items.add(new ListItem(id, value));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return items;
    }

    public int updateItem(ListItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_VALUE, item.value);

        // TODO: It is possible that the key id is not existed because of the data inconsistency. Consider deal with the case.
        return db.update(TABLE_LIST_ITEMS, values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(item.id) });
    }

    public void deleteItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_LIST_ITEMS, KEY_ITEM_ID + " = ?", new String[] { String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete item id = " + id);
        } finally {
            db.endTransaction();
        }
    }
}
