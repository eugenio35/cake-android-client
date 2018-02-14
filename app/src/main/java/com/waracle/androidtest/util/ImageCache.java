package com.waracle.androidtest.util;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * Created by emancebo on 2/14/18.
 */

public class ImageCache {

    private static ImageCache instance = null;
    private static LruCache<String, Bitmap> memoryCache;

    public static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public void init(int cacheSize) {
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (bitmap != null) {
            if (getBitmapFromMemCache(key) == null) {
                memoryCache.put(key, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    public void loadBitmap(String key, ImageView imageView) {

        final Bitmap bitmap = getBitmapFromMemCache(key);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
