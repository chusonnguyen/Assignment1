package com.example.assignment1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LinkedList<NoteModule> mNoteList;
    private LayoutInflater mInflater;
    private Context mContext;
    private boolean hideDelete;

    public NoteAdapter(Context context, LinkedList<NoteModule> mNoteList) {
        mInflater = LayoutInflater.from(context);
        this.mNoteList = mNoteList;
        this.mContext = context;
        this.hideDelete = true;
    }

    public void setHideDelete(boolean hide){this.hideDelete = hide;}

    @Override
    public int getItemViewType(int position) {
        if (mNoteList.get(position).getImageCheck() == true){
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                View mItemView1 = mInflater.inflate(R.layout.note_item1,
                        parent, false);
                mItemView1.setOnClickListener(MainActivity.myOnClickListener);
                return new NoteViewHolder1(mItemView1, this);
            case 1:
                View mItemView2 = mInflater.inflate(R.layout.note_item2,
                    parent, false);
                mItemView2.setOnClickListener(MainActivity.myOnClickListener);
                return new NoteViewHolder2(mItemView2, this);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                NoteModule currentPosition = mNoteList.get(position);
                if (holder instanceof NoteViewHolder1){
                    if (hideDelete == true) {
                        ((NoteViewHolder1) holder).deleteLayout.setVisibility(View.GONE);
                    } else {
                        ((NoteViewHolder1) holder).deleteLayout.setVisibility(View.VISIBLE);
                    }
                    ((NoteViewHolder1)holder).titleView.setText(currentPosition.getTitle());
                    ((NoteViewHolder1)holder).imageView.setImageBitmap(currentPosition.getImageSource());
                    ((NoteViewHolder1)holder).button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setMessage("Do you want to delete this note")
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mNoteList.remove(position);
                                            ((NoteViewHolder1)holder).mAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });
                }
                break;

            case 1:
                NoteModule currentPosition1 = mNoteList.get(position);
                if (holder instanceof NoteViewHolder2){
                    if (hideDelete == true) {
                        ((NoteViewHolder2) holder).deleteLayout.setVisibility(View.GONE);
                    } else {
                        ((NoteViewHolder2) holder).deleteLayout.setVisibility(View.VISIBLE);
                    }
                    ((NoteViewHolder2)holder).nameView.setText(currentPosition1.getTitle());
                    ((NoteViewHolder2)holder).detailView.setText(currentPosition1.getDetail());
                    ((NoteViewHolder2)holder).button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setMessage("Do you want to delete this note")
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mNoteList.remove(position);
                                            ((NoteViewHolder2)holder).mAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    class NoteViewHolder1 extends RecyclerView.ViewHolder {
        public final TextView titleView;
        public final ImageView imageView;
        public final MaterialButton button;
        public LinearLayout deleteLayout;
        final NoteAdapter mAdapter;
        public NoteViewHolder1(View itemView, NoteAdapter mAdapter) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.sportsImage);
            button = itemView.findViewById(R.id.delete1);
            deleteLayout = itemView.findViewById(R.id.card_delete1);
            this.mAdapter = mAdapter;
        }
    }
    class NoteViewHolder2 extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView detailView;
        public final MaterialButton button;
        public LinearLayout deleteLayout;
        final NoteAdapter mAdapter;
        public NoteViewHolder2(View itemView, NoteAdapter mAdapter) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            detailView = itemView.findViewById(R.id.description);
            button = itemView.findViewById(R.id.delete2);
            deleteLayout = itemView.findViewById(R.id.card_delete2);
            this.mAdapter = mAdapter;
        }
    }

}
