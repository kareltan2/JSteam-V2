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
public class WishSectionAdapter extends RecyclerView.Adapter<WishSectionAdapter.ViewHolder> {

    private Context context;

    private Vector<Wish> wishes;

    public WishSectionAdapter(Context context, Vector<Wish> wishes) {
        this.wishes = wishes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wish_section, parent, false);
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

        holder.tvPlaceName.setText(place.getName());
        holder.tvUsername.setText(username);

        new ImageLoaderTask(holder.ivPlaceImage).execute(place.getImage());

        holder.buttonDeleteWish.setOnClickListener(view -> {
            PopUpConfirmationActivity popUpConfirmation = new PopUpConfirmationActivity();
            popUpConfirmation.popUpConfirmation(view, context, wish, username);
        });
    }

    @Override
    public int getItemCount() {
        return wishes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cvWishSectionList;
        protected TextView tvPlaceName, tvUsername;
        protected ImageView ivPlaceImage;
        protected Button buttonDeleteWish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvWishSectionList = itemView.findViewById(R.id.cv_wish_section_list);
            tvPlaceName = itemView.findViewById(R.id.tv_title_place_on_wish_section);
            tvUsername = itemView.findViewById(R.id.tv_username_wisher_on_wish_section);
            ivPlaceImage = itemView.findViewById(R.id.iv_place_on_wish_section);
            buttonDeleteWish = itemView.findViewById(R.id.button_delete_wish_wish_section);
        }
    }
}
