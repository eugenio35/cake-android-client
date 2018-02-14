package com.waracle.androidtest.tasks;

import android.os.AsyncTask;
import com.waracle.androidtest.model.Cake;
import java.util.List;

/**
 * Created by emancebo on 2/13/18.
 */

public class getCakeListTask extends AsyncTask<Object, Void, List<Cake>> {

    private List<Cake> cakeList = null;

    public interface cakeAsyncResponse {
        void processFinish(List<Cake> output);
    }

    public cakeAsyncResponse delegate = null;

    public getCakeListTask(cakeAsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<Cake> doInBackground(Object... params) {

        return null;
    }

    @Override
    protected void onPostExecute(List<Cake> result) {
        delegate.processFinish(result);
    }
}