package com.example.jsteam.activity.popup;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsteam.R;

public class PopUpEditReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review_popup);
    }

    public void popUpEditReview(final View view, Context context, String username, String gameName, String oldContent) {
        LayoutInflater inflater = (LayoutInflater) view.getContext()
                .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupShow = inflater.inflate(R.layout.activity_edit_review_popup, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupShow, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        Button buttonEditReviewPopup = popupShow.findViewById(R.id.button_edit_review_popup);

        buttonEditReviewPopup.setOnClickListener(v -> {
            EditText newContent = popupShow.findViewById(R.id.pt_edit_review_pop_up);

            if(!String.valueOf(newContent.getText()).isEmpty()){
                popupWindow.dismiss();
                PopUpConfirmationActivity popUpConfirmation = new PopUpConfirmationActivity();
                popUpConfirmation.popUpConfirmation(view, context, username, gameName, oldContent, String.valueOf(newContent.getText()));
            } else {
                Toast.makeText(view.getContext(), "The content must not be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        popupShow.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

}