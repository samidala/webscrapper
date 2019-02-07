package com.techdisqus.service;


import com.techdisqus.dao.ArticlesDao;
import com.techdisqus.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;



@Component
public class ArticleServiceHibernateImpl implements  ArticleService {



    @Autowired
    ArticlesDao articlesDao;


    @Override
    public List<Article> getArticlesByNameAndDesc(String articleName, String articleDesc) {
        return articlesDao.searchByArticleNameOrDesc(articleName,articleDesc);
    }
}
