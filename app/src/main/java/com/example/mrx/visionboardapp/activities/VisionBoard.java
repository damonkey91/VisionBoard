package com.example.mrx.visionboardapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mrx.visionboardapp.Helpers.GetImageExternalStorage;
import com.example.mrx.visionboardapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class VisionBoard extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 21123;
    private static final String IMAGE_URI_KEY = "imageUriKey";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_board);
        imageView = findViewById(R.id.imageView);

        getChoosenImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.visionboard_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_button_actionbar:
                boolean result = GetImageExternalStorage.checkReadPermission(this);
                if (result){
                    startIntentToPickImage();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            setImage(intent.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GetImageExternalStorage.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startIntentToPickImage();
        }
    }

    private void startIntentToPickImage(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    private void setImage(Uri uri){
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(image);
            SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this).edit();
            sharedPreferences.putString(IMAGE_URI_KEY, uri.toString());
            sharedPreferences.commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.couldnt_find_file), Toast.LENGTH_SHORT).show();
        }
    }

    private void getChoosenImage(){
        String uri = PreferenceManager.getDefaultSharedPreferences(this).getString(IMAGE_URI_KEY, null);
        if (uri != null){
            boolean result = GetImageExternalStorage.checkReadPermission(this);
            if (result) {
                setImage(Uri.parse(uri));
            }
        }
    }
}
