package com.example.mia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MactchingActivity extends AppCompatActivity {

    ImageView ImageView_input;
    ImageView ImageView_output;
    Button yesbut;
    Button nobut;
    Bitmap kid_output;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mactching);

        ImageView_input = findViewById(R.id.pickedimg);
        ImageView_output = findViewById(R.id.foundimg);

        yesbut = findViewById(R.id.yesbut);
        nobut = findViewById(R.id.nobut);


        Drawable kid = getResources().getDrawable(R.drawable.kid1);
        kid_output = ((BitmapDrawable)kid).getBitmap();

//        Bundle extras = getIntent().getExtras();
//        byte[] byteArray = getIntent().getByteArrayExtra("img");
//        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        Drawable img_input = getResources().getDrawable(R.drawable.img1);
        bitmap = ((BitmapDrawable)img_input).getBitmap();

        ImageView_input.setImageBitmap(bitmap);
        ImageView_output.setImageDrawable(kid);

        yesbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(getApplicationContext(), Result.class);

                byte[] byteArray = CovertImg(kid_output);

                intent2.putExtra("img", byteArray);

                startActivity(intent2);
            }
        });

        nobut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(getApplicationContext(), BeforeMainActivity.class);

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

}
