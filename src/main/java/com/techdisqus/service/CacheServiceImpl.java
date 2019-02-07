package com.techdisqus.service;

import com.techdisqus.model.Article;
import com.techdisqus.model.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CacheServiceImpl implements CacheService {

    private final Set<String> authors = new HashSet<>();
    private final Map<String, List<Article>> articleMap = new HashMap<>();

    public void addAuthor(String author){
        authors.add(author);
    }
    public void addArticle(Article article){
        Author author = article.getAuthor();
        String authorName = "UNKNOWN";
        if(author != null){
            authorName = author.getAuthorName();
        }
        if(articleMap.containsKey(authorName)){
            articleMap.get(authorName).add(article);
        }else{
            List<Article> articles = new ArrayList<>();
            articles.add(article);
            articleMap.put(authorName,articles);
        }

    }
}
