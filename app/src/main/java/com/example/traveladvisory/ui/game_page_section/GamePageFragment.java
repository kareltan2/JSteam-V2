package com.example.traveladvisory.ui.game_page_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveladvisory.adapter.GamePageAdapter;
import com.example.traveladvisory.databinding.FragmentGamePageBinding;
import com.example.traveladvisory.helper.PlaceHelper;

import java.util.Objects;

public class GamePageFragment extends Fragment {

    private PlaceHelper placeHelper;

    private FragmentGamePageBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       new ViewModelProvider(this).get(GamePageModel.class);

        binding = FragmentGamePageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        placeHelper = new PlaceHelper(getActivity());
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

        placeHelper.open();
        recyclerViewGamesPageList.setAdapter(new GamePageAdapter(binding.getRoot().getContext(), placeHelper.findAllPlace()));
        placeHelper.close();
        recyclerViewGamesPageList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        Objects.requireNonNull(getActivity()).getIntent().putExtra("username", getActivity().getIntent().getStringExtra("username"));
    }
}