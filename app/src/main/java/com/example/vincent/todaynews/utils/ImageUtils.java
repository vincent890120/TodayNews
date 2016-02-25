package com.example.vincent.todaynews.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by vincent on 16/2/4.
 */
public class ImageUtils {
    public static Bitmap resizeImageByWidth(Bitmap defaultBitmap,
                                            int targetWidth) {
        int rawWidth = defaultBitmap.getWidth();
        int rawHeight = defaultBitmap.getHeight();
        float targetHeight = targetWidth * (float) rawHeight / (float) rawWidth;
        float scaleWidth = targetWidth / (float) rawWidth;
        float scaleHeight = targetHeight / (float) rawHeight;
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(scaleHeight, scaleWidth);
        return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight,
                localMatrix, true);
    }
    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = android.util.Base64.decode(base64Data, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 是否base64
     * @param url
     * @return
     */
    public static boolean hasBase64(String url) {
        if(url.contains("data:image") && url.contains("base64")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param str
     * @return the file path or null
     */
    public static String getRealFilePath( final Context context, final String str ) {
        return getRealFilePath(context, Uri.parse(str));
    }

    public static Bitmap createScaledBitmap(Bitmap icon, int maxWidth, int maxHeight) {
        int iconWidth = icon.getWidth();
        int iconHeight = icon.getHeight();
        float newWidth = Math.min(iconWidth, maxWidth);
        float newHeight = Math.min(iconHeight, maxHeight);
        float scale = Math.min(newWidth / iconWidth, newHeight / iconHeight);
        return Bitmap.createScaledBitmap(icon, (int) (iconWidth * scale), (int) (iconHeight * scale), false);
    }

    public static String convertBitmap2Base64String(Bitmap bitmap, String format, int quality) {
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
        if ("jpg".equals(format)) {
            compressFormat = Bitmap.CompressFormat.JPEG;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, quality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
    }

    public static File saveImage(Bitmap bit) {
        File mediaDir = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera");
        if (!mediaDir.exists()) {
            mediaDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(mediaDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bit.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
