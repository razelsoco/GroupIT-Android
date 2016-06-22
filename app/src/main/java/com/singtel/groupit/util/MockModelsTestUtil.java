package com.singtel.groupit.util;

import com.singtel.groupit.model.ArticleModel;

import java.util.Random;
import java.util.UUID;

/**
 * Created by lanna on 6/22/16.
 *
 */

public class MockModelsTestUtil {

    public static Long generateRandomLong() {
        return new Random().nextLong();
    }

    public static String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    public static int generateRandomInt() {
        return new Random().nextInt(80 - 65) + 65;
    }

    public static ArticleModel createMockArticleModel() {
        ArticleModel articleModel = new ArticleModel();
        articleModel.setTitle(generateRandomString());
        return articleModel;
    }
}
