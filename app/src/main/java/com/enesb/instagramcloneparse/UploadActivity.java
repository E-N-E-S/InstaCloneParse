package com.enesb.instagramcloneparse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    EditText commentText;
    ImageView imageView;
    Bitmap chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        commentText = findViewById(R.id.upload_activity_comment_text);
        imageView = findViewById(R.id.upload_activity_imageview);

    }

    public void upload (View view) {
        // Upload picture to database

        String comment = commentText.getText().toString();

        // Fotografi kaydetme
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        chosenImage.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        ParseFile parseFile = new ParseFile("image.png", bytes);


        ParseObject object = new ParseObject("Posts");
        object.put("comment", comment);
        object.put("username", ParseUser.getCurrentUser().getUsername());
        object.put("image", parseFile); // Fotografi ekledik.
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Post uploaded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(intent);

                }
            }
        });



    }

    public void chooseImage (View view) {
        // Choose image from gallery

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            // Izin verilmemis ise izin isteme;
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

        }
        else {

            // Izin verilmis ise;
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // Resmi alma (URI alma)
            startActivityForResult(intent, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Izin sonucunda yapilacaklar;

        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Dogru izin verildi ise;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // Resmi alma (URI alma)
                startActivityForResult(intent, 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Kullanici resmi sectikten sonra ne olacak;

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Gercekten resmi sectiyse;

            Uri uri = data.getData();

            try {
                chosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageView.setImageBitmap(chosenImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}