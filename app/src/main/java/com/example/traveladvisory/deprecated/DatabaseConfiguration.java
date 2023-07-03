package com.example.traveladvisory.deprecated;

import com.example.traveladvisory.deprecated.model.Game;
import com.example.traveladvisory.deprecated.model.Review;
import com.example.traveladvisory.deprecated.model.User;

import java.util.Vector;

/**
 * @author kareltan
 * @description:
 * deprecated due to migrate to real database connection. this is only support for JSteam-V1
 */
@Deprecated
public class DatabaseConfiguration {

    public static final Vector<User> users = new Vector<User>();

    public static final Vector<Game> games = new Vector<Game>();

    public static final Vector<Review> reviews = new Vector<Review>();


    public static void DatabaseUser(Integer userId, String username, String password, String email, String region, String phoneNumber){
        User user = new User(userId, username, password, email, region, phoneNumber);
        users.add(user);
    }

    public static void DatabaseGame(String name, String genre, Double rating, Integer price, String description){
        Game game = new Game(name, genre, rating, price, description);
        games.add(game);
    }

    public static void DatabaseReview(String gameName, String content, String username){
        Review review = new Review(gameName, content, username);
        reviews.add(review);
    }

    public static void removeDatabaseReviewComment(String reviewComment, String username, String gameName){
        int indexToBeDeleted = findIndexReviewComment(reviewComment, username, gameName);
        reviews.remove(indexToBeDeleted);
    }

    public static void updateDatabaseReviewComment(String username, String gameName, String oldContent, String updatedContent){
        reviews.forEach(review -> {
            if(review.getUsername().equals(username) && review.getGameName().equals(gameName) && review.getContent().equals(oldContent)){
                review.setContent(updatedContent);
            }
        });
    }

    public static Integer findIndexUser(String username){
        int index = -1;

        for (int i = 0 ; i < DatabaseConfiguration.users.size() ; i++){
            if(DatabaseConfiguration.users.get(i).getUsername().equals(username)){
                index = i;
                break;
            }
        }

        return index;
    }

    static int findIndexReviewComment(String reviewComment, String username, String gameName){
        int index = -1;

        for (int i = 0 ; i < DatabaseConfiguration.reviews.size() ; i++){
            if(DatabaseConfiguration.reviews.get(i).getContent().equals(reviewComment) &&
                    DatabaseConfiguration.reviews.get(i).getUsername().equals(username) &&
                    DatabaseConfiguration.reviews.get(i).getGameName().equals(gameName)
            ){
                index = i;
                break;
            }
            else index = -1;
        }

        return index;
    }

    public static int findIndexGameByName(String gameName){
        int index = -1;

        for (int i = 0 ; i < DatabaseConfiguration.games.size() ; i++){
            if(DatabaseConfiguration.games.get(i).getName().equals(gameName)){
                index = i;
                break;
            }
            else index = -1;
        }

        return index;
    }

}
