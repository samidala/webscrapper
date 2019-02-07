package com.techdisqus.dao;


import com.techdisqus.model.Article;
import com.techdisqus.model.Author;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;



public interface ArticlesDao {






    /**
     * performs partial articles by article name article desc
     * @param articleName
     * @param articleDesc
     * @return
     */
     List<Article> searchByArticleNameOrDesc(String articleName, String articleDesc);





}
