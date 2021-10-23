package com.example.assignment1;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class NoteModule implements Parcelable {
    private String title;
    private String detail;
    private Bitmap imageSource;
    private final boolean imageCheck;


    public NoteModule(String title, String detail, Bitmap imageSource, boolean imageCheck) {
        this.title = title;
        this.detail = detail;
        this.imageSource = imageSource;
        this.imageCheck = imageCheck;
        //this.colorCover = colorCover;
    }

    protected NoteModule(Parcel in) {
        title = in.readString();
        detail = in.readString();
        imageSource = in.readParcelable(Bitmap.class.getClassLoader());
        imageCheck = in.readByte() != 0;
    }

    public static final Creator<NoteModule> CREATOR = new Creator<NoteModule>() {
        @Override
        public NoteModule createFromParcel(Parcel in) {
            return new NoteModule(in);
        }

        @Override
        public NoteModule[] newArray(int size) {
            return new NoteModule[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageSource(Bitmap bitmap) {this.imageSource = bitmap; }

    public String getDetail() {
        return detail;
    }


    public Bitmap getImageSource() {
        return imageSource;
    }
    public boolean getImageCheck(){
        return imageCheck;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(detail);
        dest.writeParcelable(imageSource, flags);
        dest.writeByte((byte) (imageCheck ? 1 : 0));
    }
}
