package com.example.mrx.visionboardapp.Helpers;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.mrx.visionboardapp.Objects.BackupObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReadAsync extends AsyncTask<Uri, Void, BackupObject> {
    private IFileReadCallback callback;

    public FileReadAsync(IFileReadCallback callback) {
        this.callback = callback;
    }

    @Override
    protected BackupObject doInBackground(Uri... uris) {
        Uri uri = uris[0];
        InputStream inputStream = null;
        StringBuilder text = new StringBuilder();
        String backupObj = null;
        try {
            inputStream = AppContextGetter.getContext().getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
            reader.close();
            backupObj = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GsonHandler.convertToBackupObject(backupObj);
    }

    @Override
    protected void onPostExecute(BackupObject backupObject) {
        callback.fileRead(backupObject);
    }

    public interface IFileReadCallback{
        public void fileRead(BackupObject backupObject);
    }
}
