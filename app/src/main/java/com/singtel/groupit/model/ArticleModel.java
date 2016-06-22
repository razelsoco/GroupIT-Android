package com.singtel.groupit.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.databinding.BaseObservable;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class ArticleModel extends BaseObservable  implements Parcelable {

    private String title;

    public ArticleModel() {
    }

    public ArticleModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("title=").append(title)
                .toString();
    }

    /*
        Parcelable
     */

    public ArticleModel(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    public static final Parcelable.Creator<ArticleModel> CREATOR = new Parcelable.Creator<ArticleModel>() {
        public ArticleModel createFromParcel(Parcel source) {
            return new ArticleModel(source);
        }

        public ArticleModel[] newArray(int size) {
            return new ArticleModel[size];
        }
    };
}
