package com.singtel.groupit;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.MediumTest;
import android.text.TextUtils;

import com.singtel.groupit.model.ArticleModel;
import com.singtel.groupit.util.MockModelsTestUtil;
import com.singtel.groupit.viewmodel.ArticleViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static junit.framework.Assert.assertEquals;

/**
 * Created by lanna on 6/22/16.
 *
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class ArticleViewModelTest {

    Context mockContext;

    ArticleViewModel articleViewModel;
    ArticleModel article;

    @Before
    public void setUp() {
        article = MockModelsTestUtil.createMockArticleModel();
        mockContext = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");
        articleViewModel = new ArticleViewModel(mockContext, article);
    }

    @Test
    public void dataBindingArticleViewModel() {
        assertEquals(articleViewModel.articleTitle(), article.getTitle());
        assertEquals(articleViewModel.articleCategory(), article.getCategory());
        assertEquals(articleViewModel.articleCategoryBGColor(), Color.TRANSPARENT);
        assertEquals(articleViewModel.articleCategoryVisible(), TextUtils.isEmpty(article.getCategory()) ? GONE : VISIBLE);
        assertEquals(articleViewModel.articleNeedReadVisible(), article.isNeedRead() ? VISIBLE : INVISIBLE);
        assertEquals(articleViewModel.articleIsReaded(), article.isReaded());
    }
}
