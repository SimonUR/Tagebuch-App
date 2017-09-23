package com.example.simon.tagebuch_app.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.simon.tagebuch_app.image.PictureActivity.*;


public class Camera {
    PictureActivity activityContext;
    private String currentPhotoPath;

    public Camera(PictureActivity activityContext) {
        this.activityContext = activityContext;
    }

    public void takePicture(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activityContext.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
                if (photoFile != null) {

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    activityContext.startActivityForResult(takePictureIntent, requestCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public Bitmap getScaledBitmap(String path, Point targetSize) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


        int scaleFactor = Math.min(photoW / targetSize.x, photoH / targetSize.y);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        return bitmap;
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMAN).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();

        return image;
    }
}
