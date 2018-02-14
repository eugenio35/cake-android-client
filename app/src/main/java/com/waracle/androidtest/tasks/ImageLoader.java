package com.waracle.androidtest.tasks;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.util.ImageCache;
import com.waracle.androidtest.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emancebo on 2/13/18.
 */

public class ImageLoader extends AsyncTask<Object, Void, Void> {

    private static final String TAG = ImageLoader.class.getSimpleName();
    private List<Cake> cakeList;

    public interface AsyncResponse {
        void imageLoaderProcessFinish();
    }

    public ImageLoader.AsyncResponse delegate = null;

    public ImageLoader(ImageLoader.AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(Object... params) {
        cakeList = (ArrayList<Cake>) params[0];
        // Can you think of a way to improve loading of bitmaps
        // that have already been loaded previously??

        // - We're going to cache images using LruCache

        ImageCache imageCache = ImageCache.getInstance();
        try {
            for(Cake cake : cakeList){

                String url = cake.getImageUrl();
                if (TextUtils.isEmpty(url)) {
                    throw new InvalidParameterException("URL is empty!");
                }

                Bitmap image = convertToBitmap(loadImageData(url));
                imageCache.addBitmapToMemoryCache(cake.getTitle(), image);
            }


        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    private byte[] loadImageData(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        InputStream inputStream = null;
        try {
            try {
                // Read data from workstation
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                // Read the error from the workstation
                inputStream = connection.getErrorStream();
            }

            // Can you think of a way to make the entire
            // HTTP more efficient using HTTP headers??

            return StreamUtils.readUnknownFully(inputStream);
        } finally {

            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            connection.disconnect();
        }
    }

    private Bitmap convertToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        delegate.imageLoaderProcessFinish();
    }
}