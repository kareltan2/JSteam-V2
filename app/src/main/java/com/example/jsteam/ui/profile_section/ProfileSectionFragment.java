package com.example.jsteam.ui.profile_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jsteam.activity.popup.PopUpConfirmationActivity;
import com.example.jsteam.databinding.FragmentProfileSectionBinding;
import com.example.jsteam.model.DatabaseConfiguration;
import com.example.jsteam.model.User;

import java.util.Objects;

public class ProfileSectionFragment extends Fragment {

    private FragmentProfileSectionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        new ViewModelProvider(this).get(ProfileSectionModel.class);

        binding = FragmentProfileSectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init() {
        TextView usernameProfilePage = binding.tvUsernameOutputProfile;
        TextView emailProfilePage = binding.tvEmailOutputProfile;
        TextView regionProfilePage = binding.tvRegionOutputProfile;
        TextView phoneProfilePage = binding.tvPhoneOutputProfile;
        Button logoutButton = binding.buttonLogoutProfilePage;

        User userLoggedIn = DatabaseConfiguration.users.get(DatabaseConfiguration.findIndexUser(requireActivity().getIntent().getStringExtra("username")));

        String usernameText = userLoggedIn.getUsername();
        String emailText = userLoggedIn.getEmail();
        String regionText = userLoggedIn.getRegion();
        String phoneText = userLoggedIn.getPhoneNumber();

        usernameProfilePage.setText("Hello, " + usernameText + "!");
        emailProfilePage.setText(emailText);
        regionProfilePage.setText(regionText);
        phoneProfilePage.setText(phoneText);

        logoutButton.setOnClickListener(view -> {
            PopUpConfirmationActivity popUpConfirmation = new PopUpConfirmationActivity();
            popUpConfirmation.popUpConfirmation(view, getActivity());
        });

        Objects.requireNonNull(getActivity()).getIntent().putExtra("username", getActivity().getIntent().getStringExtra("username"));
    }
}