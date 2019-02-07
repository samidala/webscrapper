package com.techdisqus.service;

import com.techdisqus.model.Article;
import com.techdisqus.model.Author;

import java.util.List;


public interface AuthorService {

    List<Author> getAllAuthors();
    List<Author> searchByAuthorName(String authorName);
    Author getAuthorByName(String authorName);
     List<Article> getArticlesByAuthor(String authorName);
    List<Article> getArticlesByAuthorById(int id);

}
