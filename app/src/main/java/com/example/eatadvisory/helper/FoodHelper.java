package com.example.eatadvisory.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.eatadvisory.model.dao.Food;

import java.util.Vector;

/**
 * @author kareltan
 */
public class FoodHelper {

    private Context context;

    private DatabaseHelper helper;

    private SQLiteDatabase database;

    public FoodHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public Vector<Food> findAllGame(){
        Vector<Food> foods = new Vector<>();
        String query = "SELECT * FROM Food";
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

                foods.add(new Food(tempId, tempName, tempGenre, tempRating, tempPrice, tempImage, tempDescription));

                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return foods;
    }

    public void insertGame(Food food){
        String queryInsertGame = "INSERT INTO Food VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statementGame = database.compileStatement(queryInsertGame);
        statementGame.bindString(2, food.getName());
        statementGame.bindString(3, food.getGenre());
        statementGame.bindDouble(4, food.getRating());
        statementGame.bindString(5, food.getPrice());
        statementGame.bindString(6, food.getImage());
        statementGame.bindString(7, food.getDescription());
        statementGame.executeInsert();
    }

    public Food findGame(int gameId){
        String query = "SELECT * FROM Food WHERE id = '" + gameId + "'";
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

        return new Food(id, name, genre, rating, price, image, description);
    }
}
