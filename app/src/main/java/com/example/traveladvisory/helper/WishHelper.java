package com.example.traveladvisory.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.traveladvisory.model.dao.Wish;

import java.util.Vector;

/**
 * @author kareltan
 */
public class WishHelper {

    private Context context;

    private DatabaseHelper helper;

    private SQLiteDatabase database;

    public WishHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public Vector<Wish> findAllWish(){
        Vector<Wish> wishes = new Vector<>();
        String query = "SELECT * FROM Wish";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{
                Integer tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                Integer userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                Integer placeId = cursor.getInt(cursor.getColumnIndexOrThrow("place_id"));

                wishes.add(new Wish(tempId, userId, placeId));

                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return wishes;
    }

    public void insertWish(Wish wish){
        String queryInsertWish = "INSERT INTO Wish VALUES (?, ?, ?)";
        SQLiteStatement statementWish = database.compileStatement(queryInsertWish);
        statementWish.bindLong(2, wish.getUserId());
        statementWish.bindLong(3, wish.getPlaceId());
        statementWish.executeInsert();
    }

    public void deleteWish(Wish wish){
        String query = "DELETE FROM Wish WHERE id = '" + wish.getId() + "'";
        database.execSQL(query);
    }

    public boolean isExistWish(Integer userId, Integer placeId){
        String query = "SELECT * FROM Wish WHERE user_id = '" + userId + "' AND place_id = '" + placeId + "' ";

        try (Cursor cursor = database.rawQuery(query, null)) {
            return cursor != null && cursor.getCount() > 0;
        }
    }

}
