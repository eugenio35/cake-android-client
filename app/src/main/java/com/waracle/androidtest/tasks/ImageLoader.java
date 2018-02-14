package com.waracle.androidtest.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by emancebo on 2/13/18.
 */

public class GetCakeImage extends AsyncTask<Object, Void, Bitmap> {

    private static final String TAG = GetCakeImage.class.getSimpleName();
    private String cakeImageUrl;

    public interface AsyncResponse {
        void processFinish(Bitmap output);
    }

    public AsyncResponse delegate = null;

    public GetCakeImage(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        cakeImageUrl = String.valueOf(params[0]);

        try{

        }catch () {

        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        delegate.processFinish(result);
    }
}
