package com.example.notificationlistnerr;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RatingActivity extends Activity {

        private RatingBar ratingBar;
        private TextView txtRatingValue;
        private Button btnSubmit;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.rating_notification);

            addListenerOnRatingBar();
            addListenerOnButton();

        }

        public void addListenerOnRatingBar() {

            ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

            //if rating value is changed,
            //display the current rating value in the result (textview) automatically
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {

                    txtRatingValue.setText(String.valueOf(rating));

                }
            });
        }

        public void addListenerOnButton() {

            ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);

            //if click on me, then display the current rating value.
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RatingActivity.this,
                            String.valueOf("Thanks For your Rating "+ratingBar.getRating()),
                            Toast.LENGTH_SHORT).show();
                }

            });
        }
}
