package com.example.traveladvisory.ui.place_page_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveladvisory.adapter.PlacePageAdapter;
import com.example.traveladvisory.databinding.FragmentPlacePageBinding;
import com.example.traveladvisory.helper.PlaceHelper;

import java.util.Objects;

public class PlacePageFragment extends Fragment {

    private PlaceHelper placeHelper;

    private FragmentPlacePageBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       new ViewModelProvider(this).get(PlacePageModel.class);

        binding = FragmentPlacePageBinding.inflate(inflater, container, false);
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
        final RecyclerView recyclerViewPlacesPageList = binding.rvPlacePageList;

        placeHelper.open();
        recyclerViewPlacesPageList.setAdapter(new PlacePageAdapter(binding.getRoot().getContext(), placeHelper.findAllPlace()));
        placeHelper.close();
        recyclerViewPlacesPageList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        Objects.requireNonNull(getActivity()).getIntent().putExtra("username", getActivity().getIntent().getStringExtra("username"));
    }
}