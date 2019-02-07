package com.techdisqus.dao;


import com.techdisqus.model.Article;
import com.techdisqus.model.Author;

import java.util.List;


public interface AuthorDao {

    /**
     * fetches all the authors
     * @return
     */
     List<Author> getAllAuthors();

    /**
     * search partial search and return all matching authors
     * @param author
     * @return
     */
     List<Author> searchByAuthorName(String author);

    /**
     * return exact matching author name
     * @param authorName
     * @return
     */
     Author getAuthorByName(String authorName);

    /**
     * returns all the articles by author by name
     * @param authorName
     * @return
     */
     List<Article> getArticlesByAuthor(String authorName);

    /**
     * returns all the articles by author by Id
     * @param id
     * @return
     */
    List<Article> getArticlesByAuthorById(int id);







}
