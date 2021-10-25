package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final LinkedList<NoteModule> mNoteList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    static View.OnClickListener myOnClickListener;
    private StaggeredGridLayoutManager mGridLayoutManager;

    private final int INSERT = 1;
    private final int VIEW_UPDATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.my_toolbar1);
        setSupportActionBar(mToolBar);

        myOnClickListener = new MyOnClickListener(getApplicationContext());

        for (int i = 0; i < NoteData.titleArray.length; i++) {
            Random r = new Random();
            int randomNum = r.nextInt(NoteData.imageArray.length - 1);
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), NoteData.imageArray[randomNum]);
            mNoteList.add(new NoteModule(
                    NoteData.titleArray[i],
                    NoteData.detailArray[i],
                    bitmap,
                    NoteData.image[i]
            ));
        }
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mAdapter = new NoteAdapter(this, mNoteList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , AddNoteActivity.class);
                startActivityForResult(intent, INSERT);
                //Toast.makeText(MainActivity.this, "Fab test", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case INSERT:
                if (resultCode == RESULT_OK){
                    Bundle bunlde = data.getExtras();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getApplicationContext().openFileInput("myImage"));
                        NoteModule newNote = bunlde.getParcelable("note");
                        newNote.setImageSource(bitmap);
                        mNoteList.add(newNote);
                        mAdapter.notifyDataSetChanged();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                mNoteList.clear();;
                for (int i = 0; i < NoteData.titleArray.length; i++) {
                    Random r = new Random();
                    int randomNum = r.nextInt(NoteData.imageArray.length - 1);
                    Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), NoteData.imageArray[randomNum]);
                    mNoteList.add(new NoteModule(
                            NoteData.titleArray[i],
                            NoteData.detailArray[i],
                            bitmap,
                            NoteData.image[i]
                    ));
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.action_layout:
                if (item.getIcon().getConstantState().equals(
                        getResources().getDrawable(R.drawable.ic_grid).getConstantState())){
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    item.setIcon(R.drawable.ic_linear);
                } else {
                    mRecyclerView.setLayoutManager(mGridLayoutManager);
                    item.setIcon(R.drawable.ic_grid);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyOnClickListener implements View.OnClickListener{

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int selectedPosition =mRecyclerView.getChildPosition(v);
            Toast.makeText(context, "Item in position" + selectedPosition, Toast.LENGTH_SHORT).show();
        }
    }

}