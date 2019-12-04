package com.example.mia;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    Button camerabutton;
    ImageView ImageView_r;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camerabutton = findViewById(R.id.button);
        ImageView_r = findViewById(R.id.imageView);


//
//        Bundle extras = getIntent().getExtras();
//        byte[] byteArray = getIntent().getByteArrayExtra("img");
//        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Drawable kid = getResources().getDrawable(R.drawable.img1);
        bitmap = ((BitmapDrawable)kid).getBitmap();

        ImageView_r.setImageBitmap(bitmap);

        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProgressDialog();

                Intent intent2 = new Intent(getApplicationContext(), MactchingActivity.class);

//                byte[] byteArray = CovertImg(bitmap);
//
//                intent2.putExtra("img", byteArray);

                startActivity(intent2);
            }
        });


    }

    public byte[] CovertImg(Bitmap img){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = img;
        float scale = (float) (1024/(float)bitmap.getWidth());
        int image_w = (int) (bitmap.getWidth() * scale);
        int image_h = (int) (bitmap.getHeight() * scale);
        Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    private void showProgressDialog()  {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("실종 아동 매칭 중");
        dialog.show();
    }

}