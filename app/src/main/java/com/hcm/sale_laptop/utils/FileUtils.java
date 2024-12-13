package com.hcm.sale_laptop.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {
    public static String getPathFromUri(Context context, Uri uri) {
        if (uri == null) return null; // Kiểm tra null sớm

        String fileName = getFileName(context, uri);
        if (fileName == null || fileName.isEmpty()) fileName = "temp_file";

        File file = new File(context.getCacheDir(), fileName);
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(file)) {
            if (inputStream == null) return null; // Kiểm tra InputStream
            int read;
            byte[] buffers = new byte[1024];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu lỗi
        }
        return file.getAbsolutePath();
    }


    @SuppressLint("Range")
    private static String getFileName(Context context, Uri uri) {
        String fileName = "temp_file";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            cursor.close();
        }
        return fileName;
    }
}

