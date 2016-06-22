package com.singtel.groupit.viewmodel;

import android.content.Context;
import android.view.View;

import com.singtel.groupit.model.Article;
import com.singtel.groupit.util.LogUtils;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class ItemArticleViewModel {

    private Article article;

    public ItemArticleViewModel(Article article) {
        this.article = article;
    }

    /*
        Data Binding
     */
    public String articleTitle() {
        return "data binding test title: " + (article == null ? "null" : article.title);
    }

    public View.OnClickListener onClickRoot() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtils.i(this, "data binding test onClickRoot");
            }
        };
    }
}
