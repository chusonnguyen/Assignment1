package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class EditNoteActivity extends AppCompatActivity {

    Integer position;
    EditText title, detail;
    ImageView image;
    Boolean imgCheck;

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

        title = findViewById(R.id.Title);
        detail = findViewById(R.id.Detail);
        image = findViewById(R.id.image);

        if(getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            NoteModule noteModule = bundle.getParcelable("note");
            position = bundle.getInt("position");
            imgCheck = noteModule.getImageCheck();
            if (imgCheck == true){
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
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("returnPosition", position);
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            case R.id.edit_finish:
                Intent finishIntent = new Intent();
                Bundle bundle = new Bundle();
                NoteModule returnNote = new NoteModule(title.getText().toString(), detail.getText().toString(), null , imgCheck);
                bundle.putParcelable("returnNote", returnNote);
                bundle.putInt("returnPosition", position);
                finishIntent.putExtras(bundle);
                setResult(RESULT_OK, finishIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }
}