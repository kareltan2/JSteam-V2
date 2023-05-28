package com.example.jsteam.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.jsteam.model.rest.Game;

import java.util.List;

/**
 * @author kareltan
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private List<Game> games;

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "JSteam-V2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateUser = "Create table User (id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR(100), username VARCHAR(100) UNIQUE, password VARCHAR(100), region VARCHAR(100), phone_number VARCHAR(12))";
        sqLiteDatabase.execSQL(queryCreateUser);

        String queryInsertUser1 = "INSERT INTO User (email, username, password, region, phone_number) VALUES ('karel.tan@gmail.com', 'kareltan', 'karel', 'Indonesia', '0832142132')";
        String queryInsertUser2 = "INSERT INTO User (email, username, password, region, phone_number) VALUES ('niclauss.lumoring@gmail.com', 'niclauz', 'niclauss', 'Indonesia', '0832142132')";
        String queryInsertUser3 = "INSERT INTO User (email, username, password, region, phone_number) VALUES ('jason.tong@gmail.com', 'jtong', 'jason', 'Indonesia', '0832142132')";
        sqLiteDatabase.execSQL(queryInsertUser1);
        sqLiteDatabase.execSQL(queryInsertUser2);
        sqLiteDatabase.execSQL(queryInsertUser3);

        String queryCreateGame = "Create table Game (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(100), genre VARCHAR(100), rating FLOAT(10), price VARCHAR(100), image VARCHAR(255), description VARCHAR(255))";
        sqLiteDatabase.execSQL(queryCreateGame);

        String queryCreateReview = "Create table Review (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER(10), game_id INTEGER(100), comment VARCHAR(100), FOREIGN KEY (user_id) REFERENCES User(id), FOREIGN KEY (game_id) REFERENCES Game(id))";
        sqLiteDatabase.execSQL(queryCreateReview);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists User");
        sqLiteDatabase.execSQL("Drop table if exists Game");
        sqLiteDatabase.execSQL("Drop table if exists Review");
        onCreate(sqLiteDatabase);
    }

}
