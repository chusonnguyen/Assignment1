package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class EditNoteActivity extends AppCompatActivity {

    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Toolbar setup
        Toolbar mToolbar = findViewById(R.id.my_toolbar3);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText title = findViewById(R.id.Title);
        EditText detail = findViewById(R.id.Detail);
        ImageView image = findViewById(R.id.image);

        if(getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            NoteModule noteModule = bundle.getParcelable("note");
            position = bundle.getInt("position");
            if (noteModule.getImageCheck() == true){
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getApplicationContext().openFileInput("myImage"));
                    title.setText(noteModule.getTitle());
                    detail.setText(noteModule.getDetail());
                    image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                title.setText(noteModule.getTitle());
                detail.setText(noteModule.getDetail());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent();
            intent.putExtra("returnPosition", position);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}