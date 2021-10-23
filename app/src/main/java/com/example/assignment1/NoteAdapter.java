package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final LinkedList<NoteModule> mNoteList;
    private LayoutInflater mInflater;
    private Context mContext;

    public NoteAdapter(Context context, LinkedList<NoteModule> mNoteList) {
        mInflater = LayoutInflater.from(context);
        this.mNoteList = mNoteList;
        this.mContext = context;
    }
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
                // mItemView1.setOnClickListener(FirstFragment.myOnClickListener);
                return new NoteViewHolder1(mItemView1, this);
            case 1:
                View mItemView2 = mInflater.inflate(R.layout.note_item2,
                    parent, false);
                // mItemView2.setOnClickListener(FirstFragment.myOnClickListener);
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
                    ((NoteViewHolder1)holder).titleView.setText(currentPosition.getTitle());
                    Glide.with(mContext).load(currentPosition.getImageSource()).into(((NoteViewHolder1)holder).imageView);
                }
                break;

            case 1:
                NoteModule currentPosition1 = mNoteList.get(position);
                if (holder instanceof NoteViewHolder2){
                    ((NoteViewHolder2)holder).nameView.setText(currentPosition1.getTitle());
                    ((NoteViewHolder2)holder).detailView.setText(currentPosition1.getDetail());
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
        final NoteAdapter mAdapter;
        public NoteViewHolder1(View itemView, NoteAdapter mAdapter) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.sportsImage);
            this.mAdapter = mAdapter;
        }
    }
    class NoteViewHolder2 extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView detailView;
        final NoteAdapter mAdapter;
        public NoteViewHolder2(View itemView, NoteAdapter mAdapter) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            detailView = itemView.findViewById(R.id.description);
            this.mAdapter = mAdapter;
        }
    }
}
