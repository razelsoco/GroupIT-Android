package com.singtel.groupit;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import com.singtel.groupit.model.ArticleModel;
import com.singtel.groupit.util.MockModelsTestUtil;
import com.singtel.groupit.viewmodel.ArticleViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by lanna on 6/22/16.
 *
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class ArticleViewModelTest {

    ArticleViewModel articleViewModel;
    ArticleModel articleModel;

    @Before
    public void setUp() {
        articleModel = MockModelsTestUtil.createMockArticleModel();
        articleViewModel = new ArticleViewModel(articleModel);
    }

    @Test
    public void dataBindingArticleViewModel() {
        String expectedTitle = "data binding test title: " + articleModel.getTitle();
        assertEquals(articleViewModel.articleTitle(), expectedTitle);
    }
}
