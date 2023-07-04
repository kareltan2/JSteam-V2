package com.example.traveladvisory.ui.wish_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveladvisory.adapter.WishSectionAdapter;
import com.example.traveladvisory.databinding.FragmentWishSectionBinding;
import com.example.traveladvisory.helper.WishHelper;
import com.example.traveladvisory.helper.UserHelper;

import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

public class WishSectionFragment extends Fragment {

    private WishHelper wishHelper;

    private UserHelper userHelper;

    private FragmentWishSectionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        new ViewModelProvider(this).get(WishSectionModel.class);

        binding = FragmentWishSectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        wishHelper = new WishHelper(getActivity());
        userHelper = new UserHelper(getActivity());
        init();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init(){
        final RecyclerView recyclerViewWishSectionList = binding.rvWishSectionList;

        userHelper.open();
        wishHelper.open();
        recyclerViewWishSectionList.setAdapter(new WishSectionAdapter(binding.getRoot().getContext(),
                wishHelper.findAllWish().stream()
                        .filter(wish ->
                                wish.getUserId().equals(
                                        userHelper.findUser(Objects.requireNonNull(
                                                getActivity()).getIntent().getStringExtra("username")).getId()
                                )
                        )
                        .collect(Collectors.toCollection(Vector::new))
        ));
        userHelper.close();
        wishHelper.close();
        recyclerViewWishSectionList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        Objects.requireNonNull(getActivity()).getIntent().putExtra("username", getActivity().getIntent().getStringExtra("username"));
    }
}