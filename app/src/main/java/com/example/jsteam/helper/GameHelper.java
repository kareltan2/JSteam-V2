package com.example.jsteam.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.jsteam.model.dao.Game;

import java.util.Vector;

/**
 * @author kareltan
 */
public class GameHelper {

    private Context context;

    private DatabaseHelper helper;

    private SQLiteDatabase database;

    public GameHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public Vector<Game> findAllGame(){
        Vector<Game> games = new Vector<>();
        String query = "SELECT * FROM Game";
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

                games.add(new Game(tempId, tempName, tempGenre, tempRating, tempPrice, tempImage, tempDescription));

                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return games;
    }

    public void insertGame(Game game){
        String queryInsertGame = "INSERT INTO Game VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statementGame = database.compileStatement(queryInsertGame);
        statementGame.bindString(2, game.getName());
        statementGame.bindString(3, game.getGenre());
        statementGame.bindDouble(4, game.getRating());
        statementGame.bindString(5, game.getPrice());
        statementGame.bindString(6, game.getImage());
        statementGame.bindString(7, game.getDescription());
        statementGame.executeInsert();
    }

    public Game findGame(int gameId){
        String query = "SELECT * FROM Game WHERE id = '" + gameId + "'";
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

        return new Game(id, name, genre, rating, price, image, description);
    }
}
