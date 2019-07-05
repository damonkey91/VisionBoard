package com.example.mrx.visionboardapp.Helpers;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ReadAndWriteToExternalStorage {
    private static String ALBUM_NAME = "vision board data";
    private static String FILE_NAME = "visionboarddata.txt";

    public static void writeToFile(String data){
        if (isExternalStorageWritable()){
            File storageDir = getAlbumStorageDir();
            File file = new File(storageDir, FILE_NAME);
            try
            {
                file.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);

                myOutWriter.close();
                fOut.flush();
                fOut.close();
                toast("backup created in download");
            }
            catch (IOException e)
            {
                Log.e("Exception", "File write failed: " + e.toString());
                toast("couldn't write to file");
            }
        }else {
            toast("couldn't access storage");
        }
    }

    public static void readFromFile(FileReadAsync.IFileReadCallback callback, Uri uri){
        if (isExternalStorageReadable()){
            new FileReadAsync(callback).execute(uri);
        } else {
            toast("couldn't access storage");
        }
    }

    private static File getAlbumStorageDir() {
        File path = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), ALBUM_NAME);

        if (!path.exists()) {
            if (!path.mkdirs())
                Log.e("tag", "Directory not created");
        }
        return path;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private static void toast(String message){
        Toast.makeText(AppContextGetter.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
