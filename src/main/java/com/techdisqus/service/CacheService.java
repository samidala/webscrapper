package com.techdisqus.service;

import com.techdisqus.model.Article;

public interface CacheService {

    /**
     * adding to cache
     * @param author
     */
    void addAuthor(String author);

    /**
     * Add article to cache
     * @param article
     */
    void addArticle(Article article);
}
