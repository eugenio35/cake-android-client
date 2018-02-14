package com.waracle.androidtest;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Riad on 20/05/2015.
 */
public class StreamUtils {
    private static final String TAG = StreamUtils.class.getSimpleName();

    // Can you see what's wrong with this???
    // - I saw 2 loops to read the same stream. We can achieve the same with ByteArrayOutputStream
    // - We weren't using the close method

    public static byte[] readUnknownFully(InputStream stream) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read;
        while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, read);
        }
        baos.flush();

        return baos.toByteArray();
    }
}
