package com.example.eatadvisory.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.eatadvisory.model.dao.Review;

import java.util.Vector;

/**
 * @author kareltan
 */
public class ReviewHelper {

    private Context context;

    private DatabaseHelper helper;

    private SQLiteDatabase database;

    public ReviewHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        helper.close();
    }

    public Vector<Review> findAllReview(){
        Vector<Review> reviews = new Vector<>();
        String query = "SELECT * FROM Review";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{
                Integer tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                Integer userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                Integer gameId = cursor.getInt(cursor.getColumnIndexOrThrow("food_id"));
                String comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));

                reviews.add(new Review(tempId, userId, gameId, comment));

                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return reviews;
    }

    public void insertReview(Review review){
        String queryInsertReview = "INSERT INTO Review VALUES (?, ?, ?, ?)";
        SQLiteStatement statementReview = database.compileStatement(queryInsertReview);
        statementReview.bindLong(2, review.getUserId());
        statementReview.bindLong(3, review.getFoodId());
        statementReview.bindString(4, review.getComment());
        statementReview.executeInsert();
    }

    public void updateReview(Review review, String newComment){
        String query = "UPDATE Review SET comment = '" + newComment + "' WHERE id = '" + review.getId() + "'";
        database.execSQL(query);
    }

    public void deleteReview(Review review){
        String query = "DELETE FROM Review WHERE id = '" + review.getId() + "'";
        database.execSQL(query);
    }

}
