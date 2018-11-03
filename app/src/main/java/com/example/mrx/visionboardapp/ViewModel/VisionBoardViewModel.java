package com.example.mrx.visionboardapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.mrx.visionboardapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class VisionBoardViewModel extends AndroidViewModel {

    private static final String IMAGE_DIRECTORY= "imageDir";
    private static final String IMAGE_FILE_NAME = "choosenpic";

    private Context context;

    public VisionBoardViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    public Bitmap getImage(){
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), android.R.drawable.picture_frame);
        try {
            FileInputStream inputStream = new FileInputStream(getImagePath());
            image = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, context.getString(R.string.couldnt_find_file), Toast.LENGTH_SHORT).show();
        }
        return image;
    }

    public void saveBitmap(Bitmap bitmap){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getImagePath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getImagePath(){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        return new File(directory,IMAGE_FILE_NAME);
    }
}
