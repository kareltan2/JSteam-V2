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
import com.example.traveladvisory.activity.core.GamesDetailActivity;
import com.example.traveladvisory.model.dao.Place;
import com.example.traveladvisory.support.ImageLoaderTask;

import java.util.Vector;

/**
 * @author kareltan
 */
public class GamePageAdapter extends RecyclerView.Adapter<GamePageAdapter.ViewHolder> {
    private Context context;
    private Vector<Place> gamesListVector;

    public GamePageAdapter(Context context, Vector<Place> gamesListVector) {
        this.gamesListVector = gamesListVector;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = gamesListVector.get(position);

        holder.tvGameName.setText(place.getName());
        holder.tvGameGenre.setText(place.getGenre());
        holder.tvGamePrice.setText(place.getPrice());

        new ImageLoaderTask(holder.ivGameImage).execute(place.getImage());

        holder.cvGamePageList.setOnClickListener(view -> {
            Intent intent = new Intent(context, GamesDetailActivity.class);
            intent.putExtra("placeId", place.getId());
            intent.putExtra("username", ((Activity) context).getIntent().getStringExtra("username"));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gamesListVector.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cvGamePageList;
        protected TextView tvGameName, tvGameGenre, tvGamePrice;
        protected ImageView ivGameImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvGamePageList = itemView.findViewById(R.id.cv_game_page_list);
            tvGameName = itemView.findViewById(R.id.tv_title_game_on_game_page);
            tvGameGenre = itemView.findViewById(R.id.tv_genre_game_on_game_page);
            tvGamePrice = itemView.findViewById(R.id.tv_price_game_on_game_page);
            ivGameImage = itemView.findViewById(R.id.iv_game_on_game_page);
        }
    }
}
