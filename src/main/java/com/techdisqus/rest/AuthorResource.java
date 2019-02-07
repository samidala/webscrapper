package com.techdisqus.rest;


import com.techdisqus.model.Article;
import com.techdisqus.model.Author;
import com.techdisqus.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorResource {

  @Autowired
  private AuthorService authorService;


  @GetMapping(value = "/authors/all")
  @ResponseBody
  public List<Author> getAllAuthors(){
      return authorService.getAllAuthors();
  }



    @GetMapping(value = "/authors/{authorName}")
    @ResponseBody
  public List<Author> searchByAuthorName(@PathVariable("authorName") String authorName){
      return authorService.searchByAuthorName(authorName);
  }

    @GetMapping(value = "/author/articles/name/{authorName}")
    @ResponseBody
    public List<Article> getAuthorArticlesByName(@PathVariable("authorName")String authorName){
        return authorService.getArticlesByAuthor(authorName);
    }

  @GetMapping(value = "/author/articles/id/{id}")
  @ResponseBody
  public List<Article> getAuthorArticlesById(@PathVariable("id")int id){
    return authorService.getArticlesByAuthorById(id);
  }

}
