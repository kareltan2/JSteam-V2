package com.example.traveladvisory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveladvisory.R;
import com.example.traveladvisory.activity.popup.PopUpConfirmationActivity;
import com.example.traveladvisory.helper.PlaceHelper;
import com.example.traveladvisory.helper.UserHelper;
import com.example.traveladvisory.model.dao.Place;
import com.example.traveladvisory.model.dao.Wish;
import com.example.traveladvisory.support.ImageLoaderTask;

import java.util.Vector;

/**
 * @author kareltan
 */
public class ReviewSectionAdapter extends RecyclerView.Adapter<ReviewSectionAdapter.ViewHolder> {

    private Context context;

    private Vector<Wish> wishes;

    public ReviewSectionAdapter(Context context, Vector<Wish> wishes) {
        this.wishes = wishes;
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
        PlaceHelper placeHelper = new PlaceHelper(context.getApplicationContext());
        userHelper.open();
        placeHelper.open();
        Wish wish = wishes.get(position);

        Place place = placeHelper.findPlace(wish.getPlaceId());
        String username = userHelper.findUserByUserId(wish.getUserId()).getUsername();
        userHelper.close();
        placeHelper.close();

        holder.tvGameName.setText(place.getName());
        holder.tvUsername.setText(username);

        new ImageLoaderTask(holder.ivGameImage).execute(place.getImage());

        holder.buttonDeleteReview.setOnClickListener(view -> {
            PopUpConfirmationActivity popUpConfirmation = new PopUpConfirmationActivity();
            popUpConfirmation.popUpConfirmation(view, context, wish, username);
        });
    }

    @Override
    public int getItemCount() {
        return wishes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cvReviewSectionList;
        protected TextView tvGameName, tvUsername;
        protected ImageView ivGameImage;
        protected Button buttonDeleteReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvReviewSectionList = itemView.findViewById(R.id.cv_review_section_list);
            tvGameName = itemView.findViewById(R.id.tv_title_game_on_review_section);
            tvUsername = itemView.findViewById(R.id.tv_username_reviewer_on_review_section);
            ivGameImage = itemView.findViewById(R.id.iv_game_on_review_section);
            buttonDeleteReview = itemView.findViewById(R.id.button_delete_review_review_section);
        }
    }
}
