package com.example.jsteam.activity.popup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsteam.R;
import com.example.jsteam.activity.core.ReviewSectionActivity;
import com.example.jsteam.activity.prelogin.MainActivity;
import com.example.jsteam.helper.ReviewHelper;
import com.example.jsteam.model.dao.Review;

public class PopUpConfirmationActivity extends AppCompatActivity {

    private ReviewHelper reviewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_confirmation);
    }

    public void popUpConfirmation(final View view, Context context, Review review, String newComment, String username) {
        View popupShow = initPopUpShow(view);
        PopupWindow popupWindow = initPopUpWindow(popupShow, view);

        Button buttonYes = popupShow.findViewById(R.id.button_yes_confirm_popup);
        Button buttonNo = popupShow.findViewById(R.id.button_no_confirm_popup);

        buttonYes.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReviewSectionActivity.class);

            reviewHelper = new ReviewHelper(context);
            reviewHelper.open();
            reviewHelper.updateReview(review, newComment);
            reviewHelper.close();
            Toast.makeText(view.getContext(), "Review Content Updated", Toast.LENGTH_SHORT).show();

            popupWindow.dismiss();
            intent.putExtra("username", username);
            context.startActivity(intent);
        });

        buttonNo.setOnClickListener(v -> popupDismiss(popupWindow));

        popupShow.setOnTouchListener((v, event) -> {
            popupDismiss(popupWindow);
            return true;
        });
    }

    public void popUpConfirmation(final View view, Context context, Review review, String username) {
        View popupShow = initPopUpShow(view);
        PopupWindow popupWindow = initPopUpWindow(popupShow, view);

        Button buttonYes = popupShow.findViewById(R.id.button_yes_confirm_popup);
        Button buttonNo = popupShow.findViewById(R.id.button_no_confirm_popup);

        buttonYes.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReviewSectionActivity.class);

            reviewHelper = new ReviewHelper(context);
            reviewHelper.open();
            reviewHelper.deleteReview(review);
            reviewHelper.close();
            intent.putExtra("username", username);

            context.startActivity(intent);
        });

        buttonNo.setOnClickListener(v -> popupDismiss(popupWindow));

        popupShow.setOnTouchListener((v, event) -> {
            popupDismiss(popupWindow);
            return true;
        });
    }

    public void popUpConfirmation(final View view, Context context) {
        View popupShow = initPopUpShow(view);
        PopupWindow popupWindow = initPopUpWindow(popupShow, view);

        Button buttonYes = popupShow.findViewById(R.id.button_yes_confirm_popup);
        Button buttonNo = popupShow.findViewById(R.id.button_no_confirm_popup);

        buttonYes.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });

        buttonNo.setOnClickListener(v -> popupDismiss(popupWindow));

        popupShow.setOnTouchListener((v, event) -> {
            popupDismiss(popupWindow);
            return true;
        });
    }

    private View initPopUpShow(View view){
        LayoutInflater inflater = (LayoutInflater) view.getContext()
                .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.activity_pop_up_confirmation, null);
    }

    private PopupWindow initPopUpWindow(View popupShow, View view){
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupShow, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        return popupWindow;
    }

    private void popupDismiss(PopupWindow popupWindow) {
        popupWindow.dismiss();
    }

}