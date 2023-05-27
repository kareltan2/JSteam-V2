package com.example.jsteam.adapter;

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

import com.example.jsteam.activity.core.GamesDetailActivity;
import com.example.jsteam.R;
import com.example.jsteam.model.Game;

import java.util.Vector;

/**
 * @author kareltan
 */
public class GamePageAdapter extends RecyclerView.Adapter<GamePageAdapter.ViewHolder> {
    private Context context;
    private Vector<Game> gamesListVector;

    public GamePageAdapter(Context context, Vector<Game> gamesListVector) {
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
        Game game = gamesListVector.get(position);

        holder.tvGameName.setText(game.getName());
        holder.tvGameGenre.setText(game.getGenre());
        holder.tvGamePrice.setText("Rp." + String.valueOf(game.getPrice()));
        holder.cvGamePageList.setOnClickListener(view -> {
            Intent intent = new Intent(context, GamesDetailActivity.class);
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
