package com.waracle.androidtest.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.util.StreamUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emancebo on 2/13/18.
 */

public class GetCakes extends AsyncTask<Object, Void, List<Cake>> {

    private static final String TAG = GetCakes.class.getSimpleName();
    private List<Cake> cakeList = new ArrayList<>();
    private String urlEnpoint;

    public interface AsyncResponse {
        void getCakesProcessFinish(List<Cake> output);
    }

    public AsyncResponse delegate = null;

    public GetCakes(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<Cake> doInBackground(Object... params) {
        urlEnpoint = String.valueOf(params[0]);

        try {
            JSONArray array = loadData();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                Cake cake = new Cake(object.getString("title"), object.getString("desc"), object.getString("image"));
                cakeList.add(cake);
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return cakeList;
    }

    private JSONArray loadData() throws IOException, JSONException {
        URL url = new URL(urlEnpoint);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Can you think of a way to improve the performance of loading data
            // using HTTP headers???

            // Using Cache-Control and max-age we can avoid doing re-requests.


            // Also, Do you trust any utils thrown your way????
            // I usually like to test the performance before making a decision, I also like to hear from other devs using the same 3rd party lib

            byte[] bytes = StreamUtils.readUnknownFully(in);

            // Read in charset of HTTP content.
            String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            String jsonText = new String(bytes, charset);

            // Read string as JSON.
            return new JSONArray(jsonText);
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }

    @Override
    protected void onPostExecute(List<Cake> result) {
        delegate.getCakesProcessFinish(result);
    }
}