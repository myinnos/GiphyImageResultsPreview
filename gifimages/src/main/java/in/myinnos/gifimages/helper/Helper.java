package in.myinnos.gifimages.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import in.myinnos.gifimages.builder.GiphyQueryBuilder;

/**
 * Created by 10 on 24-02-2017.
 */

public class Helper {

    public static String getGiphyQueryUrl(String ARG_SEARCH_QUERY, int limit,
                                          GiphyQueryBuilder.EndPoint endPoint, String GIPHY_API_KEY) {

        GiphyQueryBuilder builder = new GiphyQueryBuilder(endPoint, GIPHY_API_KEY)
                .setQuery(ARG_SEARCH_QUERY)
                .setLimit(limit);

        return builder.build();
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
