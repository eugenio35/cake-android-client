package com.waracle.androidtest.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.R;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.util.ImageCache;

import java.util.List;

/**
 * Created by emancebo on 2/13/18.
 */

public class CakeArrayAdapter extends ArrayAdapter<Cake> {

    private final Activity appContext;
    private final List<Cake> cakeList;

    public CakeArrayAdapter(Activity context, List<Cake> cakes) {
        super(context, R.layout.list_item_layout, cakes);

        this.appContext = context;
        this.cakeList = cakes;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = appContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_layout, null, true);

        Cake cake = cakeList.get(position);

        TextView title = rowView.findViewById(R.id.title);
        TextView description = rowView.findViewById(R.id.desc);
        ImageView image = rowView.findViewById(R.id.image);

        title.setText(cake.getTitle());
        description.setText(cake.getDescription());

        ImageCache.getInstance().loadBitmap(cake.getTitle(), image);

        image.setImageBitmap(ImageCache.getInstance().getBitmapFromMemCache(cake.getTitle()));

        return rowView;
    }
}