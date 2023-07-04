package com.example.traveladvisory.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.traveladvisory.model.dao.Place;

import java.util.Vector;

/**
 * @author kareltan
 */
public class PlaceHelper {

    private Context context;

    private DatabaseHelper helper;

    private SQLiteDatabase database;

    public PlaceHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public Vector<Place> findAllPlace(){
        Vector<Place> places = new Vector<>();
        String query = "SELECT * FROM Place";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{
                int tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tempName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
                Double tempRating = cursor.getDouble(cursor.getColumnIndexOrThrow("rating"));
                String tempPrice = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String tempImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                String tempDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                places.add(new Place(tempId, tempName, tempGenre, tempRating, tempPrice, tempImage, tempDescription));

                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return places;
    }

    public void insertPlace(Place place){
        String queryInsertPlace = "INSERT INTO Place VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statementPlace = database.compileStatement(queryInsertPlace);
        statementPlace.bindString(2, place.getName());
        statementPlace.bindString(3, place.getGenre());
        statementPlace.bindDouble(4, place.getRating());
        statementPlace.bindString(5, place.getPrice());
        statementPlace.bindString(6, place.getImage());
        statementPlace.bindString(7, place.getDescription());
        statementPlace.executeInsert();
    }

    public Place findPlace(int placeId){
        String query = "SELECT * FROM Place WHERE id = '" + placeId + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String genre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
        Double rating = cursor.getDouble(cursor.getColumnIndexOrThrow("rating"));
        String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
        String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

        cursor.close();

        return new Place(id, name, genre, rating, price, image, description);
    }
}
