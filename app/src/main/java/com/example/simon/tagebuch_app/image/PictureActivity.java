package com.example.simon.tagebuch_app.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.simon.tagebuch_app.R;
import com.example.simon.tagebuch_app.image.Camera;
import com.example.simon.tagebuch_app.image.DatabaseHelper;
import com.example.simon.tagebuch_app.image.ImageAdapter;


public class PictureActivity extends Activity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Camera camera;
    private ImageAdapter gridAdapter;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhelper = new DatabaseHelper(this);
        initCamera();
        initUI();
        updateList();
    }

    private void updateList() {
        gridAdapter.clearArrayList();
        ArrayList<Bitmap> images = dbhelper.getAllImageFromDB();
        for (int i=0; i < images.size(); i++) {
            gridAdapter.addImage(images.get(i));
        }
        gridAdapter.notifyDataSetChanged();
    }

    private void initCamera() {
        camera = new Camera(this);
    }

    private void initUI() {
        setContentView(R.layout.image);
        Point displaySize = getDisplaySize();

        GridView grid = (GridView) findViewById(R.id.photo_grid);
        gridAdapter = new ImageAdapter(this, displaySize);
        grid.setAdapter(gridAdapter);

        ImageButton cameraButton = (ImageButton) findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }


    private void takePicture() {
        camera.takePicture(REQUEST_IMAGE_CAPTURE);

    }

    private void processPicture(String path) {
        Point imageSize = new Point(getDisplaySize().x / 2, getDisplaySize().y / 2);
        Bitmap image = camera.getScaledBitmap(path, imageSize);

        gridAdapter.addImage(image);
        gridAdapter.notifyDataSetChanged();

        saveImageDB(image);

    }

    private void saveImageDB(Bitmap image) {
        byte[] byteArray = getBytes(image);
        String timeStampImageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMAN).format(new Date());
        dbhelper.addImage(timeStampImageName, byteArray);
    }


    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    private Point getDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processPicture(camera.getCurrentPhotoPath());
        }
    }

}