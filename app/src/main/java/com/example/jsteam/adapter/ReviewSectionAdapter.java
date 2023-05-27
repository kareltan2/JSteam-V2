package com.example.jsteam.adapter;

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

import com.example.jsteam.activity.core.GamesDetailActivity;
import com.example.jsteam.activity.popup.PopUpConfirmationActivity;
import com.example.jsteam.activity.popup.PopUpEditReviewActivity;
import com.example.jsteam.R;
import com.example.jsteam.model.DatabaseConfiguration;
import com.example.jsteam.model.Game;
import com.example.jsteam.model.Review;

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
        Review review = reviews.get(position);

        holder.tvGameName.setText(review.getGameName());
        holder.tvUsername.setText(review.getUsername());
        holder.tvReview.setText(review.getContent());

        holder.buttonDeleteReview.setOnClickListener(view -> {
            String reviewContent = holder.tvReview.getText().toString();
            PopUpConfirmationActivity popUpConfirmation = new PopUpConfirmationActivity();
            popUpConfirmation.popUpConfirmation(view, context, reviewContent, review.getUsername(), review.getGameName());
        });

        holder.buttonUpdateReview.setOnClickListener(view -> {
            PopUpEditReviewActivity popUpClass = new PopUpEditReviewActivity();
            popUpClass.popUpEditReview(view, context, review.getUsername(), review.getGameName(), review.getContent());
        });

        holder.cvReviewSectionList.setOnClickListener(view -> {
            Intent intent = new Intent(context, GamesDetailActivity.class);

            int index = DatabaseConfiguration.findIndexGameByName(review.getGameName());
            Game game = DatabaseConfiguration.games.get(index);

            intent.putExtra("gameName", game.getName());
            intent.putExtra("gameGenre", game.getGenre());
            intent.putExtra("gamePrice", game.getPrice().toString());
            intent.putExtra("gameRating", game.getRating().toString());
            intent.putExtra("gameDescription", game.getDescription());
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
