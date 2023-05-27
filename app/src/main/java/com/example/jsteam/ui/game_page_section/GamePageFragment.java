package com.example.jsteam.ui.game_page_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsteam.adapter.GamePageAdapter;
import com.example.jsteam.databinding.FragmentGamePageBinding;
import com.example.jsteam.model.DatabaseConfiguration;
import com.example.jsteam.model.Game;

import java.util.Objects;
import java.util.Vector;

public class GamePageFragment extends Fragment {

    private FragmentGamePageBinding binding;
    private Vector<Game> gamesListVector = new Vector<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       new ViewModelProvider(this).get(GamePageModel.class);

        binding = FragmentGamePageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init(){
        final RecyclerView recyclerViewGamesPageList = binding.rvGamePageList;

        recyclerViewGamesPageList.setAdapter(new GamePageAdapter(binding.getRoot().getContext(), DatabaseConfiguration.games));
        recyclerViewGamesPageList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        Objects.requireNonNull(getActivity()).getIntent().putExtra("username", getActivity().getIntent().getStringExtra("username"));
    }
}