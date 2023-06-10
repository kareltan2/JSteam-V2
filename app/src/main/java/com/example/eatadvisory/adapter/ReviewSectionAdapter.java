package com.example.eatadvisory.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatadvisory.R;
import com.example.eatadvisory.activity.core.GamesDetailActivity;
import com.example.eatadvisory.activity.popup.PopUpConfirmationActivity;
import com.example.eatadvisory.activity.popup.PopUpEditReviewActivity;
import com.example.eatadvisory.helper.FoodHelper;
import com.example.eatadvisory.helper.UserHelper;
import com.example.eatadvisory.model.dao.Food;
import com.example.eatadvisory.model.dao.Review;
import com.example.eatadvisory.support.ImageLoaderTask;

import java.util.Vector;

/**
 * @author kareltan
 */
public class ReviewSectionAdapter extends RecyclerView.Adapter<ReviewSectionAdapter.ViewHolder> {

    private Context context;

    private Vector<Review> reviews;

    public ReviewSectionAdapter(Context context, Vector<Review> reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserHelper userHelper = new UserHelper(context.getApplicationContext());
        FoodHelper foodHelper = new FoodHelper(context.getApplicationContext());
        userHelper.open();
        foodHelper.open();
        Review review = reviews.get(position);

        Food food = foodHelper.findGame(review.getFoodId());
        String username = userHelper.findUserByUserId(review.getUserId()).getUsername();
        userHelper.close();
        foodHelper.close();

        holder.tvGameName.setText(food.getName());
        holder.tvUsername.setText(username);
        holder.tvReview.setText(review.getComment());

        new ImageLoaderTask(holder.ivGameImage).execute(food.getImage());


        holder.buttonDeleteReview.setOnClickListener(view -> {
            PopUpConfirmationActivity popUpConfirmation = new PopUpConfirmationActivity();
            popUpConfirmation.popUpConfirmation(view, context, review, username);
        });

        holder.buttonUpdateReview.setOnClickListener(view -> {
            PopUpEditReviewActivity popUpClass = new PopUpEditReviewActivity();
            popUpClass.popUpEditReview(view, context, review, username);
        });

        holder.cvReviewSectionList.setOnClickListener(view -> {
            Intent intent = new Intent(context, GamesDetailActivity.class);

            foodHelper.open();
            foodHelper.close();

            intent.putExtra("gameName", food.getName());
            intent.putExtra("gameGenre", food.getGenre());
            intent.putExtra("gamePrice", food.getPrice());
            intent.putExtra("gameRating", food.getRating().toString());
            intent.putExtra("gameDescription", food.getDescription());
            intent.putExtra("username", ((Activity) context).getIntent().getStringExtra("username"));

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cvReviewSectionList;
        protected TextView tvGameName, tvUsername, tvReview;
        protected ImageView ivGameImage;
        protected Button buttonUpdateReview, buttonDeleteReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvReviewSectionList = itemView.findViewById(R.id.cv_review_section_list);
            tvGameName = itemView.findViewById(R.id.tv_title_game_on_review_section);
            tvUsername = itemView.findViewById(R.id.tv_username_reviewer_on_review_section);
            tvReview = itemView.findViewById(R.id.tv_review_on_review_section);
            ivGameImage = itemView.findViewById(R.id.iv_game_on_review_section);
            buttonUpdateReview = itemView.findViewById(R.id.button_update_review_review_section);
            buttonDeleteReview = itemView.findViewById(R.id.button_delete_review_review_section);
        }
    }
}
