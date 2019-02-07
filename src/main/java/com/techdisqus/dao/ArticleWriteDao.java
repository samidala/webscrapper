package com.techdisqus.dao;

import com.techdisqus.model.Article;

public interface ArticleWriteDao {

    /**
     * Saves article to database
     * @param article
     */
    Article saveArticle(Article article);
}
