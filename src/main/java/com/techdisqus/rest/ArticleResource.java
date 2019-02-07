package com.techdisqus.rest;


import com.techdisqus.model.Article;
import com.techdisqus.model.Author;
import com.techdisqus.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleResource {

  @Autowired
  private ArticleService articleService;

  @GetMapping(value = "/articles/{articleName}/{articleDesc}")
  @ResponseBody
  public List<Article> searchArticlesByTitleAndDesc(@PathVariable(name = "articleName") String articleName,
                                                   @PathVariable(name = "articleDesc") String articleDesc){
      return articleService.getArticlesByNameAndDesc(articleName,articleDesc);
  }

  @GetMapping(value = "/articles/title/{title}")
  @ResponseBody
  public List<Article> searchArticlesByTitle(@PathVariable(name = "title") String articleName){
    return articleService.getArticlesByNameAndDesc(articleName,"");
  }


  @GetMapping(value = "/articles/desc/{articleDesc}")
  @ResponseBody
  public List<Article> searchArticlesDesc(@PathVariable(name = "articleDesc") String articleDesc){
    return articleService.getArticlesByNameAndDesc("",articleDesc);
  }


}
