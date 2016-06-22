package com.singtel.groupit.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.databinding.BaseObservable;

import com.singtel.groupit.util.LogUtils;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class Article extends BaseObservable  implements Parcelable {

    public String title;

    public Article() {
    }

    public Article(String title) {
        this.title = title;
    }

    /*
        Parcelable
     */

    public Article(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
