package com.example.eatadvisory.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.eatadvisory.model.dao.User;

/**
 * @author kareltan
 */
public class UserHelper {

    private Context context;

    private DatabaseHelper helper;

    private SQLiteDatabase database;

    public UserHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public User findUser(String username) {
        String query = "SELECT * FROM User WHERE username = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String usernameDatabase = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String region = cursor.getString(cursor.getColumnIndexOrThrow("region"));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));

            cursor.close();

            return new User(id, usernameDatabase, password, email, region, phoneNumber);
        } else {
            cursor.close();
            return null; // or handle the case when user is not found
        }
    }


    public User findUserByUserId(Integer userId){
        String query = "SELECT * FROM User WHERE id = '" + userId + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String usernameDatabase = cursor.getString(cursor.getColumnIndexOrThrow("username"));
        String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        String region = cursor.getString(cursor.getColumnIndexOrThrow("region"));
        String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));

        cursor.close();

        return new User(id, usernameDatabase, password, email, region, phoneNumber);
    }

    public void insertUser(User user) {
        String queryInsertUser = "INSERT INTO User (email, username, password, region, phone_number) VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement statementUser = database.compileStatement(queryInsertUser);
        statementUser.bindString(1, user.getEmail());
        statementUser.bindString(2, user.getUsername());
        statementUser.bindString(3, user.getPassword());
        statementUser.bindString(4, user.getRegion());
        statementUser.bindString(5, user.getPhoneNumber());
        statementUser.executeInsert();
    }


    public boolean isExistUsername(String username){
        String query = "SELECT * FROM User WHERE username = '" + username + "'";

        try (Cursor cursor = database.rawQuery(query, null)) {
            return cursor != null && cursor.getCount() > 0;
        }
    }
}
