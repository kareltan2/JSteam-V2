package com.example.jsteam.ui.review_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsteam.adapter.ReviewSectionAdapter;
import com.example.jsteam.databinding.FragmentReviewSectionBinding;
import com.example.jsteam.helper.ReviewHelper;
import com.example.jsteam.helper.UserHelper;

import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

public class ReviewSectionFragment extends Fragment {

    private ReviewHelper reviewHelper;

    private UserHelper userHelper;

    private FragmentReviewSectionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        new ViewModelProvider(this).get(ReviewSectionModel.class);

        binding = FragmentReviewSectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        reviewHelper = new ReviewHelper(getActivity());
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
        final RecyclerView recyclerViewReviewSectionList = binding.rvReviewSectionList;

        userHelper.open();
        reviewHelper.open();
        recyclerViewReviewSectionList.setAdapter(new ReviewSectionAdapter(binding.getRoot().getContext(),
                reviewHelper.findAllReview().stream()
                        .filter(review ->
                                review.getUserId().equals(
                                        userHelper.findUser(Objects.requireNonNull(
                                                getActivity()).getIntent().getStringExtra("username")).getId()
                                )
                        )
                        .collect(Collectors.toCollection(Vector::new))
        ));
        userHelper.close();
        reviewHelper.close();
        recyclerViewReviewSectionList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        Objects.requireNonNull(getActivity()).getIntent().putExtra("username", getActivity().getIntent().getStringExtra("username"));
    }
}