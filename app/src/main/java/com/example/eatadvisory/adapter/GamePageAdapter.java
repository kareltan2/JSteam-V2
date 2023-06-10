package com.example.eatadvisory.adapter;

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

import com.example.eatadvisory.R;
import com.example.eatadvisory.activity.core.GamesDetailActivity;
import com.example.eatadvisory.model.dao.Food;
import com.example.eatadvisory.support.ImageLoaderTask;

import java.util.Vector;

/**
 * @author kareltan
 */
public class GamePageAdapter extends RecyclerView.Adapter<GamePageAdapter.ViewHolder> {
    private Context context;
    private Vector<Food> gamesListVector;

    public GamePageAdapter(Context context, Vector<Food> gamesListVector) {
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
        Food food = gamesListVector.get(position);

        holder.tvGameName.setText(food.getName());
        holder.tvGameGenre.setText(food.getGenre());
        holder.tvGamePrice.setText(food.getPrice());

        new ImageLoaderTask(holder.ivGameImage).execute(food.getImage());

        holder.cvGamePageList.setOnClickListener(view -> {
            Intent intent = new Intent(context, GamesDetailActivity.class);
            intent.putExtra("gameId", food.getId());
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
