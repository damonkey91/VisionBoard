package com.example.mrx.visionboardapp.Helpers;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
                toast("backup created");
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

    public static void readFromFile(){
        if (isExternalStorageReadable()){
            File storageDir = getAlbumStorageDir();
            File textFile = new File(storageDir, FILE_NAME);
            try {
                FileInputStream fis = new FileInputStream(textFile);
                toast("read file");
            } catch (FileNotFoundException e) {
                toast("couldn't read from file");
                e.printStackTrace();
            }
        } else {
            toast("couldn't access storage");
        }
    }

    public static File getAlbumStorageDir() {
        // Get the directory for the user's public pictures directory.
        File path = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), ALBUM_NAME);

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
