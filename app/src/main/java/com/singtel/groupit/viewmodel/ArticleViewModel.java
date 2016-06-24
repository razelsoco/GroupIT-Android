package com.singtel.groupit.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.singtel.groupit.model.remote.ApiCommons;
import com.singtel.groupit.model.ArticleModel;
import com.singtel.groupit.util.DateHelper;
import com.singtel.groupit.util.LogUtils;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class ArticleViewModel {

    private Context context;
    private ArticleModel article;

    public ArticleViewModel(Context context, ArticleModel article) {
        this.context = context;
        this.article = article;
    }

    /*
        Data Binding
     */
    public String articleTitle() {
        return article.getTitle();
    }

    public String articleCategory() {
        return article.getCategory();
    }

    public int articleCategoryVisible() {
        return !TextUtils.isEmpty(article.getCategory())
                ? View.VISIBLE
                : View.GONE;
    }

    public int articleCategoryBGColor() {
        return ApiCommons.colorValid(article.getColor())
                ? Color.parseColor(article.getColor())
                : Color.TRANSPARENT;
    }

    public int articleNeedReadVisible() {
        return article.isNeedRead()
                ? View.VISIBLE
                : View.GONE;
    }

    @BindingAdapter("readState")
    public static void setReadState(View view, boolean readed) {
        view.setSelected(readed);
    }

    public String articleContent() {
        return article.getContent();
    }

    public String articleTime() {
        return DateHelper.timeRecentlyElapsed(context, article.getCreatedTime());
    }

    public View.OnClickListener onClickRoot() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtils.i(this, "data binding test onClickRoot");
            }
        };
    }

    public boolean articleIsReaded() {
        return article.isReaded();
    }
}
