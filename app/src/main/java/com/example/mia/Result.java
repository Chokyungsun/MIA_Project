package com.example.mia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Result extends AppCompatActivity {

    ImageView result;
    Button callPolice;
    TextView kidInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        result = findViewById(R.id.finalkid);
        callPolice = findViewById(R.id.callPolice);
        kidInfo = findViewById(R.id.kid_info);

        //정보
        kidInfo.setText("이름: 주찬형 \n\n실종 당시 나이: 9살\n실종 당시 일자: 2002년 1월 4일 \n생년월일: 1994년 10월 14일 \n실종 장소: 경기도 인천시 계양구 \n\n아이 특징: 활발함. 오른쪽 눈썹에 큰 접이 있음. 키가 작음. 부정교합이 있음. 마른 체구.");


        //이미지 띄우기
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = getIntent().getByteArrayExtra("img");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        result.setImageBitmap(bitmap);


        //경찰서 전화
        callPolice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String police_num = "tel:02-774-0182";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(police_num));
                startActivity(intent);
            }
        });


    }


}
