package com.singtel.groupit.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lanna on 6/22/16.
 *
 {
     "title":"Planned Mantanence of HR",
     "category":"HR center",
     "color":"#f00",
     "content":"Please be informed of the plan.."
     "isNeedRead":true,
     "createdTime":"Thu, 26 May 2016 12:16:30 +0800"
 }
 */

public class ArticleModel implements Parcelable {

    // FIXME data test, will update when api ready
    private String title;
    private String category;
    private String color;
    private String content;
    private boolean isNeedRead;
    private boolean isReaded;
    private String createdTime;

    public ArticleModel() {
    }

    public ArticleModel(String title, String category, String color, String content,
                        boolean isNeedRead, boolean isReaded, String createdTime) {
        this.title = title;
        this.category = category;
        this.color = color;
        this.content = content;
        this.isNeedRead = isNeedRead;
        this.isReaded = isReaded;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("title=").append(title)
                .append(", category=").append(category)
                .append(", color=").append(color)
                .append(", content=").append(content)
                .append(", isNeedRead=").append(isNeedRead)
                .append(", createdTime=").append(createdTime)
                .toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public String getContent() {
        return content;
    }

    public boolean isNeedRead() {
        return isNeedRead;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public String getCreatedTime() {
        return createdTime;
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
