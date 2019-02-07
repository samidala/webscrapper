package com.techdisqus.service;



import com.techdisqus.dao.AuthorDao;
import com.techdisqus.model.Article;
import com.techdisqus.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorServiceHibernateImpl implements  AuthorService {


    @Autowired
    AuthorDao  authorDao;


    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public List<Author> searchByAuthorName(String authorName) {
        return authorDao.searchByAuthorName(authorName);
    }

    @Override
    public Author getAuthorByName(String authorName) {
        return authorDao.getAuthorByName(authorName);
    }

    @Override
    public List<Article> getArticlesByAuthor(String authorName) {
        return authorDao.getArticlesByAuthor(authorName);
    }

    @Override
    public List<Article> getArticlesByAuthorById(int id) {
        return authorDao.getArticlesByAuthorById(id);
    }
}
