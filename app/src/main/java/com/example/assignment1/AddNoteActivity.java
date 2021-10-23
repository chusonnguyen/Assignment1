package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AddNoteActivity extends AppCompatActivity {

    EditText detail, title;
    ImageView img;
    Bitmap bitmap;
    byte[] byteArray;
    Boolean imgCheck = false;

    static final int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Toolbar setup
        Toolbar mToolbar = findViewById(R.id.my_toolbar2);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Insert");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = findViewById(R.id.textTitle);

        //set up done button for multi-line edittext
        detail = findViewById(R.id.textDetail);
        detail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        detail.setRawInputType(InputType.TYPE_CLASS_TEXT);
        Button btn = findViewById(R.id.saveButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteDetail = detail.getText().toString();
                String noteTitle = title.getText().toString();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                if(noteTitle.matches("") && noteDetail.matches("")){
                    finish();
                } else {
                    NoteModule newNote = new NoteModule(noteTitle, noteDetail, null, imgCheck);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("note", newNote);
                    bundle.putString("imgSource", createImageFromBitmap(bitmap));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        Button clearBtn = findViewById(R.id.clearButton);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.getText().clear();
                detail.getText().clear();
            }
        });

        img = findViewById(R.id.imgeView);
        Button imgBtn = findViewById(R.id.addImageButton);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        img.setImageBitmap(bitmap);
                        imgCheck = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

}