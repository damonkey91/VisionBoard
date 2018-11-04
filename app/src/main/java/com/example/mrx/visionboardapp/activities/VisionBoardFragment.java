package com.example.mrx.visionboardapp.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mrx.visionboardapp.Helpers.GetImageExternalStorage;
import com.example.mrx.visionboardapp.R;
import com.example.mrx.visionboardapp.ViewModel.VisionBoardViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class VisionBoardFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 21123;

    private ImageView imageView;
    private VisionBoardViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(VisionBoardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_vision_board, container, false);
        imageView = view.findViewById(R.id.imageView);
        setImage();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.visionboard_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_button_actionbar:
                if (GetImageExternalStorage.checkReadPermission(this)){
                    startIntentToPickImage();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK){
            Uri uri = intent.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                viewModel.saveBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            setImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GetImageExternalStorage.REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION && grantResults.length > 0
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

    private void setImage(){
        Bitmap image = viewModel.getImage();
        imageView.setImageBitmap(image);
    }

}
