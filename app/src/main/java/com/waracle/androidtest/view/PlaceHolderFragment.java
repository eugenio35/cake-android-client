package com.waracle.androidtest.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.waracle.androidtest.R;
import com.waracle.androidtest.controller.CakeArrayAdapter;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.tasks.GetCakes;
import com.waracle.androidtest.tasks.ImageLoader;
import com.waracle.androidtest.util.ImageCache;

import java.util.List;

/**
 * Created by emancebo on 2/13/18.
 */

public class PlaceHolderFragment extends ListFragment implements GetCakes.AsyncResponse, ImageLoader.AsyncResponse {

    private static final String TAG = PlaceHolderFragment.class.getSimpleName();

    private ListView mListView;
    private ProgressDialog progressDialog;
    private List<Cake> cakes;

    public PlaceHolderFragment() { /**/ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = rootView.findViewById(android.R.id.list);
        progressDialog = new ProgressDialog(getContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        ImageCache.getInstance().init(cacheSize);

        openDialog(getString(R.string.dialog_title));
        new GetCakes(this).execute(getString(R.string.cake_endpoint_url));
    }

    private void openDialog(String title) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(getString(R.string.dialog_body));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    private void setUpListView(List<Cake> cakes) {
        CakeArrayAdapter adapter = new CakeArrayAdapter(getActivity(), cakes);
        mListView.setAdapter(adapter);
    }

    @Override
    public void getCakesProcessFinish(List<Cake> cakesFromServer) {
        this.cakes = cakesFromServer;
        new ImageLoader(this).execute(cakesFromServer);
    }

    @Override
    public void imageLoaderProcessFinish() {
        progressDialog.dismiss();
        if(!cakes.isEmpty()){
            setUpListView(cakes);
        } else {
            Toast.makeText(getContext(), getString(R.string.cake_load_error), Toast.LENGTH_LONG).show();
            Log.e(TAG, getString(R.string.cake_load_error));
        }
    }
}