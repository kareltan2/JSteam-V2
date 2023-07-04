package com.example.traveladvisory.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveladvisory.R;
import com.example.traveladvisory.activity.core.PlaceDetailActivity;
import com.example.traveladvisory.model.dao.Place;
import com.example.traveladvisory.support.ImageLoaderTask;

import java.util.Vector;

/**
 * @author kareltan
 */
public class PlacePageAdapter extends RecyclerView.Adapter<PlacePageAdapter.ViewHolder> {
    private Context context;
    private Vector<Place> places;

    public PlacePageAdapter(Context context, Vector<Place> places) {
        this.places = places;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = places.get(position);

        holder.tvPlaceName.setText(place.getName());
        holder.tvPlaceGenre.setText(place.getGenre());
        holder.tvPlacePrice.setText(place.getPrice());

        new ImageLoaderTask(holder.ivPlaceImage).execute(place.getImage());

        holder.cvPlacePageList.setOnClickListener(view -> {
            Intent intent = new Intent(context, PlaceDetailActivity.class);
            intent.putExtra("placeId", place.getId());
            intent.putExtra("username", ((Activity) context).getIntent().getStringExtra("username"));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cvPlacePageList;
        protected TextView tvPlaceName, tvPlaceGenre, tvPlacePrice;
        protected ImageView ivPlaceImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvPlacePageList = itemView.findViewById(R.id.cv_place_page_list);
            tvPlaceName = itemView.findViewById(R.id.tv_title_place_on_place_page);
            tvPlaceGenre = itemView.findViewById(R.id.tv_genre_place_on_place_page);
            tvPlacePrice = itemView.findViewById(R.id.tv_price_place_on_place_page);
            ivPlaceImage = itemView.findViewById(R.id.iv_place_on_place_page);
        }
    }
}
