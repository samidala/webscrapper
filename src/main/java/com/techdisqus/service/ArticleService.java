package com.techdisqus.service;

import com.techdisqus.model.Article;
import java.util.List;


/**
 * Article Service
 */
public interface ArticleService {


     /**
      * fetches all the articles with partial matching title and description
      * @param articleName
      * @param articleDesc
      * @return
      */

     List<Article> getArticlesByNameAndDesc(String articleName, String articleDesc);

}
