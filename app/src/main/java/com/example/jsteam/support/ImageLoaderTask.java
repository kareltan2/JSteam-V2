package com.example.jsteam.support;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.jsteam.R;

import java.io.InputStream;
import java.net.URL;

/**
 * @author kareltan
 */
public class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    public ImageLoaderTask(ImageView imageView) {
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        String imageUrl = urls[0];
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Image", e.toString());
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            imageView.setImageBitmap(result);
        } else {
            // Handle the case when image loading fails
            imageView.setImageResource(R.drawable.among_us_profile_game);
        }
    }
}
